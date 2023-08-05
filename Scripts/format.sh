#!/bin/bash
set -euo pipefail
IFS=$'\n\t'

if [ "$#" -lt 1 ]; then
  echo "error: please provide a filename"
  exit 1
fi

OUTPUT_FILE=${2:-"$1.fmt"}

iconv -f utf8 -t ascii//TRANSLIT $1 | sed -n '/^[a-zA-Z]*$/p' | tr '[a-z]' '[A-Z]' | sort -u -o $OUTPUT_FILE
