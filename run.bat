@echo off
echo Compiling SmartLedger...

if not exist out mkdir out

javac -cp "lib\*" -d out src\model\*.java src\database\*.java src\parser\*.java src\commands\*.java src\dashboard\*.java src\gui\*.java src\App.java src\WebApp.java

if %errorlevel% neq 0 (
    echo.
    echo Compilation failed! Check errors above.
    pause
    exit /b 1
)

echo Compilation successful!
echo Starting SmartLedger...
echo.
echo Desktop app will open shortly.
echo Web login:  http://localhost:8080/auth/login
echo Landing:    http://localhost:8080/
echo.

java -Xmx512m -cp "out;lib\*" App
