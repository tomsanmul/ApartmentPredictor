# ApartmentPredictor masterDoc v3

## Summary

### All References

- Reference project: [Spring Boot: H2 DB and Thymeleaf – albertprofe wiki](https://albertprofe.dev/springboot/boot-what-create-th-h2.html)
- Microservices: https://spring.io/]
- Spring Boot is open-source: [GitHub - spring-projects/spring-boot: Spring Boot helps you to create Spring-powered, production-grade applications and services with absolute minimum fuss.](https://github.com/spring-projects/spring-boot)
- Spring Boot Guides / Academy: https://spring.io/guides / https://spring.academy/courses
- Quickstart: https://spring.io/quickstart

Historical notes:

- [Spring Framework – albertprofe wiki](https://albertprofe.dev/spring/spring-basics.html)
- [JPA Before Annotatons one-to-many xml](https://github.com/AlbertProfe/ApartmentPredictor/blob/master/docs/appends/JPA_Before_Annotatons:_one-to-many-xml.md)

#### Api Rest references

- What is api rest:
  
  - [Network: API Rest – albertprofe wiki](https://albertprofe.dev/devops/devops-network-rest.html)
  - [What is a REST API?](https://www.redhat.com/en/topics/api/what-is-a-rest-api) /  [What is a REST Api, GoogleCloud](https://cloud.google.com/discover/what-is-rest-api)

- API REST test tools:
  
  - [postman](https://www.postman.com/) / [Postman docs](https://learning.postman.com/docs/sending-requests/requests/)
  - [Swagger](https://swagger.io/) / [OpenApi](https://springdoc.org/)

- [restaurantManager controller](https://github.com/AlbertProfe/restaurantManager/blob/master/src/main/java/dev/example/restaurantManager/controller/BookingController.java)

- [Booking Controller](https://github.com/AlbertProfe/restaurantManager/blob/master/src/main/java/dev/example/restaurantManager/controller/BookingController.java) / [LibraryRestController](https://github.com/AlbertProfe/viladoms2022books/blob/master/libraryRest/src/main/java/com/example/myfirstprojectspring/LibraryRestController.java)

### Product Goal

> Use this small housing-price dataset to train a regularized regression model that handles multicollinearity, then expose predictions through a Spring Boot REST API and a React form UI where users input area, bedrooms, bathrooms, furnishing, and road proximity for instant price estimates.

We are defining a clear full-stack goal: implement RESTful CRUD endpoints in Spring Boot for Apartment and Post entities, then build React views to list, create, edit, delete, and assign posts to apartments with a simple, responsive UI.

From source:

> A simple yet challenging project, to predict the housing price based on certain factors like house area, bedrooms, furnished, nearness to mainroad, etc.
> 
> The dataset is small yet, it's complexity arises due to the fact that it has strong <mark>multicollinearity</mark>.

### Version goal

> We are defining a Rest Controller in Spring Boot for **Apartment, Review, Reviewer, Owner, PropiertyContract and School** entities, then test them to list, create, edit, delete, and assign reviews to apartments with <mark>Postman</mark> test tool.
> 
> We also are working in **Renovations** and **Person** as superclass for Owner and Reviewer.

## Project commits

- [Commits · AlbertProfe/ApartmentPredictor · GitHub](https://github.com/AlbertProfe/ApartmentPredictor/commits/master/)

### Project structure

```textile
[Tue Feb 10 11:04:35] albert@albert-VirtualBox:~/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor (master)
$ tree
.
├── ApartmentPredictorApplication.java
├── controller
│   ├── ApartmentRestController.java
│   └── SchoolRestController.java
├── model
│   ├── Apartment.java
│   ├── Duplex.java
│   ├── House.java
│   ├── Owner.java
│   ├── Reviewer.java
│   ├── Review.java
│   └── School.java
├── repository
│   ├── ApartmentRepository.java
│   ├── ReviewRepository.java
│   └── SchoolRepository.java
├── service
│   ├── ApartmentService.java
│   └── ReviewService.java
└── utils
    ├── PopulateDB.java
    └── PrintingUtils.java

6 directories, 17 files


```

## Data Model

### Java classes

```java
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
    // refactor both fiels to one
    private String hotwaterheating;
    //private String hotwater;
    //private String heating;
    private String airconditioning;
    private Integer parking;
    private String prefarea;
    private String furnishingstatus;
    // @OneToMany
    private List reviews;
    //@ManyToMany
    private List schools;
}

public class School {
    
    private String id;
    private String name;
    private String type;
    private String location;
    private int rating;
    private boolean isPublic;
}

public class PropiertyContract {
     private String id;
     private String date;
     private String registerNumberPropiertyContract;
     private long valueRealState;  
    
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

```java
public class Review {

    private String id;
    private String title;
    private String content;
    private int rating;
    private LocalDate reviewDate;
    // @ManyToOne
    private Apartment aparment;
}

public class Reviewer {
    private  String id;
    private String name;
    private String email;
    private int age;

}
```

### UML

todo

## Code

### MC

> This architecture defines a simple Spring Boot testing setup where a Test component exercises a `Service` layer that delegates to a `Repository`, which in turn <mark>performs CRUD operations against an in-memory/local H2 database. </mark>
> 
> **It is MC (Model-Controller) layered**, with Test invoking Service (buisness logic) → Repository (data access) → DB, enforcing s<mark>eparation of concerns. </mark>
> 
> The goal is to:
> 
> - validate application logic and data access without external infrastructure, enabling fast, isolated tests.
> 
> - validate **Rest Controle**r layer and <mark>data publising with Postman tool.</mark>

![](https://raw.githubusercontent.com/AlbertProfe/ApartmentPredictor/refs/heads/master/docs/diagrams/mc_uml-junittest_v1.png)

## Postman documentation API REST

- [apartmentPredictorCRUD](https://documenter.getpostman.com/view/7473960/2sBXVeFs8L)

## JPA

### Summary

- [Getting Started :: Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/jpa/getting-started.html) / [Core concepts :: Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/repositories/core-concepts.html)

- [Spring Boot: Data &amp; DB – albertprofe wiki](https://albertprofe.dev/springboot/boot-concepts-data.html)

Spring Data’s mission is to provide a familiar and consistent, **Spring-based programming model for data access**.

> It makes it easy to use **data access** technologies, `relational` and `non-relational` databases, `map-reduce frameworks`, and `cloud-based` data services.

This is an **umbrella project which contains many subprojects** that are specific to a given database. The projects are developed by working together with many of the companies and developers that are behind these exciting technologies.

### Relationships

- [JPA Relationships Guide](https://github.com/AlbertProfe/ApartmentPredictor/blob/master/docs/appends/JPA_Relationships_Guide.md)
- [JPA Relationships Implementation Rule](https://github.com/AlbertProfe/ApartmentPredictor/blob/master/docs/appends/JPA_RelationshipsImplementation_Rule.md)

### Many-to-Many Apartment and School

Many-to-many, **unidirectional**, Apartment is the **owning side**

- **Cardinality**
  - One [Apartment](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/model/Apartment.java:7:0-12:23) can be linked to many `School`s.
  - One `School` can be linked to many [Apartment](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/model/Apartment.java:7:0-12:23)s (in the database via the join table), even though the `School` entity does **not** have an `apartments` field (because it’s **unidirectional**).
- **Owning side**
  - [Apartment](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/model/Apartment.java:7:0-12:23) is the owning side because it declares `@ManyToMany` + `@JoinTable`.
  - `School` is not aware of the relationship in Java (no `mappedBy` on the other side).

<mark>Join table is what stores the relationship</mark>

Our mapping:

```java
@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
@JoinTable(
        name = "APARTMENT_SCHOOL_JOIN_TABLE",
        joinColumns = @JoinColumn(name = "apartment_id"),
        inverseJoinColumns = @JoinColumn(name = "school_id")
)
private List<School> schools = new ArrayList<>();
```

This creates/uses a join table:

- **Table:** `APARTMENT_SCHOOL_JOIN_TABLE`
- **Columns:**
  - `apartment_id` → FK to [Apartment](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/model/Apartment.java:7:0-12:23)
  - `school_id` → FK to `School`

Each row means: “this apartment is connected to this school”.

#### How it works in your REST controller

![](https://raw.githubusercontent.com/AlbertProfe/ApartmentPredictor/refs/heads/master/docs/diagrams/schoolsAssignToApartment-Flow/apartment-schools-controller.png)

File: [src/main/java/com/example/apartment_predictor/controller/ApartmentRestController.java](cci:7://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/controller/ApartmentRestController.java:0:0-0:0)

- Endpoint: `PUT /api/apartment/assignSchoolsToApartment`
- Flow:
  - Load the [Apartment](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/model/Apartment.java:7:0-12:23) by `apartmentId`
  - Load all `School`s by `schoolIds`
  - If some requested ids weren’t found, return `400 Bad Request`
  - Update the relationship by adding schools to the apartment (you currently use [apartment.addSchools(schoolsFound)](cci:1://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/model/Apartment.java:10:4-13:5))
  - Save the apartment → JPA writes rows into `APARTMENT_SCHOOL_JOIN_TABLE`
  - Return `200 OK` with response headers and the saved apartment

![](https://raw.githubusercontent.com/AlbertProfe/ApartmentPredictor/refs/heads/master/docs/diagrams/schoolsAssignToApartment-Flow/postman-apartment-schools.png)

```json
{
    "id": "fe9614a2-1a3f-403c-9822-4f5d027e13ed",
    "price": 130000,
    "area": 5,
    "bedrooms": 2,
    "bathrooms": 2,
    "stories": 3,
    "mainroad": "yes",
    "guestroom": "no",
    "basement": "no",
    "hotwaterheating": "yes",
    "airconditioning": "yes",
    "parking": 2,
    "prefarea": "yes",
    "furnishingstatus": "furnished",
    "reviews": [
        {
            "id": "7afe17a7-8fac-4847-a4b1-d934b2cc8a06",
            "title": "Nice Apartment in Fifth Avenue",
            "content": "This apartment exceeded my expectations. The location is perfect and the amenities are top-notch. Highly recommended for anyone looking for a comfortable stay.",
            "rating": 5,
            "reviewDate": "2025-12-11"
        }
    ],
    "schools": [
        {
            "id": "87ffb224-a053-4c3d-b593-cab8cf2f457e",
            "name": "Sunrise School",
            "type": "religious",
            "location": "East Side",
            "rating": 4,
            "public": false
        },
        {
            "id": "a2afa2f1-bab1-4fa6-816e-b77b8f3e31cd",
            "name": "Sunrise High School",
            "type": "religious",
            "location": "Downtown",
            "rating": 4,
            "public": false
        },
        {
            "id": "d217c2be-5079-43c8-9ffb-631ea8642bba",
            "name": "Hill Institute",
            "type": "private",
            "location": "East Side",
            "rating": 1,
            "public": false
        }
    ]
}
```

#### How schools get created (PopulateDB)

File: `src/main/java/com/example/apartment_predictor/utils/PopulateDB.java`

- `PopulateDB` creates `School` entities in the database first.
- Later, the controller assigns existing schools to an apartment by referencing their ids.
- Because the relationship is managed from the [Apartment](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/model/Apartment.java:7:0-12:23) side, you only need to save/update the apartment to persist the join table links.

Notes about `cascade = CascadeType.ALL`

- With `CascadeType.ALL`, saving an [Apartment](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/model/Apartment.java:7:0-12:23) can also persist `School` objects **if they are new/transient**.
- In your use case (schools already exist and you load them from [SchoolRepository](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/repository/SchoolRepository.java:5:0-6:1)), cascade isn’t strictly required for the join table update, but it won’t break things as long as you don’t accidentally attach brand-new `School` instances with duplicate ids.

#### H2 Database tables

![](https://raw.githubusercontent.com/AlbertProfe/ApartmentPredictor/refs/heads/master/docs/diagrams/schoolsAssignToApartment-Flow/school-db.png)



![](https://raw.githubusercontent.com/AlbertProfe/ApartmentPredictor/refs/heads/master/docs/diagrams/schoolsAssignToApartment-Flow/apartment-schools-db.png)



![](https://raw.githubusercontent.com/AlbertProfe/ApartmentPredictor/refs/heads/master/docs/diagrams/schoolsAssignToApartment-Flow/apartment-db.png)



## H2 & application.properties

> Welcome to **H2**, the Java SQL database. The main features of H2 are:
> 
> - Very fast, open source, `JDBC API`
> - Embedded and server modes; in-memory databases
> - Browser based Console application
> - Small footprint: around <mark>2.5 MB jar file size</mark>

References:

- Official web: https://h2database.com/html/installation.html
- Create H2 db from CLI: [Lab#SB08-3: H2 and API Rest – albertprofe wiki](https://albertprofe.dev/springboot/sblab8-3.html#h2-db)
- Step-by-step: [Spring Boot: H2 DB and Thymeleaf – albertprofe wiki](https://albertprofe.dev/springboot/boot-what-create-th-h2.html)
- DDL: [DDL auto generation](https://albertprofe.dev/springboot/sblab8-3.html#configuring-h2-database-in-spring-boot-with-ddl-auto-generation)
- Woking with different profiles: [Profiles and enviroment](https://albertprofe.dev/springboot/sblab8-3.html#working-on-different-environments)  
- [Lab#SB08-3: H2 and API Rest – albertprofe wiki](https://albertprofe.dev/springboot/sblab8-3.html)

Config `applcations properties` 

```properties
spring.application.name=ApartmentPredictor
# config db params connection
spring.datasource.url=jdbc:h2:/home/albert/MyProjects/Sandbox/ApartmentPredictorProject/db/apartmentpredictordb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=albert
spring.datasource.password=1234
# config db param
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=none
#spring.jpa.hibernate.ddl-auto=create
spring.jpa.hibernate.ddl-auto=update
```

This [application.properties](cci:7://file:///home/albert/MyProjects/Sandbox/rentingCarTest/rentingCar-boot/src/main/resources/application.properties:0:0-0:0) file configures a Spring Boot application for aparment price predictor management with H2 database integration.

Application Identity

- **`spring.application.name=ApartmentPredictor`** - Sets the application name used for identification in logs, monitoring tools, and service discovery. This appears in Spring Boot banners and helps distinguish this app from others.

Database Configuration

- **H2 Database Setup** - Uses H2 as an embedded/file-based database

- **Active URL**: `jdbc:h2:/home/albert/MyProjects/Sandbox/ApartmentPredictorProject/db/apartmentpredictordb` - Points to a persistent file-based H2 database stored locally

- **Commented alternatives**:
  
  - TCP server mode: `jdbc:h2:tcp://localhost/...` (for remote access)
  - In-memory mode: `jdbc:h2:mem:testdb` (data lost on restart)
    Authentication

- **Username**: `albert` (custom user, `sa` is H2's default admin)

- **Password**: `1234` (simple password for development)
  JPA/Hibernate Settings

Database Dialect and DDL

- **`spring.jpa.database-platform=org.hibernate.dialect.H2Dialect`** - Tells Hibernate to use H2-specific SQL syntax
- **`spring.jpa.show-sql=true`** - Enables SQL query logging for debugging
- **`spring.jpa.hibernate.ddl-auto=update`** - Automatically updates database schema without dropping existing data (safer than `create` which recreates tables)

### DDL - Data Definition Language

> **DDL - Data Definition Language**
> 
> <mark>DDL (Data Definition Language)</mark> consists of SQL commands that can be used for defining, altering and deleting database structures such as tables, indexes and schemas. It simply deals with descriptions of the database schema and is used to create and modify the structure of database objects in the database

![](https://raw.githubusercontent.com/AlbertProfe/ApartmentPredictor/refs/heads/master/docs/diagrams/sql-env.jpg)

### Populate DB

**Java Faker Code Method**

Add DataFaker dependency, inject `Faker` bean, and use `@PostConstruct` or `CommandLineRunner` to populate repositories. Generate realistic names, emails, dates, and UUIDs matching your `@Builder` entities: 

- `Conference.builder().title(faker.book().title()).build()`. Ideal for dynamic volumes.[[github](https://github.com/datafaker-net/datafaker)]​

**REST API Method**

Create `@RestController` endpoint `/api/fake-data` returning `List<Conference>`.

## Maven

> **Apache Maven** is a <mark>build tool for Java projects</mark>. Using a <mark>project object model </mark>(`POM`), Maven manages a project's compilation, testing, and documentation.

- [Maven](https://maven.apache.org/)

- [Maven Repository](https://mvnrepository.com/)

### pom.xml

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

- <mark>JUniit 3.8.1</mark>

- Create project by <mark>Sprint Init</mark>
  
  - Alternative: Maven Project: **`maven-archetype-quickstart` archetype**
    
    - https://maven.apache.org/
    
    - mvn --version
      Apache Maven 3.8.7
      Maven home: /usr/share/maven
      Java version: 21.0.8, vendor: Ubuntu, runtime: /usr/lib/jvm/java-21-openjdk-amd64
      Default locale: en_US, platform encoding: UTF-8
      OS name: "linux", version: "6.8.0-83-generic", arch: "amd64", family: "unix"

- <mark>Spring Data JPA 4.0.0</mark>
