#!/bin/bash

while [ : ]
do
	time=$(date +"%T")
	curl --header "Content-type: application/json" --request POST --data "{\"message\":\"test message\",\"senderId\":1,\"receiverId\":0,\"sendToGroup\":false,\"tag\":\"test tag\",\"time\":\""${time}"\",\"priority\":1}" http://localhost:9000/send
	sleep 5
done
