FROM ubuntu:latest

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven && \
    apt-get clean;

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV MAVEN_HOME=/usr/share/maven
ENV PATH=$PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin

WORKDIR /app

COPY . /app

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/finflow-back-end-0.0.1-SNAPSHOT.jar"]