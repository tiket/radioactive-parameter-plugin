package io.jenkins.plugins.luxair.util;

import net.sf.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fetches (and caches) an OAuth2 access token from the GCE/GKE metadata server.
 * Used for GCR / Artifact Registry when the configured credential cannot supply
 * a password, e.g. on GKE Workload Identity.
 */
public final class GcpMetadataToken {

    private static final Logger logger = Logger.getLogger(GcpMetadataToken.class.getName());
    private static final String DEFAULT_URL =
        "http://metadata.google.internal/computeMetadata/v1/instance/service-accounts/default/token";
    private static final long EXPIRY_MARGIN_MS = 60_000L;
    private static final HttpClient CLIENT = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(2))
        .build();

    private static String cachedToken;
    private static long cachedExpiresAt;

    private GcpMetadataToken() {
    }

    public static boolean isGoogleRegistry(String credentialId, String registry) {
        if (credentialId != null && credentialId.startsWith("gcr:")) {
            return true;
        }
        if (registry == null) {
            return false;
        }
        String host = registry.replaceFirst("^https?://", "").split("/")[0];
        return host.endsWith("gcr.io") || host.endsWith("-docker.pkg.dev");
    }

    public static synchronized String get() {
        long now = System.currentTimeMillis();
        if (cachedToken != null && now < cachedExpiresAt - EXPIRY_MARGIN_MS) {
            return cachedToken;
        }

        String url = System.getProperty(GcpMetadataToken.class.getName() + ".url", DEFAULT_URL);
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("Metadata-Flavor", "Google")
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                logger.warning("Metadata token request failed: HTTP " + response.statusCode());
                return null;
            }

            JSONObject json = JSONObject.fromObject(response.body());
            String token = json.optString("access_token", "");
            if (token.isEmpty()) {
                logger.warning("Metadata token response has no access_token");
                return null;
            }

            long expiresIn = json.optLong("expires_in", 300L);
            cachedToken = token;
            cachedExpiresAt = now + expiresIn * 1000L;
            return token;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.WARNING, "Interrupted while fetching metadata token", e);
            return null;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Cannot fetch token from metadata server " + url, e);
            return null;
        }
    }
}
