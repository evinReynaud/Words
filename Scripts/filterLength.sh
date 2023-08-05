#!/bin/bash
set -euo pipefail
IFS=$'\n\t'

if [ "$#" -lt 2 ]; then
  echo "error: please provide a filename and word length"
  exit 1
fi

OUTPUT_FILE=${3:-"$1.len$2"}

sed -nr "/^.{$2}$/p" $1 > $OUTPUT_FILE
