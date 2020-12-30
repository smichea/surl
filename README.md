# surl project

## Name

surl - receive a URL

## Synopsis

surl \[Options / URLs\]

## Description

surl is a tool to receive data from a client, test if it is what we expect and send back some response (for HTTP).

It is the complement to curl in the sense that if you have a curl command, starting first the
same surl command on the destination server will allow to log the request sent by curl, check it is valid and send a response. 
