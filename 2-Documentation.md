[back](README.md)

## 2. Documentation

The **Visit-me** API uses an H2 database where the data is stored in memory.
When you run the application, the application creates all the needed tables for implemented features automatically.
If you want to see these tables, simply access the database at `http://localhost:8080/v1/h2-console` and use the following information:

+ **JDBC URL:** `jdbc:h2:mem:visitme_db`
+ **Username:** `admin`
+ **Password:** `admin`

You can use SQL statements to manipulate data directly.
However, this is not recommended.
It is always better to use the endpoints of the following sections to perform actions in the tables, avoiding conflicts and inconsistencies in the application.   

The **Visit-me** API is widely documented.
You can access the technical details about how to send a request through the below links or Swagger, at http://localhost:8080/v1/swagger-ui/index.html.
Whenever you wants to know about *bussiness rules* or application's behavior, you get more infomration in the  [Feature](3-Features.md).

### Documentation content

----
[2.1. Sign-up](documentation/1-Sign-Up.md)

[2.2. Sign-up](documentation/2-Sign-In.md)