dir_iris="iris/*.scala iris/*/*.scala"
dir_setup="setup-iris/*.scala setup-iris/*/*.scala"
dir_lib="lib/*/*.scala"

echo "Building Iris"
scalac $dir_iris $dir_lib -d iris.jar

echo "Building setup-iris"
scalac $dir_setup $dir_lib -d setup-iris.jar
