@echo off

REM set MAVEN_OPTS environment
set MAVEN_OPTS=-Xms128m -Xmx1024m -XX:PermSize=64m -XX:MaxPermSize=640m
REM compile && package Helper project
cd Helper
cmd /c mvn clean package
cmd /c mvn dependency:copy-dependencies -DoutputDirectory=lib
cd ..
REM generate TestProject according to TestCase
cd TestCase
cmd /c mvn compile
cd ..

pause