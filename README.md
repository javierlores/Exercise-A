# Exercise-A
-----------

This program employs an executor service to run three threads to count in a specified range.

## Compile
-----------
javac src/ThreadedCounter.java
javac -cp .:test/junit.jar:test/hamcrest.jar test/ThreadedCounterTests.java test/ThreadedCounterTestsRunner.java

## Run Integration Tests
-----------
java -cp .:test/junit.jar:test/hamcrest.jar test/ThreadedCounterTestsRunner

## Documentation
-----------
javadoc -cp .:test/junit.jar -d javadoc src test

## Build Runnable Jar
-----------
jar -cvfm threadedcounter.jar manifest.txt src/*.class

## Execute Runnable Jar
-----------
java -jar threadedcounter.jar



