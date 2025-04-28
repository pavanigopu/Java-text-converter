@echo off
setlocal

:: Set the input file name
set INPUT=../resources/small.in

:: Extract filename without extension
for %%F in (%INPUT%) do set BASENAME=%%~dpnF

:: Run the Java program
java -Xmx32m -jar ..\target\text-converter-1.0-SNAPSHOT.jar %INPUT%

:: Confirm output
if exist %BASENAME%.xml (
    echo Successfully created %BASENAME%.xml and %BASENAME%.csv
) else (
    echo Something went wrong. Check for errors above.
)

pause
