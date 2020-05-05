#!/bin/sh
java -jar target/jcoinche-server.jar > test_server_1.txt &
sleep 5
telnet localhost 9123 > test_telnet1.txt &
telnet localhost 9123 > test_telnet2.txt &
telnet localhost 9123 > test_telnet3.txt &
telnet localhost 9123 > test_telnet4.txt &

sleep 5
pkill java
./client_connection.sh