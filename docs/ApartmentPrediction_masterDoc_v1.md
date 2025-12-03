# ApartmentPredictor masterDoc v1

## Summary

### References

- Reference project: [Spring Boot: H2 DB and Thymeleaf – albertprofe wiki](https://albertprofe.dev/springboot/boot-what-create-th-h2.html)
- Microservices: https://spring.io/]
- Spring Boot is open-source: [GitHub - spring-projects/spring-boot: Spring Boot helps you to create Spring-powered, production-grade applications and services with absolute minimum fuss.](https://github.com/spring-projects/spring-boot)
- Spring Boot Guides / Academy: https://spring.io/guides / https://spring.academy/courses
- Quickstart: https://spring.io/quickstart

### Product Goal

> Use this small housing-price dataset to train a regularized regression model that handles multicollinearity, then expose predictions through a Spring Boot REST API and a React form UI where users input area, bedrooms, bathrooms, furnishing, and road proximity for instant price estimates.

We are defining a clear full-stack goal: implement RESTful CRUD endpoints in Spring Boot for Apartment and Post entities, then build React views to list, create, edit, delete, and assign posts to apartments with a simple, responsive UI.

From source:

> A simple yet challenging project, to predict the housing price based on certain factors like house area, bedrooms, furnished, nearness to mainroad, etc.
> 
> The dataset is small yet, it's complexity arises due to the fact that it has strong <mark>multicollinearity</mark>.

### Version goal

> We are defining a CRUD service in Spring Boot for Apartment, Owners, Reviewers and Post entities, then test them to list, create, edit, delete, and assign posts to apartments with <mark>JUnit</mark>.

## Project commits

- [Commits · AlbertProfe/ApartmentPredictor · GitHub](https://github.com/AlbertProfe/ApartmentPredictor/commits/master/)

## Create project

### Using the CLI

> The Spring Boot CLI (Command-Line Interface) can be installed manually by using <mark>SDKMAN</mark>! (the SDK Manager) or by using Homebrew or MacPorts if you are an OSX user. See [Installing the Spring Boot CLI](https://docs.spring.io/spring-boot/installing.html#getting-started.installing.cli) in the “Getting Started” section for comprehensive installation instructions.

- [Installing Spring Boot :: Spring Boot](https://docs.spring.io/spring-boot/installing.html#getting-started.installing.cli)

- [Using the CLI :: Spring Boot](https://docs.spring.io/spring-boot/cli/using-the-cli.html)

By CLI `Spring init`

```textile
[Tue Dec 02 11:09:02] albert@albert-VirtualBox:~
$ spring init --dependencies=web,data-jpa,h2,devtools --build=maven --java-version=21 --name=ApartmentPredictor --artifactId=apartment-predictor --groupId=com.example ApartmentPredictor
```

```text
spring init 
    --dependencies=web,data-jpa,h2,devtools
    --build=maven 
    --java-version=21 
    --name=ApartmentPredictor 
    --artifactId=apartment-predictor 
    --groupId=com.example ApartmentPredictor
```

### Spring Init

- https://start.spring.io/

### Project structure

```textile
[Tue Dec 02 17:12:58] albert@albert-VirtualBox:~/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor (master)
$ tree 
.
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── apartment_predictor
│   │   │               ├── Apartment.java
│   │   │               ├── ApartmentPredictorApplication.java
│   │   │               ├── Owner.java
│   │   │               ├── Post.java
│   │   │               └── Reviewer.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── apartment_predictor
│                       └── ApartmentPredictorApplicationTests.java
└── target
    ...

34 directories, 19 files
```

## Data Model

Data source `csv`

- [Kaggle HousePrices datasheet](https://www.kaggle.com/datasets/yasserh/housing-prices-dataset/data)
- Raw cleanned data:
  - https://raw.githubusercontent.com/ywchiu/riii/master/data/house-prices.csv
  - https://raw.githubusercontent.com/YBI-Foundation/Dataset/main/House%20Prices.csv

### Java classes

```java
// java class
/**
 * The Apartment class models the details of a residential property. 
 * It stores pricing and structural information such as price, area, 
 * number of bedrooms, bathrooms, and floors (stories). It also tracks 
 * property features like main road access, guest room, basement, 
 * hot water, heating, air conditioning, and parking availability. 
 * Additionally, it includes preferences related to furnishing 
 * (furnished, semi-furnished, or unfurnished) and property status. 
 * This class provides getter and setter methods for encapsulated 
 * data access and supports comparison, filtering, and reporting 
 * operations in real estate applications.
 */

public class Apartment {

    private String id;
    private Long price;
    private Integer area;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer stories;
    private String mainroad;
    private String guestroom;
    private String basement;
    private String hotwater;
    private String heating;
    private String airconditioning;
    private Integer parking;
    private String prefarea;
    private String furnishingstatus;
}
```

```java
public class Owner {

    private  String id;
    private String name;
    private String email;
    private int age;
    private boolean isActive;
    private boolean isBusiness;
    private String idLegalOwner;
    private LocalDate registrationDate;
    private int qtyDaysAsOwner;
}
```

## UML

Here are very brief, concrete `restaurant-related` examples for each **UML** class diagram relationship in [Class diagrams | Mermaid](https://mermaid.js.org/syntax/classDiagram.html):

- **Inheritance (Generalization):**  
  Chef --|> Employee  
  Chef is a type of Employee.
- **Composition:**  
  Menu --* MenuItem  
  Menu is composed of MenuItems; MenuItems can’t exist without the Menu.
- **Aggregation:**  
  Restaurant --o Table  
  Restaurant has Tables, but Tables can exist if the Restaurant is closed.
- **Association:**  
  Waiter --> Order  
  Waiter takes Orders.
- **Link (Solid):**  
  Customer -- Bill  
  Customer is directly connected to Bill, representing a specific interaction.
- **Dependency:**  
  Receipt ..> Printer  
  Receipt depends on Printer (uses it temporarily).
- **Realization:**  
  CardPayment ..|> PaymentMethod  
  CardPayment realizes (implements) PaymentMethod interface.
- **Link (Dashed):**  
  Chef .. Kitchen  
  Chef is loosely connected to Kitchen (less specific/weak association).

```mermaid
classDiagram
Chef --|> Employee : Inheritance
Menu --* MenuItem : Composition
Restaurant --o Table : Aggregation
Waiter --> Order : Association
Customer -- Bill : Link(Solid)
Receipt ..> Printer : Dependency
CardPayment ..|> PaymentMethod : Realization
Chef .. Kitchen : Link(Dashed)
```

### UML V1.0 ApartmentPredictor

- [UML mermaid](https://github.com/AlbertProfe/ApartmentPredictor/blob/master/docs/appends/ApartmentPredictor-uml_v1.md)

![](https://raw.githubusercontent.com/AlbertProfe/ApartmentPredictor/refs/heads/master/docs/diagrams/ApartmentPredictor-uml_v1.png)

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>apartment-predictor</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>ApartmentPredictor</name>
    <description>Demo project for Spring Boot</description>
    <url/>
    <licenses>
        <license/>
    </licenses>
    <developers>
        <developer/>
    </developers>
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>
    <properties>
        <java.version>21</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.deeplearning4j</groupId>
            <artifactId>deeplearning4j-core</artifactId>
            <version>1.0.0-M2.1</version>
        </dependency>
        <dependency>
            <groupId>org.nd4j</groupId>
            <artifactId>nd4j-native-platform</artifactId>
            <version>1.0.0-M2.1</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## Tech Stack

- IDE: IntelliJ IDEA 2025.1.3 (Community Edition)
  
  - [Descargar IntelliJ IDEA](https://www.jetbrains.com/es-es/idea/download/?section=linux)
  
  - With [Installing snap on Ubuntu | Snapcraft documentation](https://snapcraft.io/docs/installing-snap-on-ubuntu): `sudo snap install intellij-idea-community --classic`

- Java 21 (or 25, 17, 11, 8) <mark>open-jdk</mark>

- JUniit 3.8.1

- Create project by <mark>Sprint Init</mark>
  
  - Alternative: Maven Project: **`maven-archetype-quickstart` archetype**
    
    - https://maven.apache.org/
    
    - mvn --version
      Apache Maven 3.8.7
      Maven home: /usr/share/maven
      Java version: 21.0.8, vendor: Ubuntu, runtime: /usr/lib/jvm/java-21-openjdk-amd64
      Default locale: en_US, platform encoding: UTF-8
      OS name: "linux", version: "6.8.0-83-generic", arch: "amd64", family: "unix"
