@echo off
echo Cleaning previous builds...
rmdir /s /q bin
mkdir bin

echo Compiling Java files...
javac -Xlint:unchecked -d bin -sourcepath src main/*.java controllers/*.java views/*.java models/*.java utils/*.java

if %errorlevel% neq 0 (
    echo Compilation failed.
    exit /b %errorlevel%
)

echo Running the application...
java -cp bin main.HMSApplication