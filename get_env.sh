#!/bin/bash
rm .env
touch .env
echo -n "DATABASE_URL=" >> .env
heroku config:get DATABASE_URL -a csci4448-library >> .env
echo -n "SPRING_DATASOURCE_URL=jdbc:postgresql://" >> .env
heroku config:get DATABASE_URL -a csci4448-library | sed 's/.*@//' >> .env
echo -n "SPRING_DATASOURCE_PASSWORD=" >> .env
heroku config:get DATABASE_URL -a csci4448-library | sed -e 's/\(^.*:\)\(.*\)\(@.*$\)/\2/' >> .env
echo -n "SPRING_DATASOURCE_USERNAME=" >> .env
heroku config:get DATABASE_URL -a csci4448-library | sed -e 's/\(^.*\/\)\(.*\)\(:.*:.*$\)/\2/' >> .env