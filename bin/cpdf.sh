#! /bin/bash

SH_FILE=$(readlink -f "$0")
SH_DIR=$(dirname "${SH_FILE}")

echo sh_file: "$SH_FILE"
echo sh_dir : "$SH_DIR"

java -jar "$SH_DIR"/../pdf-creator-%PROJECT_VERSION%.jar "$@"
