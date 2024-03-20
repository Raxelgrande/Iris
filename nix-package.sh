echo "Building lightweight JAR"
scalac iris/*.scala iris/*/*.scala -d iris.jar
echo "Building Fat JAR"
steam-run scala-cli --power package iris --assembly --preamble=false --jvm 11 -o iris-java.jar
