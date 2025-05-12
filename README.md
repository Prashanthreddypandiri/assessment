# Assessment Application
### _This is a Spring Boot 3.4.5 application designed for managing book reservations. It includes RESTful APIs, database integration, and structured exception handling._


## 1. Prerequisites
Before running the application, ensure you have:
- **JDK 8 or higher** installed
- **Maven 3.8+**
- **MySQL 8.0+** (Ensure MySQL is running)
- **Git (optional)** if cloning the repository
## 2. Setting Up & Deploying
**Clone the Repository** - [GitHub](https://github.com/Prashanthreddypandiri/assessment.git) 
After cloning the repo route in cmd to 
```sh
cd assessment
```
**Build the Application**
Run the following commands to clean, compile, and package the application:
```sh
mvn clean package
```
A **JAR file** will be created inside the _target/_ directory
## 3. Configure Database (MySQL)
**Ensure MySQL is Installed and Running**
- Install MySQL if not already installed.
- Start MySQL and create the required database:
```sh
CREATE DATABASE assessment;
```
**Update** `application.properties`
Modify `src/main/resources/application.properties` to match your database configuration:
```sh
spring.datasource.url=jdbc:mysql://localhost:3306/assessment
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```
- Verify the Database Connection
- You can test the connection by running:
- mvn spring-boot:run

This will automatically create tables in `assessment` if configured correctly.
## 4. Running the Application
Start the application using: 
```sh
mvn spring-boot:run
```
Or directly execute the JAR file:
```sh 
java -jar target/assesment-0.0.1-SNAPSHOT.jar
```
**Access API Endpoints**
Once running, access APIs via:
- **Swagger UI** → http://localhost:8080/swagger-ui/index.html
- **Example API Call** → http://localhost:8080/api/v1/books/search?title=Java

## 5. Running Test Cases
Unit Tests Execute unit tests with:
```sh
mvn test
```
This runs all JUnit test cases located under `src/test/java`.
**Integration Tests**
Run Spring Boot integration tests using:
```sh
mvn verify
```
**Code Coverage**
To generate a JUnit test coverage report, use:
```sh
mvn jacoco:report
```
Then open the `target/site/jacoco/index.html` file in a browser.
**To Change the port in `application.properties`:**
```sh
server.port=9090
```


`author`: [Pandiri Prashanth Reddy](https://www.linkedin.com/in/prashanth-reddy-pandiri-72689578) 