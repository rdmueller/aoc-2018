./gradlew clean
cd docs/groovy
unzip apache-groovy-binary-2.5.4.zip
cd ..
./generateIndex.groovy
cd ..
./gradlew generateHTML
echo 0