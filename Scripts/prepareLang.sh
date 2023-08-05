#!/bin/bash
set -euo pipefail
IFS=$'\n\t'

if [ "$#" -lt 1 ]; then
  echo "error: please provide a language"
  exit 1
fi

IN_PATH=../Words/
OUT_PATH=../Words/formatted/

./format.sh "$IN_PATH$1.txt" "$OUT_PATH$1.fmt"

for i in $(seq 3 8);
do
    ./filterLength.sh "$OUT_PATH$1.fmt" $i "$OUT_PATH$1.len$i"
done

