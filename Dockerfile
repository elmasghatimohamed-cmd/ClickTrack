
FROM tomcat:10.1-jre17-temurin

COPY target/clicktrack.war /usr/local/tomcat/webapps/clicktrack.war
