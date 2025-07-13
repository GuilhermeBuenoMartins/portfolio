# sauce-demo-web-auto Project

Automated testing of the [sauce demo](https://www.saucedemo.com/) website, focusing on the following features:

 - **Login**
 - **Access product**
 - **Add product to the cart**
 - **Remove products from cart**

The corresponding _Gherkin_ files are located in `./src/test/resources` and the project relies on the following technologies:

 - Java 11
 - Maven 3.9.5
 - Selenium WebDriver
 - Cucumber
 - JUnit
 - Docker / Selenium Grid

 ## Prerequisites

 The minimum requirements to run automated testing are listed below. You can download them from their respective links.

 + [Git](https://git-scm.com/downloads), versão 2.39.5
 + [Java](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html), versão 11
 + [Maven](https://maven.apache.org/download.cgi), versão 3.9.5
 + [Docker CLI/Engine](https://www.docker.com/get-started/), versão 28.1.1

 # Execution

 Once the prerequirements are met, the commands must be run from the same directory as this README.md file. To start the execution environment, use the following Docker command:

`docker compose -f docker-compose.yml up selenium-hub chrome`

After the environment has been started, run the tests with the following Maven command:

`mvn clean test`

To perform a multi-thread execution of test scenarios, use the Maven command bellow:

`mvn -T 4 clean test`

Where `4` is a number of threads used during execution.

If you want to run only a specific feature or test scenario, use tags as shown in the example below:

`mvn clean test -Dcucumber.filter.tags="@login"`

The result of all test runs can be accessed at `./target/cucumber/report.html`.

## Developers

If you're interested in expanding test coverage or studying this project, please fork it and use the `doker-compose.yml` file with the `debian-env` container to develop in stardardized environment.