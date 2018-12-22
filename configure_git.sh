#!/usr/bin/env bash
git config filter.private.clean  "openssl enc -e -aes-256-cbc -md sha256 -nosalt -a -pass pass:$SECURE_PASSWORD"
git config filter.private.smudge "openssl enc -d -aes-256-cbc -md sha256 -nosalt -a -pass pass:$SECURE_PASSWORD"
git config diff.private.textconv "openssl enc -d -aes-256-cbc -md sha256 -nosalt -a -pass pass:$SECURE_PASSWORD"
git stash save
rm .git/index
git checkout HEAD -- "$(git rev-parse --show-toplevel)"
git stash pop
