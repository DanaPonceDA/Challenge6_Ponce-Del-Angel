
##  Project Functionality

The goal of this project is to build an **API service** for an **online collectible store** that supports CRUD operations for both **Users** and **Items**.

The API allows:
- Managing users (add, edit, delete, and view).
- Managing collectible items.
- Testing endpoints through tools like **Postman** or **cURL**.
- Proper Maven configuration with required dependencies.

---

##  Technologies Used

- **Java 17+**
- **Spark Framework**
- **Maven**
- **MySQL**
- **Gson** (for JSON serialization)
- **Logback** (for logging)

---

##  Project Structure

src/
└── main/
└── java/org/example/
├── Database.java
├── Item.java
├── ItemDAO.java
├── User.java
├── UserDAO.java
└── Main.java


---

## 🗃️ Database Configuration

**File:** `Database.java`

The connection parameters can be modified as needed:

```java
private static final String URL = "jdbc:mysql://localhost:3306/challenge6";
private static final String USER = "root";
private static final String PASSWORD = "Luzoscura0531";


Dependencies included in pom.xml:

<dependencies>
    <dependency>
        <groupId>com.sparkjava</groupId>
        <artifactId>spark-core</artifactId>
        <version>2.9.4</version>
    </dependency>

    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>

    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.4.11</version>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>



URL: http://localhost:4567/

HTTP Method	Endpoint	Description
GET	        http://localhost:4567/users	Retrieve a list of all users.
GET	        http://localhost:4567/users/:id	Retrieve user by ID.
POST	      http://localhost:4567/users/:id	Add a new user (with ID).
PUT	        http://localhost:4567/users/:id	Update an existing user.
OPTIONS	    http://localhost:4567/users/:id	Check if user with given ID exists.
DELETE	    http://localhost:4567/users/:id	Delete a user by ID.

Base URL: http://localhost:4567/items

HTTP Method	Endpoint	Description
GET	    http://localhost:4567/items	Retrieve all items (ID, name, price).
GET	    http://localhost:4567/items/:id	Retrieve details of an item by ID.
POST	  http://localhost:4567/items/:id	Add a new item.
PUT	    http://localhost:4567/items/:id	Update an existing item.
OPTIONS	http://localhost:4567/items/:id	Check if item with given ID exists.
DELETE	http://localhost:4567/items/:id	Delete an item by ID.
