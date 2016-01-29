#!/bin/bash
rm -Rf bin
mkdir bin
javac -d ./bin/ -cp ./bin/ ./src/es/arcri/arborium/*.java
java -cp ./bin/ es.arcri.arborium.Test
