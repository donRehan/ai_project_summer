#!/bin/bash

javac -d bin/ src/*.java

if [ $# -eq 0 ]
then
	echo "No arguments given"
else
	java -cp bin/ $1
fi
