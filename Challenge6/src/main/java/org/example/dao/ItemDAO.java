package org.example.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Item;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// El DAO ahora maneja una lista en memoria, no una conexión a BD
public class ItemDAO {
    // 1. Lista que mantendrá los datos del JSON
    private final List<Item> itemsData;
    private final Gson gson = new Gson();

    // Ruta del archivo JSON dentro de resources
    private static final String JSON_FILE_PATH = "/recursos/items.json";

    public ItemDAO() {
        this.itemsData = loadItemsFromJSON();
    }

    // Método para cargar el JSON en la lista
    private List<Item> loadItemsFromJSON() {
        try {
            // Carga el InputStream
            InputStreamReader reader = null;
            if (getClass().getResourceAsStream(JSON_FILE_PATH) != null) {
                reader = new InputStreamReader(getClass().getResourceAsStream(JSON_FILE_PATH));
            } else {
                System.err.println("No se encontró el archivo JSON en la ruta: " + JSON_FILE_PATH);
                return new ArrayList<>();
            }

            Type itemListType = new TypeToken<ArrayList<Item>>() {}.getType();
            List<Item> items = gson.fromJson(reader, itemListType);

            if (items == null) items = new ArrayList<>(); // si JSON estaba vacío
            System.out.println("Se cargaron " + items.size() + " items del JSON.");

            return items;
        } catch (Exception e) {
            System.err.println("Error al cargar el archivo JSON: " + JSON_FILE_PATH);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 1. Regresar la lista completa de artículos (GET /items)
    // Ya no lanza SQLException
    public List<Item> getAllItems() {
        // Retorna una copia de la lista para evitar modificaciones externas si fuera necesario
        return itemsData;
    }

    // 2. Regresar la descripción del artículo cuando se envía un ID dado (GET /items/:id)
    // Ya no lanza SQLException
    public Item getItemById(String id) {
        // Usa Stream API para encontrar el artículo por ID
        return itemsData.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // MÉTODOS CREADOS ANTERIORMENTE (Ahora son solo "simulados" y deben actualizar la lista local)

    // Simulando addItem: añade a la lista en memoria (no persistente)
    public boolean addItem(Item item) {
        if (getItemById(item.getId()) != null) return false; // ID ya existe
        itemsData.add(item);
        return true;
    }

    // Simulando updateItem: encuentra y reemplaza
    public boolean updateItem(String id, Item item) {
        Item existingItem = getItemById(id);
        if (existingItem == null) return false;

        // Actualiza los campos del objeto existente
        existingItem.setName(item.getName());
        existingItem.setDescription(item.getDescription());
        existingItem.setPrice(item.getPrice());

        // Nota: En una solución real, el 'id' no se cambiaría aquí
        // El DAO simulado asume que solo se actualizan los campos
        return true;
    }

    // Simulando deleteItem: elimina de la lista
    public boolean deleteItem(String id) {
        return itemsData.removeIf(item -> item.getId().equals(id));
    }

    // El método itemExists ahora usa el nuevo getItemById
    public boolean itemExists(String id) {
        return getItemById(id) != null;
    }
}