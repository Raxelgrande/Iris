echo "Building lightweight JAR"
scalac iris/*.scala iris/*/*.scala -d iris.jar
scalac setup-iris/*.scala setup-iris/*/*.scala -d setup-iris.jar

echo "Building Fat JAR"
steam-run scala-cli --power package iris --assembly --preamble=false --jvm 11 -o iris-java.jar
steam-run scala-cli --power package setup-iris --assembly --preamble=false --jvm 11 -o setup-iris-java.jar
