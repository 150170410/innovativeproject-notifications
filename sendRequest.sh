
#!/bin/bash

while [ : ]
do
	time=$(date +"%T")
	curl --header "Content-type: application/json" -X POST --data "{\"message\":\""${time}"\n\"}" http://localhost:9000
	sleep 5
done

