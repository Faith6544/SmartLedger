@echo off
echo Compiling SmartLedger...

if not exist out mkdir out

javac -cp "lib\*" -d out src\model\*.java src\database\*.java src\parser\*.java src\commands\*.java src\dashboard\*.java src\gui\*.java src\App.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Starting SmartLedger...
java -cp "out;lib\*" App
