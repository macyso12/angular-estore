# E-Store: Small Business Store

An online E-store system built in Java 8=>11 and Apache Maven with Spring Boot 3.3 => latest.
  
## Team

- Macy So
- Chen Guo
- Greg Lynskey

## Prerequisites

- Java 8=>11 (Make sure to have correct JAVA_HOME setup in your environment)
- Maven 3.3 => latest
- cURL

## How to run it

1. Clone the repository and go to the root directory.
2. `cd estore-api`
2. Execute `mvn compile exec:java`
3. Test estore-api CRUD operations by cURL

### cURL commands for CRUD operations

```PowerShell

# Create

curl -i -X POST -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"name\": \"Coke\", \"price\": 3.59, \"description\": \"a can of Coke\"}'
curl -i -X POST -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"name\": \"milk\", \"price\": 12.99}'

# Read

curl -i -X GET 'http://localhost:8080/products'
curl -i -X GET 'http://localhost:8080/products/12'
curl -i -X GET 'http://localhost:8080/products/99'
curl -i -X GET 'http://localhost:8080/products/?name=l'
curl -i -X GET 'http://localhost:8080/products/?name=*'

# Update

curl -i -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"id\": 11, \"name\": \"milk %2\", \"price\": 3.49, \"description\": \"gallon of milk\"}'
curl -i -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"id\": 11, \"name\": \"milk %2\", \"price\": 5.99, \"description\": \"gallon of milk\"}'
curl -i -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"id\": 11, \"name\": \"milk %2\", \"price\": 5.99, \"description\": \"gallon of milk 2%\"}'
curl -i -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"id\": 99, \"name\": \"milk %2\", \"price\": 5.99, \"description\": \"gallon of milk 2%\"}'

# Delete

curl -i -X DELETE 'http://localhost:8080/products/11'

```

## Known bugs and disclaimers

(It may be the case that your implementation is not perfect.)

Document any known bug or nuisance.
If any shortcomings, make clear what these are and where they are located.

- For cURL commands with CRUD operations, please use the original products.json file to ensure proper testing.

## How to test it

The Maven build script provides hooks for run unit tests and generate code coverage
reports in HTML.

To run tests on all tiers together do this:

1. Execute `mvn clean test jacoco:report`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/index.html`

To run tests on a single tier do this:

1. Execute `mvn clean test-compile surefire:test@tier jacoco:report@tier` where `tier` is one of `controller`, `model`, `persistence`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/{controller, model, persistence}/index.html`

To run tests on all the tiers in isolation do this:

1. Execute `mvn exec:exec@tests-and-coverage`
2. To view the Controller tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
3. To view the Model tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
4. To view the Persistence tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`

*(Consider using `mvn clean verify` to attest you have reached the target threshold for coverage)
  
## How to generate the Design documentation PDF

1. Access the `PROJECT_DOCS_HOME/` directory
2. Execute `mvn exec:exec@docs`
3. The generated PDF will be in `PROJECT_DOCS_HOME/` directory

## How to setup/run/test program

1. Tester, first obtain the Acceptance Test plan
2. IP address of target machine running the app
3. Execute ________
4. ...
5. ...

## License

MIT License

See LICENSE for details.
