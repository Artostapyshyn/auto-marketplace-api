# Auto-Marketplace API

## About project:
It's RESTful API for selling vehicles with, personal/advertisement information managment and login/registration process.

### Authorization​ controller
| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `POST`    | `/api/v1/sign-in`                             | Sign-in to upload and manage sale advertisements.                      |
| `POST`   | `/api/v1/sign-up`                 | Sign-up as seller.                 |

### Seller​ controller
| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`    | `/api/v1/sellers`                             | Retrieve all sellers.                      |
| `GET`    | `/api/v1/sellers/find-by-id{id}`                          | Find seller by id.                      |
| `GET`    | `/api/v1/sellers/find-by-email{email}`                          | Find seller by email.                       |
| `GET`    | `/api/v1/sellers/find-by-phoneNumber{phoneNumber}`                          | Find seller by phone number.                       |
| `POST`   | `/api/v1/sellers/edit{id}`                 | Edit seller information.                 |
| `DELETE` | `/api/v1/sellers{id}` | Delete seller by id.                    |

### Sale advertisement controller
| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`    | `/api/v1/sale-advertisements`                             | Retrieve all sale advertisements.                      |
| `GET`    | `/api/v1/sale-advertisements/images`                             | Retrieve all sale advertisements images.                      |
| `GET`    | `/api/v1/sale-advertisements/filter-by-id{id}`                          | Find advertisement by id.                      |
| `GET`    | `/api/v1/sale-advertisements/images{id}`                             | Find sale advertisement image by id.                      |
| `GET`    | `/api/v1/sale-advertisements/filter-by-type{type}`                          | Find sale advertisements by vehicle type.                       |
| `GET`    | `/api/v1/sale-advertisements/filter-by-brand{brand}`                          | Find sale advertisements by vehicle brand.                       |
| `GET`    | `/api/v1/sale-advertisements/filter-by-year{year}`                          | Find sale advertisements by vehicle production year.                   |
| `POST`   | `/api/v1/sale-advertisements/add`                 | Add sale advertisement.                 |
| `POST`   | `/api/v1/sale-advertisements/edit{id}`                 | Edit sale advertisement information.                 |
| `POST`   | `/api/v1/sale-advertisements/images/add{id}`                 | Add sale advertisement image.                 |
| `DELETE` | `/api/v1/sale-advertisements{id}` | Delete sale advertisement by id.                    |
| `DELETE` | `/api/v1/sale-advertisements/images{id}` | Delete sale advertisement by id.                    |
### Random controller
| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`    | `/api/v1/random-advertisements`                            | Retrive 15 random advertisements.                      |
Also you can get detailed documentation by accessing /swagger-ui/index.html#/

## How to start:
1. Clone the repository
  `https://github.com/Artostapyshyn/auto-marketplace.git`
2. Connect your database in [src/main/resources/application.properties](src/main/resources/application.properties) with valid credentials.
 
To use api you can register your own account or use already created one.

ADMIN:

Email : `admin@gmail.com`

Password : `admin2023`

SELLER:

Email : `user@gmail.com`

Password : `user2023`

## Stack
1. Java 17
2. Spring Boot, MVC, Data Jpa, Security, JDBC
3. Hibernate
4. PostgreSQL
5. Flyway
7. Swagger
8. Log4j2
9. JUnit, Mockito
10. Jackson
11. Lombok
12. Maven