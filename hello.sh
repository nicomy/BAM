#!/bin/bash
java -Djava.security.policy="./server.policy" -Djava.security.manager -DLEVEL=ALL -jar all-jar/test.jar Repository/Configurations/hello.server2.xml server2 &

java -Djava.security.policy="./server.policy" -Djava.security.manager -DLEVEL=ALL -jar all-jar/test.jar Repository/Configurations/hello.server1.xml server1 &

java -Djava.security.policy="./server.policy" -Djava.security.manager -DLEVEL=ALL -jar all-jar/test.jar Repository/Configurations/client.client1.xml client1 &
