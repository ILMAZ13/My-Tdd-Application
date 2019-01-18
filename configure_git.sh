#!/usr/bin/env bash
read -r -p "All your local changes can be lost. Are you sure? [y/N] " response
if [[ "$response" =~ ^([yY][eE][sS]|[yY])+$ ]]
then
    git config filter.private.clean  "openssl aes-256-cbc -e -md sha256 -nosalt -a -pass pass:$SECURE_PASSWORD"
    git config filter.private.smudge "openssl aes-256-cbc -d -md sha256 -nosalt -a -pass pass:$SECURE_PASSWORD"
    git config diff.private.textconv "openssl aes-256-cbc -d -md sha256 -nosalt -a -pass pass:$SECURE_PASSWORD"
    git stash save
    rm .git/index
    git checkout HEAD -- "$(git rev-parse --show-toplevel)"
    git stash pop
    echo Done.
else
    echo Rejected.
fi