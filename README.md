# BestCommerce

This is a sample Spring Boot/Maven Project.Consist of 3 microservices 
* sign
* products
* notification

### Sign Service
 Api documentation  http://localhost:8093/swagger-ui.html
 Project runs port on port 8093
 
### Products Service
Api documentation http://localhost:8092/swagger-ui.html 
 Project runs port on port 8092

### Notification Service 
This is a Message Broker Apache Kafka sends email to merchants.
### Run project

This will start:

    mvn clean package
    docker-compose up -d
   
    
### Stop the project 

    docker-compose down --volume

