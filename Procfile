web: ./get_env.sh && export $(grep -v '^#' .env | xargs)
web: java $JAVA_OPTS -Dserver.port=$PORT -jar target/*.jar
