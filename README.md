# Collectible Store API

## Project Overview
This project is an online collectible store API built using Java 17+, Spark Framework, and MySQL. The API supports CRUD operations for both Users and Items and includes a web interface for managing item offers.

The main goal is to provide a backend service with the ability to:
- Manage users (add, edit, delete, and view).
- Manage collectible items.
- Submit and track offers for items through a form.
- Handle exceptions such as 404 (item not found) and 500 (server error).

## Features Added
1. **Exception Handling**
   - Returns a custom 404 page when an item or user is not found.
   - Returns a 500 error page for server-side errors.

2. **Templates**
   - **Item List Page**: Displays all items with their name and price.
   - **Item Detail Page**: Shows the description of an individual item when clicked.
   - Templates are implemented using Mustache, following the existing CSS styles in `styles.css`.

3. **Forms**
   - Added a form to submit offers for items, including:
     - Offer amount
     - User email
   - Offers are saved to `/recursos/ofertas.json` and can be retrieved via a GET API endpoint.

4. **API Endpoints**
   - **Users**
     - `GET /users` – List all users
     - `GET /users/:id` – Retrieve a specific user
     - `POST /users/:id` – Add a user
     - `PUT /users/:id` – Update a user
     - `DELETE /users/:id` – Delete a user
   - **Items**
     - `GET /items` – List all items
     - `GET /items/:id` – Retrieve item details
     - `POST /items/:id` – Add a new item
     - `PUT /items/:id` – Update an item
     - `DELETE /items/:id` – Delete an item
   - **Offers**
     - `POST /offers` – Submit a new offer
     - `GET /offers` – List all offers

## Technologies and Dependencies
- **Java 17+**
- **Spark Framework 2.9.4** – For building the web API.
- **Spark Template Mustache 2.7.1** – For rendering HTML templates.
- **Gson 2.10.1** – For JSON serialization.
- **MySQL Connector 8.0.33** – Database connection.
- **Logback Classic 1.4.11** – Logging framework.
- **Velocity Engine** – Optional template engine support.
- **SLF4J API 2.0.7** – Logging API.

## Project Structure
<img width="389" height="814" alt="image" src="https://github.com/user-attachments/assets/8c53d76f-bf32-4c21-9ea1-ec98828f7753" />
