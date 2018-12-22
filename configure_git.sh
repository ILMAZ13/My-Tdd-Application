#!/usr/bin/env bash
git config filter.private.clean  "openssl enc -e -aes-256-cbc -md md5 -nosalt -a -pass pass:$SECURE_PASSWORD"
git config filter.private.smudge "openssl enc -d -aes-256-cbc -md md5 -nosalt -a -pass pass:$SECURE_PASSWORD || cat"
git config diff.private.textconv "openssl enc -d -aes-256-cbc -md md5 -nosalt -a -pass pass:$SECURE_PASSWORD || cat"
git reset --hard HEAD
