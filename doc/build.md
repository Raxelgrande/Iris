# Building Iris from source

Iris requires Scala 3 to build from source. You can use the scala compiler directly, or use scala-cli.
Most of these commands are in the project's bash scripts, so if you have bash (or a compatible shell), you can run them.


# Building a lightweight JAR

You can build a lightweight JAR with this command:
```
scalac iris/*.scala iris/*/*.scala lib/*/*.scala -d iris.jar
scalac setup-iris/*.scala setup-iris/*/*.scala lib/*/*.scala -d setup-iris.jar
```
This builds Iris and the setup utility using the scala compiler. You can run them with scala:
```
scala iris.jar
scala setup-iris.jar
```

If you prefer to use Scala-CLI instead, here's the command:
```
scala-cli --power package iris lib --library -o iris.jar
scala-cli --power package setup-iris lib --library -o setup-iris.jar
```
These can only be run with Scala-CLI.

# Building an assembly JAR

With Scala-CLI, you can build an assembly JAR, which contains all the runtime and dependencies and so it can be run directly with Java:
```
scala-cli --power package iris lib --assembly --preamble=false -o iris.jar
scala-cli --power package setup-iris lib --assembly --preamble=false -o setup-iris.jar
```
You can run Iris with Java:
```
java -jar iris.jar
java -jar setup-iris.jar
```

# Building bootstrap JARs

A bootstrap works like an executable, in the sense that you can run it as if it was one.

With Scala-CLI, you can build a bootstrap, like this:
```
scala-cli --power package iris lib -o iris.jar
scala-cli --power package setup-iris lib -o setup-iris.jar
```
Having Scala in your system, you can then run it as if it was an executable:
```
./iris.jar
./setup-iris.jar
```
If you want to include all dependencies in your bootstrap and only rely on Java, then you can build the following way:
```
scala-cli --power package iris lib --assembly -o iris.jar
scala-cli --power package setup-iris lib --assembly -o setup-iris.jar
```
