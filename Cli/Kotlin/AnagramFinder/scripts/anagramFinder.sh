#!/bin/bash
set -euo pipefail
IFS=$'\n\t'

java -jar lib/AnagramFinder.jar $@
