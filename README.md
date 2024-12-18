# pseudo-boolean-uvl-encoder
This project allows to encode uvl feature models in pseudo-boolean constraint logic.

# dependencies
needs the java-fm-metamodel project. If it is up to date in the maven repo no manual steps are needed. If not build and install it in your local maven repo:

https://github.com/Universal-Variability-Language/java-fm-metamodel
`mvn install:install-file -Dfile=fm-metamodel-1.1-jar-with-dependencies.jar -DgroupId=de.vill -DartifactId=fm-metamodel -Dversion=1.1 -Dpackaging=jar`

# building
build a jar that contains all dependencies:
`mvn clean compile assembly:single`