#!/bin/sh
java -jar target/jcoinche-server.jar > test_server_2.txt &
sleep 5
java -jar target/jcoinche-client.jar localhost 9123 > test_client1.txt &
java -jar target/jcoinche-client.jar localhost 9123 > test_client2.txt &
java -jar target/jcoinche-client.jar localhost 9123 > test_client3.txt &
java -jar target/jcoinche-client.jar localhost 9123 > test_client4.txt &
sleep 5
pkill java

./server_charge.sh