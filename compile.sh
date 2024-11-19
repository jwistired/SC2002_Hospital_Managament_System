#!/bin/bash

echo "Cleaning previous builds..."
rm -rf bin
mkdir bin

echo "Compiling Java files with detailed warnings..."
javac -Xlint:unchecked -d bin -sourcepath src main/*.java controllers/*.java views/*.java models/*.java utils/*.java

if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

echo "Running the application..."
java -cp bin main.HMSApplication