#!/bin/bash
rm .env
touch .env
db_url=$(heroku config:get DATABASE_URL -a csci4448-library)
ds_url=$(echo "$db_url" | sed 's/.*@//')
ds_pass=$(echo "$db_url" | sed -e 's/\(^.*:\)\(.*\)\(@.*$\)/\2/')
ds_user=$(echo "$db_url" | sed -e 's/\(^.*\/\)\(.*\)\(:.*:.*$\)/\2/')

echo -n "DATABASE_URL=" >> .env
echo "$db_url" >> .env

echo -n "SPRING_DATASOURCE_URL=jdbc:postgresql://" >> .env
echo "$ds_url" >> .env

echo -n "SPRING_DATASOURCE_PASSWORD=" >> .env
echo "$ds_pass" >> .env

echo -n "SPRING_DATASOURCE_USERNAME=" >> .env
echo "$ds_user" >> .env
