FROM java:8
MAINTAINER sharkey
ADD sharkey-0.0.1-SNAPSHOT.jar sharkey.jar
EXPOSE 9100
ENTRYPOINT ["java","-jar","/sharkey.jar"]