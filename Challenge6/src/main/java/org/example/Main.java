package org.example;

import static spark.Spark.*;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class Main {

    public static void main(String[] args) {
        port(4567);
        UserDAO userDAO = new UserDAO();
        ItemDAO itemDAO = new ItemDAO();
        Gson gson = new Gson();

        // GET all users
        get("/users", (Request req, Response res) -> {
            res.type("application/json");
            return gson.toJson(userDAO.getAllUsers());
        });

        // GET user by ID
        get("/users/:id", (req, res) -> {
            res.type("application/json");
            try {
                int id = Integer.parseInt(req.params("id"));
                User user = userDAO.getUserById(id);
                if (user != null) {
                    return gson.toJson(user);
                } else {
                    res.status(404);
                    return gson.toJson("User not found");
                }
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("Internal server error");
            }
        });

        // POST add user
        post("/users/:id", (Request req, Response res) -> {
            res.type("application/json");
            try {
                int id = Integer.parseInt(req.params("id"));      // Captura el ID de la URL
                User user = gson.fromJson(req.body(), User.class);
                user.setId(id);                                   // Asigna el ID al objeto
                boolean created = userDAO.addUser(user);          // Inserta el usuario
                res.status(created ? 201 : 400);
                return gson.toJson(created ? "User created" : "Failed to create user");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("Internal server error");
            }
        });

        // PUT update user
        put("/users/:id", (Request req, Response res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id")); // <- Sin ":"
            User user = gson.fromJson(req.body(), User.class);
            boolean updated = userDAO.updateUser(id, user);
            res.status(updated ? 200 : 404);
            return gson.toJson(updated ? "User updated" : "User not found");
        });

        // OPTIONS check user existence
        options("/users/:id", (Request req, Response res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id")); // <- Sin ":"
            boolean exists = userDAO.userExists(id);
            return gson.toJson(exists);
        });

        // DELETE user
        delete("/users/:id", (Request req, Response res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id")); // <- Sin ":"
            boolean deleted = userDAO.deleteUser(id);
            res.status(deleted ? 200 : 404);
            return gson.toJson(deleted ? "User deleted" : "User not found");
        });

        // ==================== RUTAS DE ITEMS ====================

            // GET /items y /items/
            get("/items", (req, res) -> {
                res.type("application/json");
                return gson.toJson(itemDAO.getAllItems());
            });
            get("/items/", (req, res) -> {
                res.type("application/json");
                return gson.toJson(itemDAO.getAllItems());
            });

            // GET /items/:id
            get("/items/:id", (req, res) -> {
                res.type("application/json");
                String id = req.params("id");
                Item item = itemDAO.getItemById(id);
                if (item != null) {
                    return gson.toJson(item);
                } else {
                    res.status(404);
                    return gson.toJson("Item not found");
                }
            });

            // POST /items/:id
            post("/items/:id", (req, res) -> {
                res.type("application/json");
                String id = req.params("id");
                Item item = gson.fromJson(req.body(), Item.class);
                item.setId(id);
                boolean created = itemDAO.addItem(item);
                res.status(created ? 201 : 400);
                return gson.toJson(created ? "Item created" : "Failed to create item");
            });

            // PUT /items/:id
            put("/items/:id", (req, res) -> {
                res.type("application/json");
                String id = req.params("id");
                Item item = gson.fromJson(req.body(), Item.class);
                boolean updated = itemDAO.updateItem(id, item);
                res.status(updated ? 200 : 404);
                return gson.toJson(updated ? "Item updated" : "Item not found");
            });

            // OPTIONS /items/:id
            options("/items/:id", (req, res) -> {
                res.type("application/json");
                String id = req.params("id");
                boolean exists = itemDAO.itemExists(id);
                return gson.toJson(exists);
            });

            // DELETE /items/:id
            delete("/items/:id", (req, res) -> {
                res.type("application/json");
                String id = req.params("id");
                boolean deleted = itemDAO.deleteItem(id);
                res.status(deleted ? 200 : 404);
                return gson.toJson(deleted ? "Item deleted" : "Item not found");
            });
    }
}
