# Notification Service

A notification system that sends out notifications of various types with a rate limit implementation to protect 
recipients due to unintentional errors or abuse.

# Technical decisions

A simple design was implemented for the rate limit. Encapsulated inside the 'ratelimit' package, the notification 
system only needs to inject the RateLimitManager. Therefore, it doesn't need to know how it works internally. 

The RateLimit interface allows for the creation of multiple notification types that uses any desired algorithm 
implementation for rate limiting. In this example, the token-bucket algorithm was used via the bucket4j Java library.

# Frameworks & Libraries

- bucket4j-core - implements the token-bucket algorithm for rate limiting
- mockito-junit-jupiter - test implementation and mocking

# Compiling and running the project

**A JDK version 21 or higher installed is required to compile and run the project.

In a terminal on the project directory:

1. Compiling
```
./mvnw clean install
```

2. Running
```
java -jar target/notification-service-1.0-SNAPSHOT-jar-with-dependencies.jar
```

# Running the tests

In a terminal on the project directory:
```
./mvnw test
```