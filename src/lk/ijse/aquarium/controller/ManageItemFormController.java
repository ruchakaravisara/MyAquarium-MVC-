package lk.ijse.aquarium.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import lk.ijse.aquarium.model.CustomerModel;
import lk.ijse.aquarium.model.ItemModel;
import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageItemFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtPrice;
    public JFXTextField txtQuantity;
    public TableView tblItem;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colPrice;
    public TableColumn colQuantity;

    public void btnAddItemOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        double price = Double.parseDouble((txtPrice.getText()));
        int quantity = Integer.parseInt((txtQuantity.getText()));

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{4,}$");
        Matcher matcher = pattern.matcher(txtName.getText());
        boolean isMatches = matcher.matches();
        if(isMatches) {

            Item item = new Item(id, name, price, quantity);
            try {
                boolean isAdded = ItemModel.saveItem(item);

                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item Added!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Error!").show();
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            loadAllItem();
        } else txtName.setFocusColor(Paint.valueOf("RED"));
    }

    public void btnUpdateItemOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        double price = Double.parseDouble((txtPrice.getText()));
        int quantity = Integer.parseInt((txtQuantity.getText()));

        Item item =new Item(id,name,price,quantity);
        boolean isUpdated = false;
        try {
            isUpdated = ItemModel.updateItem(item, id);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            } else
                new Alert(Alert.AlertType.ERROR, " Not Updated!").show();
        } catch (SQLException e) {
            System.out.println("SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Class");
        }
        loadAllItem();
    }

    public void btnDeleteItemOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        Item item =new Item(id);
        try {
            boolean isDeleted = ItemModel.DeleteItem(item,id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        loadAllItem();
    }

    public void btnSearchItemOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            Item item = ItemModel.searchItem(id);
            if(item != null) {
                txtId.setText(item.getId());
                txtName.setText(item.getName());
                txtPrice.setText(String.valueOf(item.getPrice()));
                txtQuantity.setText(String.valueOf(item.getQuantity()));
            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException  e) {
            System.out.println("Sql");
        }catch (ClassNotFoundException e){
            System.out.println("class");
        }
    }

    public void initialize()  {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        loadAllItem();
    }

    private void loadAllItem() {
        ObservableList<Item> CList = FXCollections.observableArrayList();

        try {
            ArrayList<Item> itemData = ItemModel.getItemData();

            for (Item st : itemData) {

                Item s = new Item(st.getId(), st.getName(), st.getPrice(), st.getQuantity());
                CList.add(s);

            }
        }catch (SQLException e){
            System.out.println("sql");
        }catch (ClassNotFoundException e){
            System.out.println("Class");
        }
        tblItem.setItems(CList);
    }
    private String generateNextStockItemID(String currentItemID) {
        System.out.println(currentItemID);
        if (currentItemID != null) {
            String[] ids = currentItemID.split("I00");
            int id = Integer.parseInt(ids[1]);
            id += 1;

            return "I00" + id;
        }
        return "I001";
    }

    public void btnNewOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String nextID = generateNextStockItemID(ItemModel.getCurrentItemID());
        txtId.setText(nextID);
    }
}
