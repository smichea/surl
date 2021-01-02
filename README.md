# surl project

## Name

surl - receive a URL

## Synopsis

surl \[Options / URLs\]

## Description

surl is a tool to receive data from a client, test if it is what we expect and send back some response (for HTTP).

It is the complement to curl in the sense that if you have a curl command, starting first the
same surl command on the destination server will allow to log the request sent by curl, check it is valid and send a response. 

## compilation of binaries
assumes you used maven to compile the uberjar surl-xx.jar

Install graalVM
```
wget https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-20.3.0/graalvm-ce-java11-linux-amd64-20.3.0.tar.gz
```
untar 
```
tar -xvzf graalvm-ce-java11-linux-amd64-20.3.0.tar.gz
```
Move the unpacked dir to `/usr/lib/jvm/` and create a symbolic link to make your life easier when updating the GraalVM version:
```
mv graalvm-ce-java11-linux-amd64-20.3.0/ /usr/lib/jvm/

```
