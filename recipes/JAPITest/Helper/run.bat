@echo off

REM package
cmd /c mvn clean package
REM copy dependencies
cmd /c mvn dependency:copy-dependencies -DoutputDirectory=lib

pause