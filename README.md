cbt-ws-dao
==========

Project setup

1. Generate JOOQ libraries:
Use /src/main/resources/cbt.sql to create required database
set appropriate credentials in /jooq-config.xml
launch class: org.jooq.util.GenerationTool with argument: /jooq-config.xml

2. Fix dependencies
Library for parsing DEX files (https://code.google.com/p/smali/) is not available in MAVEN CENTRAL, copy is included in /lib folder
Add lib\baksmali-1.4.2.jar to local maven repository with:
mvn install:install-file -Dfile=lib\baksmali-1.4.2.jar -DgroupId=org.jf.dexlib -DartifactId=baksmali -Dversion=1.4.2 -Dpackaging=jar
