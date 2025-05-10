FROM 101314775920.dkr.ecr.ap-northeast-2.amazonaws.com/java:corretto-17.0.8

COPY build/libs/* application.jar

CMD ["java", "-Dfile.encoding=UTF-8", "-jar", "application.jar"]