package org.example.controller;

import com.google.gson.Gson;
import org.example.dao.ItemDAO;
import org.example.model.Item; // CAMBIO: Importación del modelo desde el nuevo paquete
import spark.Request;
import spark.Response;
import static spark.Spark.*;


import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class ItemController {
    private final ItemDAO itemDAO;
    private final Gson gson;


// Constructor que recibe el DAO y el Gson, y configura las rutas
    public ItemController(ItemDAO itemDAO, Gson gson) {
        this.itemDAO = itemDAO;
        this.gson = gson;
        setupRoutes();
    }
    private void setupRoutes() {
// Todas las rutas de Item se definen aquí
// GET /items y /items/
        get("/items", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("items", itemDAO.getAllItems());
            return new ModelAndView(model, "item_list.mustache");
        }, new MustacheTemplateEngine());
        get("/items/", this::getAllItems);


// GET /items/:id
        get("/items/:id", (req, res) -> {
            String id = req.params("id");
            Item item = itemDAO.getItemById(id);
            Map<String, Object> model = new HashMap<>();

            if (item == null) {
                res.status(404);
                return new ModelAndView(model, "not_found.mustache");
            }

            model.put("item", item);
            return new ModelAndView(model, "item_detail.mustache");
        }, new MustacheTemplateEngine());
// POST /items/:id
        post("/items/:id", this::addItem);
// PUT /items/:id
        put("/items/:id", this::updateItem);
// OPTIONS /items/:id
        options("/items/:id", this::checkItemExistence);
        delete("/items/:id", this::deleteItem);
    }

    private Object getAllItems(Request req, Response res) {
        res.type("application/json");
        return gson.toJson(itemDAO.getAllItems());
    }



    private Object getItemById(Request req, Response res) {
        res.type("application/json");
        String id = req.params("id");
        Item item = itemDAO.getItemById(id);
        if (item != null) {
            return gson.toJson(item);
        }
        res.status(404);
        return gson.toJson("Item not found");
    }



    private Object addItem(Request req, Response res) throws Exception {
        res.type("application/json");
        String id = req.params("id");
        Item item = gson.fromJson(req.body(), Item.class);
        item.setId(id);
        boolean created = itemDAO.addItem(item);
        res.status(created ? 201 : 400);
        return gson.toJson(created ? "Item created" : "Failed to create item");
    }


    private Object updateItem(Request req, Response res) throws Exception {
        res.type("application/json");
        String id = req.params("id");
        Item item = gson.fromJson(req.body(), Item.class);
        boolean updated = itemDAO.updateItem(id, item);
        res.status(updated ? 200 : 404);
        return gson.toJson(updated ? "Item updated" : "Item not found");
    }


    private Object checkItemExistence(Request req, Response res) throws Exception {
        res.type("application/json");
        String id = req.params("id");
        boolean exists = itemDAO.itemExists(id);
        return gson.toJson(exists);
    }

    private Object deleteItem(Request req, Response res) throws Exception {
        res.type("application/json");
        String id = req.params("id");
        boolean deleted = itemDAO.deleteItem(id);
        res.status(deleted ? 200 : 404);
        return gson.toJson(deleted ? "Item deleted" : "Item not found");
    }
}