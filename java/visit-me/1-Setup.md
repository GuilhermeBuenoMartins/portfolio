[back](README.md)

## 1. Setup

This section contents what tools you need to develop or execute this project in your local machine.

### 1.1. Prerequisites

**Visit-me** was made using the tools below.
For you execute successfully, use the same or newest versions.
You can use the following links to download them.
If one or more links does not work, you can find the tools search them.

 - [Git version 2.39.5](https://git-scm.com/install/linux)
 - [Java version 17.0.17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
 - [Apache Maven version 3.9.5](https://maven.apache.org/download.cgi)

Once installed all the tools above, you will be able to execute the project.

### 1.2 How to execute

Once inside of `visit-me-api` folder, you can launch the project using one of the following two command in a terminal:

```bash
mvn clean spring-boot:run
```

The application will start on `http://localhost:8080`.


If you wish to execute just the unit testing, use the following command:

```bash
mvn clean test
```