
Make sure [Skybar](https://github.com/WinterTechForum/skybar) is built first:

```
cd skybar
./gradlew
```

Then run the demo:
```
cd skybar-demo
mvn clean package
java -javaagent:../skybar/build/libs/skybar-1.0-SNAPSHOT-all.jar \
  -Dskybar.includedPackages=com/skybar/demo \
  -jar target/skybar-demo-1.0-SNAPSHOT-jetty-console.war --headless

```
or using Maven:

```
mvn jetty:run-forked
```
