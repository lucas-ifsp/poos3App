package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManagementController {

    public static final String SQLITE_DB_URL = "jdbc:sqlite:product_management.db";

    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, String> clName;
    @FXML
    private TableColumn<Product, String> clCode;
    @FXML
    private TableColumn<Product, String> clPrice;
    @FXML
    private TableColumn<Product, String> clQuantity;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtProduct;
    @FXML
    private TextField txtQuantity;

    private ObservableList<Product> products;

    public void save(Product p) {
        final String sql = "INSERT INTO product (code, name, price, quantity) values (?,?,?,?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:product_management.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // preenche os valores para (?,?, ..., ?)
            stmt.setString(1, p.getCode());
            stmt.setString(2, p.getName());
            stmt.setDouble(3, p.getPrice());
            stmt.setInt(4, p.getQuantity());

            // executa o comando SQL
            stmt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Product> findAll() {
        List<Product> result = new ArrayList<>();
        String sql = "SELECT * FROM product";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:product_management.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery(); // executa o comando SQL e armazena no ResultSet

            while (rs.next()) {
                Product product = new Product(rs.getString("code"), rs.getString("name"),
                        rs.getDouble("price"), rs.getInt("quantity"));
                result.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return result;
    }

    public  Product find(String pKey) {
        Product p = null;
        String sql = "SELECT * FROM product WHERE code = ?";

        try (Connection conn = DriverManager.getConnection(SQLITE_DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pKey);

            ResultSet rs = stmt.executeQuery(); // executa o comando SQL e armazena no ResultSet

            if (rs.next()) {
                p = new Product(rs.getString("code"), rs.getString("name"),
                        rs.getDouble("price"), rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return p;
    }

    public void delete(String pKey){
        String sql = "DELETE FROM product WHERE code = ?" ;
        try (Connection conn = DriverManager.getConnection(SQLITE_DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pKey);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public static void delete(String pKey){
        String sql = "DELETE FROM product WHERE code = '" + pKey + "'"; //dados inseridos na string
        try (Connection conn = DriverManager.getConnection(SQLITE_DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql); // não é necessário colocar valores para cada ?
        } catch (SQLException e) {e.printStackTrace();}
    }*/

    @FXML
    private void initialize() {
        bindTableToList();
        bindEntityToColumn();
        loadTableFromDatabase();
    }

    private void bindTableToList() {
        products = FXCollections.observableArrayList();
        tableView.setItems(products);
    }

    private void bindEntityToColumn() {
        clCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        clQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    private void loadTableFromDatabase() {
        products.clear();
        products.addAll(findAll());
    }

    public void saveOrUpdate(ActionEvent actionEvent) {
        save(getProductFromView());
        clearInputFields();
        loadTableFromDatabase();
    }

    private Product getProductFromView() {
        String code = txtCode.getText();
        String name = txtProduct.getText();
        Double price = Double.valueOf(txtPrice.getText());
        Integer quantity = Integer.valueOf(txtQuantity.getText());
        Product p = new Product(code, name, price, quantity);
        return p;
    }

    private void clearInputFields() {
        txtProduct.setText("");
        txtPrice.setText("");
        txtCode.setText("");
        txtQuantity.setText("");
    }

    public void delete(ActionEvent actionEvent) {
        Product selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String code = selectedItem.getCode();
            delete(code);
        }
        loadTableFromDatabase();
    }
}