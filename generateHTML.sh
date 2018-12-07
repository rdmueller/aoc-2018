./gradlew clean
cd docs
mkidr groovy
cd groovy
wget https://bintray.com/artifact/download/groovy/maven/apache-groovy-binary-2.5.4.zip
unzip apache-groovy-binary-2.5.4.zip
cd ..
./generateIndex.groovy
cd ..
./gradlew generateHTML
echo 0