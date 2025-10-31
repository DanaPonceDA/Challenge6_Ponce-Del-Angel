package org.example.controller;

import com.google.gson.Gson;
import org.example.dao.UserDAO;
import org.example.model.User; // CAMBIO: Importación del modelo desde el nuevo paquete
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class UserController {
    private final UserDAO userDAO;
    private final Gson gson;

    // Constructor que recibe el DAO y el Gson, y configura las rutas
    public UserController(UserDAO userDAO, Gson gson) {
        this.userDAO = userDAO;
        this.gson = gson;
        setupRoutes();
    }

    private void setupRoutes() {
        // Todas las rutas de User se definen aquí

        // GET all users
        get("/users", this::getAllUsers);

        // GET user by ID
        get("/users/:id", this::getUserById);

        // POST add user
        post("/users/:id", this::addUser);

        // PUT update user
        put("/users/:id", this::updateUser);

        // OPTIONS check user existence
        options("/users/:id", this::checkUserExistence);

        // DELETE user
        delete("/users/:id", this::deleteUser);
    }

    // Métodos manejadores de rutas

    private Object getAllUsers(Request req, Response res) throws Exception {
        res.type("application/json");
        return gson.toJson(userDAO.getAllUsers());
    }

    private Object getUserById(Request req, Response res) throws Exception {
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
        } catch (NumberFormatException e) {
            res.status(400);
            return gson.toJson("Invalid ID format");
        } catch (Exception e) {
            res.status(500);
            // Logger para depuración
            // e.printStackTrace();
            return gson.toJson("Internal server error");
        }
    }

    // El resto de los métodos (addUser, updateUser, checkUserExistence, deleteUser) se implementarán de forma similar
    // a como estaban en Main.java, usando los campos userDAO y gson.

    private Object addUser(Request req, Response res) throws Exception {
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
    }

    private Object updateUser(Request req, Response res) throws Exception {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params("id"));
            User user = gson.fromJson(req.body(), User.class);
            boolean updated = userDAO.updateUser(id, user);
            res.status(updated ? 200 : 404);
            return gson.toJson(updated ? "User updated" : "User not found");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("Internal server error");
        }
    }

    private Object checkUserExistence(Request req, Response res) throws Exception {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params("id"));
            boolean exists = userDAO.userExists(id);
            return gson.toJson(exists);
        } catch (Exception e) {
            res.status(500);
            return gson.toJson(false); // Podría ser un 500/error, pero devolvemos false por simplicidad
        }
    }

    private Object deleteUser(Request req, Response res) throws Exception {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params("id"));
            boolean deleted = userDAO.deleteUser(id);
            res.status(deleted ? 200 : 404);
            return gson.toJson(deleted ? "User deleted" : "User not found");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("Internal server error");
        }
    }
}