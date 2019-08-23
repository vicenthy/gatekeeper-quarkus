# gatekeeper-quarkus
aplicação de exemplo utilizando quarkus como api para autenficar no github

First configure de file application.properties with your github application

Build with maven


```
mvn clean install

``` 
Run de Application

```
java -jar target/gatekeeper4j-1.0-SNAPSHOT-runner.jar

```


Build to native *before read on bottom how to install graalVM

```
mvn clean install -Dnative 

```

Run native Application

```
./target/gatekeeper4j-1.0-SNAPSHOT-runner

```


## How to Install GraalVM Community Edition on Linux

1) Download the [new release of GraalVM](https://github.com/oracle/graal/releases) and unpack it anywhere in your filesystem:

```
$ tar -xvzf graalvm-ce-1.0.0-rc14-linux-amd64.tar.gz
```

2) Move the unpacked dir to `/usr/lib/jvm/` and create a symbolic link to make your life easier when updating the GraalVM version:

```
# mv graalvm-ce-1.0.0-rc14/ /usr/lib/jvm/
# cd /usr/lib/jvm
# ln -s graalvm-ce-1.0.0-rc14 graalvm
```

3) Add a new alternatives configuration. First grab the priorization number by listing the already installed JVMs and then use this number to configure the new one:

```
# alternatives --config java

There are 3 programs which provide 'java'.

  Selection    Command
-----------------------------------------------
   1           java-11-openjdk.x86_64 (/usr/lib/jvm/java-11-openjdk-11.0.2.7-0.fc29.x86_64/bin/java)
*+ 2           java-1.8.0-openjdk.x86_64 (/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.201.b09-2.fc29.x86_64/jre/bin/java)
   3           java-openjdk.x86_64 (/usr/lib/jvm/java-12-openjdk-12.0.0.33-1.ea.1.rolling.fc29.x86_64/bin/java)
```

In this case I have 3 java alternatives installed, so I'm going to install the fourth.

```
# alternatives --install /usr/bin/java java /usr/lib/jvm/graalvm/bin/java 4
```

## Testing

To make sure everything is working fine, set the new JVM on your environment:

```
[ricferna@skywalker Downloads]$ sudo alternatives --config java

There are 4 programs which provide 'java'.

  Selection    Command
-----------------------------------------------
   1           java-11-openjdk.x86_64 (/usr/lib/jvm/java-11-openjdk-11.0.2.7-0.fc29.x86_64/bin/java)
*+ 2           java-1.8.0-openjdk.x86_64 (/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.201.b09-2.fc29.x86_64/jre/bin/java)
   3           java-openjdk.x86_64 (/usr/lib/jvm/java-12-openjdk-12.0.0.33-1.ea.1.rolling.fc29.x86_64/bin/java)
   4           /usr/lib/jvm/graalvm/bin/java

Enter to keep the current selection[+], or type selection number: 4
```

To verify, just check the version number:

```
[ricferna@skywalker Downloads]$ java -version
openjdk version "1.8.0_202"
OpenJDK Runtime Environment (build 1.8.0_202-20190206132807.buildslave.jdk8u-src-tar--b08)
OpenJDK GraalVM CE 1.0.0-rc14 (build 25.202-b08-jvmci-0.56, mixed mode)
```

And you're set.

## Note

The native-image executable is not bundled in the GraalVM distribution anymore. Install it manually using `$GRAALVM_HOME/bin/gu install native-image`.

