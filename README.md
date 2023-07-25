# PropertyEye
This is simple Spring Boot backend project where I have created a market place for sellers to sell their property and buyers to express their interest in them.
I have developed the frontend in ReactJs

**The project is well devided in multiple layers**
**Controller Layer**
This layer interacts with the user interface or the frontend of the prject. All endpoints of the Rest API are declared in this layer.
- Global Exception Handlin is being carried out for the controller layer using @ControllerAdvice Annotation.
- Exceptions are thrown through the service layer itself and caught globally at the controller layer.
- This layer has endpoints for Buysers, Sellers, and Property with sepcific functionalities as they are neede to be given.

**Service Layer**
This layer holds the business logic of the project, i.e the logic for handling data
- it uses repository layers for making sql queries to the database and run other logics on them
- all the functionalitied mapped in the controller layer are of service layer.

**Repository (DAO) Layer**
This layers maked user defined sql queries to the database and return the result back to the java Spring Boot application.
- This layer is responsible for interaction with the database.

**Entity**
This creates the Object Relation Model for the tabled in MySQL Databse.
- The schema of the table to be created in the database is defined in the entity.
- Ther are entity for Property, Seller, Buyer.
- It also gives the relations to be present between variious tables. like @OneToMany for one to many relationship.
