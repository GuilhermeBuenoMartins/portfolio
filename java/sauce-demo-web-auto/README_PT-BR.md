# Projeto sauce-demo-web-auto

Automação de testes do site [sauce demo](https://www.saucedemo.com/) focado nas funcionalidades:

 - **_login_**;
 - **acessar produto**;
 - **adicionar produto ao carrinho**; e 
 - **remover produtos do carrinho**.
 
 Os arquivos _Gherkin_ correspondentes estão localizados em `./src/test/resources/` e utiliza as tecnologias:

 - Java 11
 - Maven 3.9.5
 - Selenium WebDriver
 - Cucumber
 - JUnit
 - Docker / Selenium Grid

## Pré-requisitos

Os requisitos mínimos para a execução dos testes automatizados estão listados abaixo. É possível baixa-los a partir de seus respectivos _links_:

 + [Git](https://git-scm.com/downloads) versão 2.39.5;
 + [Java](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html) versão 11;
 + [Maven](https://maven.apache.org/download.cgi) versão 3.9.5; e
 + [Docker CLI/Engine](https://www.docker.com/get-started/) versão 28.1.1.

## Execução

Atendidos os pré-requisitos, a execução deverá ser feita a partir do diretório onde este README.md se encontra. Para iniciar o ambiente de execução use os comandos do _docker_ abaixo:

`docker compose -f docker-compose.yml up selenium-hub chrome`

Após a inicialização do ambiente, execute os testes com o seguinte Maven:

`mvn clean test`

Para execução _multi-thread_ dos cenários de testes, utilize o comando Maven abaixo:

`mvn -T 4 clean test`

Onde 4 é o número de _threads_ usada na execução.

Caso deseje executar apenas uma funcionalidade (_feature_) ou cenário de teste, utilize as _tags_ como no exemplo abaixo:

`mvn clean test -Dcucumber.filter.tags="@login"`

O resultado de todas as execuções pode ser acessado no caminho `./target/cucumber/report.html`.

## Desenvolvedores

Os interessados em ampliar a quantidade de testes ou estudar este projeto, por favor, efetuar o _fork_ do projeto e utilizar o arquivo `docker-compose.yml` com o container `debian-env` para desenvolvimento em ambiente padronizado.


