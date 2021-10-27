#!/bin/bash
if [ $# -lt 1 ]; then
	echo 'args less than 1'
fi
case $1 in
	'start' )
		for i in hadoop101 hadoop102 hadoop103; do
		echo ==============$i=============
		ssh $i "/opt/module/zookeeper/bin/zkServer.sh start"
		done
		;;
'stop' )
		for i in hadoop101 hadoop102 hadoop103; do
		echo ==============$i=============
		ssh $i '/opt/module/zookeeper/bin/zkServer.sh stop'
		done
		;;
* )
		echo 'args not correct!'
		;;
esac
