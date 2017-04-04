#!/bin/bash
java -Djava.security.policy="./server.policy" -Djava.security.manager -DLEVEL=ALL -jar all-jar/MobilagentServer.jar Repository/Configurations/hello.server1.xml server1

