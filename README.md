# Description: 
    This is a sample application (which is still a work in progress) which demonstrates basic capability using:
    - Java, Spring-Boot, JPA 
    - React.js, JavaScript
    - Maven, Intellij IDEA, Bootstrap
    This application can be run locally and by default uses an in-memory H2 database

# security
	- spring security JWT

# Persistence
	- ORM : hibernate(JPA)

# Database :
	- Server : H2
    - Type : memoire
	- console : http://localhost:8080/h2-console
	- user : sa
	- password :

# API docs :
	- http://localhost:8080/api-docs
    - http://localhost:8080/swagger-ui/index.html

# Lancement du projet (se positionner à la racine du projet) :
    - api : 
        - cd product-management-api
        - mvn clean install
        - MacOs/Linux  : ./mvnw spring-boot:run
        - Windows : mvnw.cmd spring-boot:run
    - web :
        - cd product-management-web
        - npm install
        - npm start
    - test : 
        - url : http://localhost:3000/
        - user : john.doe@gmail.com
        - password : 123