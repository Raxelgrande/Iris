echo "Building Linux native binary (glibc, static, x86_64)"
native-image --no-fallback --static -O3 -jar iris-java.jar -o iris

echo "Packaging Linux native binary"
7z a -mx7 -mmt0 iris-linux-x86_64.zip iris
