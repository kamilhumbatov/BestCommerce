# BestCommerce

This is a sample Spring Boot/Maven Project with Gatling tests incorporated in build. For learning purpose.

# Run all with tests from Maven

This will start the jety server and then run the performance tests :

    docker-compose up
   
    
# Run from your IDE

    docker-compose down --volume

It is faster than from Maven because your IDE already compiled all sources.

## Start the project

Run the main from ```com.worldline.formation.gatling.service.Application```

## Launch the gatling tests

Run the main from ```com.worldline.formation.gatling.service.FibonacciSimulation```
