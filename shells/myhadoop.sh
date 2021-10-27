#！/bin/bash
if [ $# -lt 1 ]; then
	echo 'agrs less than 1'
fi
case $1 in
	'start' )
		echo " =================== start hadoop cluster ==================="
        	echo " --------------- start hdfs ---------------"
        		ssh hadoop101 "/opt/module/hadoop-3.1.3/sbin/start-dfs.sh"
        	echo " --------------- start yarn ---------------"
        		ssh hadoop102 "/opt/module/hadoop-3.1.3/sbin/start-yarn.sh"
       	 	echo " --------------- start historyserver ---------------"
        		ssh hadoop101 "/opt/module/hadoop-3.1.3/bin/mapred --daemon start historyserver"
		;;
	'stop' )
		echo " =================== stop hadoop cluster ==================="
		echo " --------------- stop historyserver ---------------"
    			ssh hadoop101 "/opt/module/hadoop-3.1.3/bin/mapred --daemon stop historyserver"
    		echo " --------------- stop yarn ---------------"
    			ssh hadoop102 "/opt/module/hadoop-3.1.3/sbin/stop-yarn.sh"
        	echo " --------------- stop hdfs ---------------"
        		ssh hadoop101 "/opt/module/hadoop-3.1.3/sbin/stop-dfs.sh"
		;;
	* )
			echo 'args not correct!'
		;;
esac
