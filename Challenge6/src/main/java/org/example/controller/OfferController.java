package org.example.controller;

import org.example.dao.ItemDAO;
import org.example.dao.OfferDAO;
import org.example.model.Offer;
import org.example.model.Item;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import java.util.List;
import java.util.ArrayList;

import static spark.Spark.*;

public class OfferController {

    private final ItemDAO itemDAO;
    private final OfferDAO offerDAO;
    private final Gson gson;

    public OfferController(ItemDAO itemDAO, OfferDAO offerDAO, Gson gson) {
        this.itemDAO = itemDAO;
        this.offerDAO = offerDAO;
        this.gson = gson;

        setupRoutes();
    }

    private void setupRoutes() {
        // Lista de ofertas
        get("/offers", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Offer> offers = offerDAO.getAllOffers();

            // Creamos un mapa para asociar cada oferta con el nombre del item
            List<Map<String, Object>> offersWithNames = new ArrayList<>();
            for (Offer offer : offers) {
                Map<String, Object> o = new HashMap<>();
                o.put("id", offer.getId());
                o.put("itemId", offer.getItemId());
                o.put("offerPrice", offer.getOfferPrice());
                o.put("offerUser", offer.getOfferUser());

                // Obtenemos el nombre del item
                Item item = itemDAO.getItemById(offer.getItemId());
                o.put("itemName", item != null ? item.getName() : "Desconocido");

                offersWithNames.add(o);
            }

            model.put("offers", offersWithNames);
            return new ModelAndView(model, "offer_list.mustache");
        }, new MustacheTemplateEngine());

        // Formulario para crear oferta
        get("/offers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("items", itemDAO.getAllItems());
            return new ModelAndView(model, "offer_form.mustache");
        }, new MustacheTemplateEngine());

        // Crear oferta
        post("/offers", (req, res) -> {
            String itemId = req.queryParams("itemId");
            double offerPrice = Double.parseDouble(req.queryParams("offerPrice"));
            String offerUser = req.queryParams("offerUser");

            Offer offer = new Offer(0, itemId, offerPrice, offerUser);
            offerDAO.addOffer(offer);

            res.redirect("/offers");
            return null;
        });
    }
}
