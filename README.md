# Stock - Drinks - ######


### Challenge

#### The API must be responsible for managing:
* Registration and consultation of drinks stored in each section with their
respective queries.
* Query the total volume in stock for each type of drink.
* Consultation of available storage locations of a given volume
Drink (calculate via algorithm).
* Consult the sections available for sale of a certain type of drink.
(calculate via algorithm).
* Record of entry and exit of drinks in case of sale and
receipt.
* Consultation of the history of entries and exits by type of drink and section.

#### The following rules must be observed in the registration and calculation flow:
* A section cannot have two or more different types of drinks (as already
said)
* There is no stock entry or exit without respective history entry.
* Registration must contain time, type, volume, section and input.
* A section cannot receive non-alcoholic beverages if it received alcohol in the
same day. Ex: Section 2 started the day with 50 liters of alcoholic beverages that
were consumed from stock, you can only receive non-alcoholics the next day.
* Inventory entry and exit history query endpoint, must return
results sorted by date and section, and you can change the sorting via
parameters.
* For error situations, request response must be consistent
to display a message consistent with the error.



### Stack:

* Java 8:
* Spring Boot
* Spring - Data
* JUNIT4
* MongoDB
* Maven

### Dev Environment

* Spring Tool Suite -- Eclipse Based
* Debian 8


### Data Base Technology
* I have experience with JPA / Hibernate / SpringData finally relational databases, but for this challenge I chose to use NOSQL - Mongo DB,
* for this challenge to add to my investment in BIG DATA and Java micro services, and demonstrate the simplicity of solving problems with simple cardinality

* The base is hosted on mlab in a free profile (Server Location Amazon Web Service)

### Query Strategy ##
AgregationFramework (Spring data) used to generate some queries in the base, I did not choose to use spring query to make it easy to maintain with criteria.

### Considerations Made

#### Registration and consultation of drinks stored in each section with their respective queries
> R: path parameter defines which section is queried:
eg  /estoque/secoes/1  (1 represents section number)


#### Query total volume in stock for each drink type
> R: Path parameter defines the Enum Type of the drink type.
/estoque/{DrinkType}


#### Query the available storage locations for a particular volume and type of beverage. (calculate via algorithm).
A: Path parameter defines the Enum Type of the beverage type by returning the available storage sections.
Path: /estoque/disponivel/ {DrunkType} /? Volume = 45

#### Query the sections available for sale for a particular type of drink
> Path: estoque/disponivel/venda/{DrinksType}



#### Registration of drinks entry and exit history in case of sale and receipt.

> Attribute containing the stock operation code must be sent indicating the operation performed in the case Enum In or Out Enum: OperacaoEstoqueType
/estoque/secoes/{numbering}
There are tests that guarantee the operation of this operation.
however, it is necessary to enter the ID of the Stock Item to make the exit.


#### Query history of entries and exits by drink type and section. The stock entry and exit history query endpoint must return the results sorted by date and section, and can change the sorting via parameters.
> A: EndPoint performs ordered queries (ascending and descending) that are handled via boolean parameter
Path: /historico?desc=true



#### Comments on code ####
> I appreciate clean code. But the comments in the code is just to facilitate the dynamics of the recruiter's code review

#### Unit Tests - Junit

* RestTemplate
* Using Restfull we validate the "Http status code" to identify the expected return semantics
* Validation in the business rule layer

#### Requirement-For error situations, the request response must be consistent in displaying a message that matches the error.
> R: - Exceptions are handled by an @ControllerAdvice of the spring class - ResourceExceptionHandler


### Testing Junits Implemented
* Validates stock transaction insertion with nonexistent section, returns http status code not found 404 (section not found)

* Validates insertion into existing section number but no required fields, time, type, volume, section and input, expected error with http status in range 422 (Unprocesable entity)

* Validates No business layer section query, expecting Exception NotFoundsecaoException

* Validates attempted insertion of two types of drinks into the same section, waiting for Exception TwoDrinkTypeException

* Validates attempted insertion of beverages that exceed the storage capacity of the alcoholic section (Note volume is calculated per ml)

* Validates attempted insertion of beverages that exceed the storage capacity of the non-alcoholic section (Note volume is calculated per ml)

* Validates if incoming operations performed via WebService are logged historical

* Validates drink insertion not allowed, with http status code not found 404

* Validates Beverage Type Query that does not exist in the business layer, expecting Exception NotFoundTypeDrinkException

* Validates if the amount entered is the volume returned regardless of the stored section, an insertion of a different drink type into this test has been validated.

* Validates available section lookup method for storing a certain volume of a particular beverage type

* Validation of available sections for sale from a certain type of drink.

* Validates attempt to exit non-existent item

* Validation of attempted insertion of alcoholic and non-alcoholic beverages on the same day and in the same section.