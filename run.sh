echo "Building Iris"
scalac src/*.scala src/*/*.scala -d test.jar && echo "Running Iris" && scala test.jar && rm test.jar
