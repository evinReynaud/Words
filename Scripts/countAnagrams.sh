#!/bin/bash
set -euo pipefail
IFS=$'\n\t'

if [ "$#" -lt 1 ]; then
  echo "error: please provide a file"
  exit 1
fi

OUTPUT_FILE=${2:-"$1.cnt"}

echo -n '' > $OUTPUT_FILE

while read LINE; do
 echo $LINE | grep -o . | sort | tr -d "\n" >> $OUTPUT_FILE
 echo "" >> $OUTPUT_FILE
done < $1

sort $OUTPUT_FILE | uniq -c | sort -gro $OUTPUT_FILE
