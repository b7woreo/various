#!/bin/bash
# Auto build or deploy

set -e

if ["$TRAVIS_BRANCH" == 'master' && "$TRAVIS_TAG" != ""]; then
  echo "Build for Release => Branch ["$TRAVIS_BRANCH"]  Tag ["$TRAVIS_TAG"]"
  ./gradlew -PbintrayUser="$BINTRAY_USER" -PbintrayKey="$BINTRAY_KEY" clean uploadArchives
else
  echo "Build => Branch ["$TRAVIS_BRANCH"]  Tag ["$TRAVIS_TAG"]"
  ./gradlew build
fi