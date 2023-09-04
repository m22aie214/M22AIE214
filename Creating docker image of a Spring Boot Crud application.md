# Creating docker image of a Spring Boot Crud application

In this technical document, we will walk through the process of deploying a Spring Boot 3 CRUD application to a Docker environment. The application under evaluation is a CRUD (Create, Read, Update, Delete) system that runs on the MySQL database management system (DBMS). As a result, inside our deployment architecture, we will regard the web application and the MySQL database as separate microservices. To do so, we will develop two Docker images, one for the Spring Boot application and one for the MySQL database, which will be hosted in separate Docker containers. To provide smooth communication between the two services, these containers will be integrated within the same Docker network.

************************GitHub repository for the spring boot application: Web-Application.************************

********************************************************************************Prerequisite: Docker should be installed in your system. (Install WSL if you are using Windows OS)********************************************************************************

As we are going to create two docker containers which will be communicating with each other.

### 1. Deploying MySQL Image in a Container

```docker

FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=springstudent

ENV MYSQL_DATABASE=employee_directory

COPY employee_directory.sql /docker-entrypoint-initdb.d/
```

In the employee_directory.sql :

```sql
CREATE DATABASE IF NOT EXISTS `employee_directory`;
USE `employee_directory`;

-- Table structure for table `employee`
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Data for table `employee`
INSERT INTO `employee` VALUES 
	(1,'Leslie','Andrews','leslie@luv2code.com'),
	(2,'Emma','Baumgarten','emma@luv2code.com'),
	(3,'Avani','Gupta','avani@luv2code.com'),
	(4,'Yuri','Petrov','yuri@luv2code.com'),
	(5,'Juan','Vega','juan@luv2code.com');
```

Build your custom docker image.

```docker
docker build -t mysql .
```

### 2. Creating a docker network to communicate Spring boot application and MySQL database.

```docker
docker network create springBootCrud-mysql-net
```

Here, *springBootCrud-mysql-net* is the network name.

You can check the list of networks using: 

```docker
docker network ls
```

### 3. Run the MySQL image in a docker container in the same network

```docker
docker run --name mysqldb --network springBootCrud-mysql-net -e MYSQL_ROOT_PASSWORD=springstudent -e MYSQL_DATABASE=employee_directory -d mysql
```

Command to access the MySQL database in the container:

```docker
docker exec -it mysqldb bash
```

### Deploy Spring Boot Application in a Docker Container

1. Create the jar file of the spring boot application using the following command.

```docker
mvn clean package
```

The jar will be created in target folder.

1. Create the Dockerfile to build the docker image.

```docker
FROM openjdk:17

COPY target/thymeleafdemo-0.0.1-SNAPSHOT.jar thymeleafdemo-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/thymeleafdemo-0.0.1-SNAPSHOT.jar"]

LABEL authors="PRATEEK_M22AIE214"
```

1. Build the docker image from the docker file.
    
    ```docker
    docker build -t springbootcrud .
    ```
    
    Check the list of docker images using the following command:
    
    ```docker
    docker images
    ```
    
2. Run the docker image in the docker container in the same network.
    
    ```docker
    docker run --network springboot-mysql-net --name springboot-container -p 8080:8080 -d springbootcrud
    ```
    
    ****************************************************URL to access the application: http://localhost:8080****************************************************
    
    You can check the logs using the following command:
    
    ```docker
    docker logs container-id
    ```
    

### Details of the web application:

It is a CRUD application where you can add the details of employee such as first_name, last_name and email. You can add new employees, delete the existing employees. Also, you can update the details of the existing employees.

 

![Untitled](Creating%20docker%20image%20of%20a%20Spring%20Boot%20Crud%20applic%204415ee809f7347aca25c45628a46e55e/Untitled.png)

Update Page:

![Untitled](Creating%20docker%20image%20of%20a%20Spring%20Boot%20Crud%20applic%204415ee809f7347aca25c45628a46e55e/Untitled%201.png)

Add New Employee:

![Untitled](Creating%20docker%20image%20of%20a%20Spring%20Boot%20Crud%20applic%204415ee809f7347aca25c45628a46e55e/Untitled%202.png)