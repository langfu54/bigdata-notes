#! /bin/bash

case $1 in
"start"){
    for i in hadoop101 hadoop102 hadoop103
    do
        echo " --------start $i Kafka-------"
        ssh $i "/opt/module/kafka/bin/kafka-server-start.sh -daemon /opt/module/kafka/config/server.properties"
    done
};;
"stop"){
    for i in hadoop101 hadoop102 hadoop103
    do
        echo " --------stop $i Kafka-------"
        ssh $i "/opt/module/kafka/bin/kafka-server-stop.sh stop"
    done
};;
esac
