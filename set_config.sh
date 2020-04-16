#!/bin/bash
db_url=$(heroku config:get DATABASE_URL -a csci4448-library)
ds_url=$(echo "$db_url" | sed 's/.*@//')
ds_pass=$(echo "$db_url" | sed -e 's/\(^.*:\)\(.*\)\(@.*$\)/\2/')
ds_user=$(echo "$db_url" | sed -e 's/\(^.*\/\)\(.*\)\(:.*:.*$\)/\2/')

heroku config:set SPRING_DATASOURCE_URL="jdbc:postgresql://$ds_url" -a csci4448-library
heroku config:set SPRING_DATASOURCE_PASSWORD="$ds_pass" -a csci4448-library
heroku config:set SPRING_DATASOURCE_USERNAME="$ds_user" -a csci4448-library
