# radioactive-parameter-plugin

## Introduction

Dropdown choice parameter that allow user to show/hide other parameters for each option (dynamic parameters).

## Getting started

Jenkins DSL use:
```
radioactiveParameter {

	name(String value)
	
	description(String value)
	
	// Set default selected options.
	selectedOption(String value)
	
	// List all options and enabled parameters for each option (comma separated).
	options {
		option {
			// Options name.
			optionName(String value)
			// Comma separated list of enabled parameters name.
			enabledParameters(String value)
		}
	}

}
```

## image-tag-parameter (tiket fork)

The `image-tag-parameter.hpi` attached to this repo's releases is a tiket fork of
[jenkinsci/image-tag-parameter-plugin](https://github.com/jenkinsci/image-tag-parameter-plugin).
Its source is in [`image-tag-parameter/`](image-tag-parameter/): upstream `d48ad694` plus tiket changes
(GCR / Artifact Registry auth, GKE metadata token fallback). Build it with:

```
cd image-tag-parameter && mvn package    # -> target/image-tag-parameter.hpi
```

| Release | image-tag-parameter | Notes |
|---------|---------------------|-------|
| v2.0.0  | 4.0-SNAPSHOT | GCR unquoted `service`, challenge logging |
| v2.1.0  | 4.1 | GKE metadata server token fallback |
| v2.2.0  | 4.2 | Bearer challenge without `service` (Artifact Registry `*-docker.pkg.dev`) |

## Contributing

TODO review the default [CONTRIBUTING](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md) file and make sure it is appropriate for your plugin, if not then add your own one adapted from the base file

Refer to our [contribution guidelines](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md)

