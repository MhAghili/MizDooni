FROM openjdk:11-jre-slim

COPY elastic-apm-agent-1.50.0.jar /elastic-apm-agent.jar
COPY my-application.jar /my-application.jar

ENTRYPOINT ["java", "-javaagent:/elastic-apm-agent.jar", \
            "-Delastic.apm.service_name=my-application", \
            "-Delastic.apm.server_urls=http://localhost:8200", \
            "-Delastic.apm.secret_token=", \
            "-Delastic.apm.environment=production", \
            "-Delastic.apm.application_packages=org.example", \
            "-jar", "/my-application.jar"]
