# How to run

obtain .env file from Tuqa or Nikhil, move to Library directory, then:
`export $(grep -v '^#' .env | xargs)`
after environment variables set, run:
`./mvnw spring-boot:run`

