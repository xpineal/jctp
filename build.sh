set -v
cd swig
./build.sh
cd ../cpp
./build.sh
cd ../java
rm -rf build
rm -rf .gradle
./gradlew jar