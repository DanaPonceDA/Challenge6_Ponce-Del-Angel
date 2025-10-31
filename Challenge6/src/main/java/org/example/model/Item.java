package org.example.model;

public class Item {
    private String id;
    private String nombre;
    private String descripcion;
    private String price;

    public Item() {}

    public Item(String id, String nombre, String descripcion, String price) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.price = price;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return nombre; }
    public void setName(String nombre) { this.nombre = nombre; }

    public String getDescription() { return descripcion; }
    public void setDescription(String descripcion) { this.descripcion = descripcion; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
}
