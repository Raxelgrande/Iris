echo "Building Iris"
scalac setup-iris/*.scala setup-iris/*/*.scala lib/*/*.scala -d setup-iris-test.jar && echo "Running Iris" && scala setup-iris-test.jar && rm setup-iris-test.jar
