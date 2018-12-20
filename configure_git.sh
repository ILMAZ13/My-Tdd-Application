#!/usr/bin/env bash
git config --global filter.private.clean "openssl enc -aes-256-cbc -nosalt -a -k $SECURE_PASSWORD"
git config --global filter.private.smudge "openssl enc -d -aes-256-cbc -nosalt -a -k $SECURE_PASSWORD"
git config --global diff.private.textconv "openssl enc -d -aes-256-cbc -nosalt -a -k $SECURE_PASSWORD || cat"