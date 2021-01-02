# surl project

## Name

surl - receive a URL

## Synopsis

surl \[Options / URLs\]

## Description

surl is a tool to receive data from a client, test if it is what we expect and send back some response (for HTTP).

It is the complement to curl in the sense that if you have a curl command, starting first the
same surl command on the destination server will allow to log the request sent by curl, check it is valid and send a response. 

# Compilation of binaries

## Get the source code
On a linux system clone this repo
```
cd /home
# git clone https://github.com/smichea/surl.git
```

## Compile the jar

Make sure you have Java and Maven installed 
```
# echo $JAVA_HOME
/usr/lib/jvm/java-11-openjdk-amd64/
# echo $MAVEN_HOME
/usr/share/maven
```
if not install jdk11 and maven with apt-get
```
apt-get install openjdk-11-jdk
apt-get install maven
```
and set the env variables `JAVA_HOME` and `MAVEN_HOME`

Compile the project
```
cd surl
mvn install
```

## Compile the binaries

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
mv graalvm-ce-java11-20.3.0/ /usr/lib/jvm/
rm graalvm-ce-java11-linux-amd64-20.3.0.tar.gz
cd /usr/lib/jvm/
ln -s graalvm-ce-java11-20.3.0 graalvm
```
add the graalVm as an alternative jvm
first check how many alternative you have already
```
# update-alternatives --config java
There is only one alternative in link group java (providing /usr/bin/java): /usr/lib/jvm/java-11-openjdk-amd64/bin/java
Nothing to configure.

```
then add the graalvm one (change 1 at the end to 2,3,.. if you have more than one jvm already listed in the alternatives)
```
sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/graalvm/bin/java 1
```
switch to graalvm
```
# update-alternatives --config java
There are 2 choices for the alternative java (providing /usr/bin/java).

  Selection    Path                                         Priority   Status
------------------------------------------------------------
* 0            /usr/lib/jvm/java-11-openjdk-amd64/bin/java   1111      auto mode
  1            /usr/lib/jvm/graalvm/bin/java                 1         manual mode
  2            /usr/lib/jvm/java-11-openjdk-amd64/bin/java   1111      manual mode

Press <enter> to keep the current choice[*], or type selection number: 1
```
and check the version of java
```
# java -version
openjdk version "11.0.9" 2020-10-20
OpenJDK Runtime Environment GraalVM CE 20.3.0 (build 11.0.9+10-jvmci-20.3-b06)
OpenJDK 64-Bit Server VM GraalVM CE 20.3.0 (build 11.0.9+10-jvmci-20.3-b06, mixed mode, sharing)
```

get `native-image` tool
```
/usr/lib/jvm/graalvm/bin/gu install native-image
```

compile the jar to native image
```
cd /home/surl/target
/usr/lib/jvm/graalvm/bin/native-image --static -jar surl-1.0.jar
```

if you get an error 137
```
Error: Image build request failed with exit status 137
```
Free some memory (like stopping docker images)

right now the compilation fails with the error :
```
/usr/bin/ld: cannot find -lz
collect2: error: ld returned 1 exit status
        at com.oracle.svm.hosted.image.NativeBootImageViaCC.handleLinkerFailure(NativeBootImageViaCC.java:463)
        at com.oracle.svm.hosted.image.NativeBootImageViaCC.write(NativeBootImageViaCC.java:430)
        at com.oracle.svm.hosted.NativeImageGenerator.doRun(NativeImageGenerator.java:680)
        at com.oracle.svm.hosted.NativeImageGenerator.lambda$run$0(NativeImageGenerator.java:471)
        at java.base/java.util.concurrent.ForkJoinTask$AdaptedRunnableAction.exec(ForkJoinTask.java:1407)
        at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:290)
        at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1020)
        at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:1656)
        at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1594)
        at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:183)
Error: Image build request failed with exit status 1

```
