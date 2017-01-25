#!/bin/bash

while [ : ]
do
	time=$(date +"%T")
	curl --header "Content-type: application/json" --request POST --data "{\"message\":\""${time}"\",\"senderId\":1,\"receiverId\":1,\"sendToGroup\":false,\"tag\":\"facebook\",\"time\":\""${time}"\",\"priority\":1,\"agg_type\":0}" http://notifications.pl:9000/send
	sleep 5
done
