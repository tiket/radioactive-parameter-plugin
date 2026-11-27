package io.jenkins.plugins.luxair;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ImageTagTest {

    @Test
    void parsesQuotedService() {
        // Docker Hub style
        assertArrayEquals(
            new String[]{"Bearer", "https://auth.docker.io/token", "registry.docker.io"},
            ImageTag.parseAuthenticateHeader(
                "Bearer realm=\"https://auth.docker.io/token\",service=\"registry.docker.io\""));
    }

    @Test
    void parsesUnquotedService() {
        // GCR style
        assertArrayEquals(
            new String[]{"Bearer", "https://asia.gcr.io/v2/token", "asia.gcr.io"},
            ImageTag.parseAuthenticateHeader(
                "Bearer realm=\"https://asia.gcr.io/v2/token\",service=asia.gcr.io"));
    }

    @Test
    void parsesRealmWithoutService() {
        // Artifact Registry style: no service in the challenge
        assertArrayEquals(
            new String[]{"Bearer", "https://asia-southeast1-docker.pkg.dev/v2/token", ""},
            ImageTag.parseAuthenticateHeader(
                "Bearer realm=\"https://asia-southeast1-docker.pkg.dev/v2/token\""));
    }

    @Test
    void parsesBasic() {
        assertArrayEquals(
            new String[]{"Basic", "", ""},
            ImageTag.parseAuthenticateHeader("Basic realm=\"Registry\""));
    }

    @Test
    void rejectsBearerWithoutRealm() {
        assertArrayEquals(
            new String[]{"", "", ""},
            ImageTag.parseAuthenticateHeader("Bearer service=\"registry.example.com\""));
    }
}
