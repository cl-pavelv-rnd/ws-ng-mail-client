FROM openjdk:8-jdk-alpine
MAINTAINER Pavel Veksler <cl_pavelv@rnd-hub.com>

# Add the profiling agent
RUN wget https://www.yourkit.com/download/docker/YourKit-JavaProfiler-2021.3-docker.zip -P /tmp/ && \
  unzip /tmp/YourKit-JavaProfiler-2021.3-docker.zip -d /usr/local && \
  rm /tmp/YourKit-JavaProfiler-2021.3-docker.zip

RUN apk add --no-cache libc6-compat

ENV LD_LIBRARY_PATH=/lib64

EXPOSE 10001

# Add the service itself
ARG JAR_FILE
COPY target/${JAR_FILE} /usr/share/myservice/myservice.jar

EXPOSE 8206
ENTRYPOINT ["/usr/bin/java", "-agentpath:/usr/local/YourKit-JavaProfiler-2021.3/bin/linux-x86-64/libyjpagent.so=port=10001,listen=all", "-jar", "/usr/share/myservice/myservice.jar"]
