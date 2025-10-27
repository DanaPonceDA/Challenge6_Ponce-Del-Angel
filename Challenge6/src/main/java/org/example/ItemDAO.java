package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT id, name, price FROM items";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                items.add(new Item(
                        rs.getString("id"),
                        rs.getString("name"),
                        null,
                        rs.getString("price")
                ));
            }
        }
        return items;
    }

    public Item getItemById(String id) throws SQLException {
        String query = "SELECT * FROM items WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Item(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("price")
                );
            }
        }
        return null;
    }

    public boolean addItem(Item item) throws SQLException {
        String query = "INSERT INTO items (id, name, description, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, item.getId());
            pstmt.setString(2, item.getName());
            pstmt.setString(3, item.getDescription());
            pstmt.setString(4, item.getPrice());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateItem(String id, Item item) throws SQLException {
        String query = "UPDATE items SET name = ?, description = ?, price = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getDescription());
            pstmt.setString(3, item.getPrice());
            pstmt.setString(4, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteItem(String id) throws SQLException {
        String query = "DELETE FROM items WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean itemExists(String id) throws SQLException {
        return getItemById(id) != null;
    }
}
