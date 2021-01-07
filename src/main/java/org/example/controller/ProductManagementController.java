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

import java.util.Arrays;
import java.util.List;

public class ProductManagementController {

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
        System.out.println("Saved: " + getProductFromView());
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

    public List<Product> findAll() {
        List<Product> result = Arrays.asList(
                new Product("CODE01", "Corote", 3.4, 15),
                new Product("CODE02", "Paiero", 0.25, 20),
                new Product("CODE03", "38", 300.1, 17),
                new Product("CODE04", "Opala", 10000.0, 1)
        );
        return result;
    }

    public void delete(ActionEvent actionEvent) {

    }
}
