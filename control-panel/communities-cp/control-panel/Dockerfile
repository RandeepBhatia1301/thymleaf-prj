FROM openjdk:8-jre-alpine
MAINTAINER Anshika Mittal <anshika.mittal@webdunia.com>
ADD target/control-panel-1.0-SNAPSHOT.jar control-panel.jar
ENTRYPOINT exec java -Xms128m -Xmx256m -XX:MaxMetaspaceSize=128m -XX:CompressedClassSpaceSize=256m -Xss256k -Xmn8m -XX:InitialCodeCacheSize=4m -XX:ReservedCodeCacheSize=128m -XX:MaxDirectMemorySize=16m  -XX:MinHeapFreeRatio=10 -XX:MaxHeapFreeRatio=70 -jar /control-panel.jar
EXPOSE 9010