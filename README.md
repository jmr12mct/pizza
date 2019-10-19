# Welcome to Pizza Management Application

Pizza Management Application enables the user to Add, Update or Retrieve Pizza Information from Pizza Table in Data Store.

Pizza holds an Identifier, Name, Price and Description of the Pizza.


## API

Below is the list of API endpoints with their respective input and output:

### Base URL:

```
http://localhost:8080/
```


### Add Pizza

Endpoint:

```
POST
api/v1/pizzas
```

Input:

```json
{
	"name": "Tikka",
	"price": 25,
	"description": "Pizza loaded with Spicy Chicken Tikka"
}
```

### Get All Pizzas

Endpoint:

```
GET
api/v1/pizzas
```

Output:

```json
[
    {
        "id": "e69ce6ff-f92e-4075-8063-55e6b2d4b828",
        "name": "Tikka",
        "price": 25.0,
        "description": "Pizza loaded with Spicy Chicken Tikka"
    },
    {
        "id": "2041c240-09e0-4eb0-8735-be3d0add2f85",
        "name": "Barbeque",
        "price": 20.0,
        "description": "Pizza loaded with Barbeque Chicken"
    }
]
```

### Get Pizza By Id

Endpoint:

```
GET
api/v1/pizzas/{id}
```

Output:

```json
{
    "id": "e69ce6ff-f92e-4075-8063-55e6b2d4b828",
    "name": "Tikka",
    "price": 25.0,
    "description": "Pizza loaded with Spicy Chicken Tikka"
}
```

### Update Pizza

Endpoint:

```
PUT
api/v1/pizzas/{id}
```

Input:

```json
{
    "id": "e69ce6ff-f92e-4075-8063-55e6b2d4b828",
    "name": "Tikka",
    "price": 24.0,
    "description": "Pizza loaded with Spicy Chicken Tikka"
}
```

## Getting Started

### Prerequisites
* Java 1.8
* Maven

### Build
To generate an executable JAR, the below maven command has to be executed.
Unit tests will get executed as part of this command.

```
mvn clean package
```

### Test
To run tests, the below maven command has to be executed.

```
mvn clean test
```

### Run the Application
We can run the application in two ways:

*  **Spring Boot In-built Tomcat Server** :
	The below command will allow the application to run using the embedded Tomcat Server that comes as part of Spring Boot.

```
mvn clean spring-boot:run
```

*  **Execute the JAR** :
	Go to the local where the jar is present and execute the below command to run the application.  
	**You can get the jar file from Artifact folder in the project folder**

```
java -jar pizza-0.0.1-SNAPSHOT.jar
```

### View Tables
We can view the Table in embedded H2 In-memory Database by accessing below URL:

```
http://localhost:9080/console
```

Provide the below information:
* *JDBC URL* = jdbc:h2:mem:pizzadb
* *UserName* = admin
* *Password* = password

### Actuator
We can view the health and App info by accessing actuator URL and it will show the APIs that could be accessed as part of it:

```
http://localhost:9080/actuator
```

## CAUTION

Since we use in-memory Database, all data stored will get erased on Server restart.

## Author

Mano Ranjan Jayamaran
