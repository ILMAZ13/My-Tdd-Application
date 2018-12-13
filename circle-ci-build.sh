#!/bin/bash

if [[ -z "${CI_PULL_REQUEST}" ]]; then
    echo "---> Making build outside of Pull Request (building single commit or branch)"
    ./gradlew gnagCheck
else
    echo "---> Making build for Pull Request"
    # In the env variable CI_PULL_REQUEST CircleCI provides the URL of the PR (like https://github.com/amatkivskiy/sample/pull/3)
    # But for the Gnag task we need PR number (simply 3)
    # ${CI_PULL_REQUEST##*/} means that we simply get '3' from the URL provided in CI_PULL_REQUEST/
    echo "---> PR Number: ${CI_PULL_REQUEST##*/}"
    ./gradlew gnagReport -PauthToken="${PR_BOT_AUTH_TOKEN}" -PissueNumber="${CI_PULL_REQUEST##*/}"
fi
