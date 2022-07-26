#!/usr/bin/env
# Docker entrypoint command
set -o pipefail

# if the env var DB_URL is not empty
if [ ! -z "${PG_URL}" ]; then
   echo "url=${PG_URL}" >> app/src/main/resources/application.yml
fi
# do the same for other props
#...
#exec call-the-main-entry-point-here $@

java -jar /app/knighttodo.jar
