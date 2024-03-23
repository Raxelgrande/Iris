echo "Building Iris"
scalac iris/*.scala iris/*/*.scala lib/*/*.scala -d test.jar && echo "Running Iris" && scala test.jar && rm test.jar
