#!/bin/sh

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'
i=0
echo "${GREEN}Here we go the traces for the server test connection\n"
echo "${RED}Test Server Traces :\n${NC}"
cat test_server_1.txt 2>> /dev/null
if [ $? -eq 0 ]
then
    i=$((i+1))
fi
echo "\n${RED}Test Telnet 1 Traces :\n${NC}"
cat test_telnet1.txt 2> /dev/null
if [ $? -eq 0 ]
then
   i=$((i+1))
fi
echo "\n${RED}Test Telnet 2 Traces :\n${NC}"
cat test_telnet2.txt 2> /dev/null
if [ $? -eq 0 ]
then
  i=$((i+1))
fi

echo "\n${RED}Test Telnet 3 Traces :\n${NC}"
cat test_telnet3.txt 2> /dev/null
if [ $? -eq 0 ]
then
    i=$((i+1))
fi

echo "\n${RED}Test Telnet 4 Traces :\n${NC}"
cat test_telnet4.txt 2> /dev/null
if [ $? -eq 0 ]
then
    i=$((i+1))
fi



echo "\n${GREEN}Here we go the traces for the client test connection\n"
echo "${RED}Test Server 2 Traces :\n${NC}"
cat test_server_2.txt 2> /dev/null
if [ $? -eq 0 ]
then
   i=$((i+1))
fi

echo "\n${RED}Test Client 1 Traces :\n${NC}"
cat test_client1.txt 2> /dev/null
if [ $? -eq 0 ]
then
   i=$((i+1))
fi

echo "\n${RED}Test Client 2 Traces :\n${NC}"
cat test_client2.txt 2> /dev/null
if [ $? -eq 0 ]
then
   i=$((i+1))
fi

echo "\n${RED}Test Client 3 Traces :\n${NC}"
cat test_client3.txt 2> /dev/null
if [ $? -eq 0 ]
then
  i=$((i+1))
fi

echo "\n${RED}Test Client 4 Traces :\n${NC}"
cat test_client4.txt 2> /dev/null
if [ $? -eq 0 ]
then
    i=$((i+1))
fi
echo "\n${GREEN}Here we go the traces for test charge server\n"
echo "${RED}Test charge 1 Traces 122 Client :\n${NC}"
cat test_server4.txt 2> /dev/null
cat test_charge_full2.txt 2> /dev/null

echo "${RED}Test charge 1 Traces 628 Client :\n${NC}"
cat test_server5.txt 2> /dev/null
cat test_charge_full1.txt 2> /dev/null

echo "${NC}Covering test percentage :"
if [ $i -gt 0 ]
then
i=$((i * i))
fi
if [ $i -eq 100 ]
then
echo  "${GREEN}$i%"
fi
if [ $i -lt 100 ]
then
echo  "${RED}$i%"
fi
rm test_*.txt 2>> /dev/null