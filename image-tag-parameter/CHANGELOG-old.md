Changelog
===

The CHANGELOG is now maintained on the GitHub releases page: [Releases](https://github.com/jenkinsci/image-tag-parameter-plugin/releases)

## v2.0 (Jul 23, 2022)
* Add option to disable SSL verification for self-signed registry certificates
* Bump credentials from 2.5 to 1139.veb_9579fca_33b_
* Bump unirest-java from 3.11.11 to 3.13.10 
* Bump release-drafter/release-drafter from 5.15.0 to 5.20.0
* Bump plugin from 3.50 to 4.43.1
* Bump jenkins version from 2.164.3 to 2.346.1

## v1.10.0 (Mar 22, 2021)
* Handle AWS ECR credentials (#28) @lancehudson
* fix(ordering): In rare ocassions TagOrder was null and lead to unexpected behaviour (#25) @h1dden-da3m0n
* authorize space in realm service name - fix JENKINS-65073 - (#49) @maduma
* Update: Release Drafter config (depends on #14) (#15) @h1dden-da3m0n
* Fix: dependabot config (#14) @h1dden-da3m0n
* Bump release-drafter/release-drafter from v5.11.0 to v5.15.0 (#50) @dependabot
* Bump unirest-java from 3.3.00 to 3.11.11 (#45) @dependabot

## v1.9.0 (Sep 06, 2020)
* Ignore case sensitivity on authentication type checking

## v1.8.2 (Jul 28, 2020)
* fix non resetting Error Message

## v1.8.1 (Jul 27, 2020)
* allow '/' character in image name (needed for google registries)

## v1.8 (Jul 26, 2020)
* change to improve ordering of tag values for parameter
* add option to revere ordering
* add default credential used for the default registry
* change to move registry, credential and reverseOrder option into advanced configuration
* fix JobDSL API breaking by requiring new optional properties for parameter creation (regression from v1.6)

## v1.7 (Jul 10, 2020)
* add support for basic authorization type (for repositories like AWS ECR and Registry (self hosted))

## v1.6 (Jun 25, 2020)
* Add support for default value
* Add additional envVar export to get imageTag without image name

## v1.5 (Jun 15, 2020)
* Fix pagination in HTML for display

## v1.4 (Jun 01, 2020)
* Compatibility with Pipeline

## v1.3 (Jun 01, 2020)
* For compatibility with OAuth 2.0, we will also accept token under the name access_token

## v1.2 (Dec 30, 2019)
* Possibility to set a default registry in global configuration

## v1.1.1 (Dec 19, 2019)
* Change pom.xml groupId to org.jenkins-ci.plugins

## v1.1 (Dec 19, 2019)
* Add unirest error inteceptor for handling hostname and connection error

## v1.0 (Dec 17, 2019)
* Initial Release
