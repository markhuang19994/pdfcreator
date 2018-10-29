#! /bin/bash
RUN_FILE_NAME="pdfc"
cat createExecutionFile.sh "./$1" > "$RUN_FILE_NAME"  && chmod 755 "$RUN_FILE_NAME"
exit 0
