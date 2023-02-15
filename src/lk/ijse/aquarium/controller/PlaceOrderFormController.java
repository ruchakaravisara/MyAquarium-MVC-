package lk.ijse.aquarium.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import lk.ijse.aquarium.db.DBConnection;
import lk.ijse.aquarium.model.ItemModel;
import lk.ijse.aquarium.model.OrderDetailModel;
import lk.ijse.aquarium.to.Item;
import lk.ijse.aquarium.to.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceOrderFormController {

    public TableColumn colOid;
    public TableColumn colTime;
    public TableColumn colCid;
    public TableColumn colDid;
    public TableColumn colIid;
    public TableColumn colName;
    public TableColumn colPrice;
    public TableColumn colQuantity;
    public JFXTextField txtOid;
    public JFXTextField txtDid;
    public JFXTextField txtCid;
    public JFXTextField txtIid;
    public JFXTextField txtName;
    public JFXTextField txtPrice;
    public JFXTextField txtQuantity;
    public JFXTextField txtTime;
    public TableView<OrderDetails> tblOrderDetail;
    public JFXTextField txtSearch;
    public JFXComboBox combItemId;



    public void initialize() {

        colOid.setCellValueFactory(new PropertyValueFactory<>("Oid"));
        colCid.setCellValueFactory(new PropertyValueFactory<>("Cid"));
        colDid.setCellValueFactory(new PropertyValueFactory<>("Did"));
        colIid.setCellValueFactory(new PropertyValueFactory<>("Iid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        setRegIdS();

        tblOrderDetail.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setData(newValue);
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadStockData(newValue);

        });

        loadStockData("");
    }

    private void setData(OrderDetails newValue) {
        txtOid.setText(newValue.getOid());
        txtCid.setText(newValue.getCid());
        txtDid.setText(newValue.getDid());
       txtIid.setText(newValue.getIid());
        txtName.setText(newValue.getName());
        txtPrice.setText(String.valueOf(newValue.getPrice()));
        txtQuantity.setText(String.valueOf(newValue.getQuantity()));

    }

    private void loadStockData(String SearchID) {
        ObservableList<OrderDetails> list = FXCollections.observableArrayList();
        try {
            ArrayList<OrderDetails> otherItemData = OrderDetailModel.getItemsData();
            for (OrderDetails s : otherItemData) {
                if (s.getName().contains(SearchID) || s.getIid().contains(SearchID)) {
                    OrderDetails oti = new OrderDetails(s.getOid(), s.getCid(), s.getDid(), s.getIid(), s.getName(), s.getPrice(), s.getQuantity());
                    list.add(oti);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        tblOrderDetail.setItems(list);
    }


    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String oid = txtOid.getText();
        String cid = txtCid.getText();
        String did = txtDid.getText();
        String iid = txtIid.getText();
        String name = txtName.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());


            try {
                OrderDetails orderDetails = new OrderDetails(oid, cid, did, iid, name, price, quantity);
                boolean isInserted = OrderDetailModel.insertNewItemTransaction(orderDetails);
                if (isInserted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Added !").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Wrong Supplier !").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Not Added !\n" + e).show();
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Not Added ! \nwrong input(s)").show();
            }
            loadStockData("");

    }

    private String generateNextStockItemID(String currentItemID) {
        System.out.println(currentItemID);
        if (currentItemID != null) {
            String[] ids = currentItemID.split("O00");
            int id = Integer.parseInt(ids[1]);
            id += 1;

            return "O00" + id;
        }
        return "O001";
    }


    public void NewOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String nextID = generateNextStockItemID(OrderDetailModel.getCurrentItemID());
        txtOid.setText(nextID);
    }

    public void UpdateOnAction(ActionEvent actionEvent) {
        String oid = txtOid.getText();
        String cid = txtCid.getText();
        String did = txtDid.getText();
        String iid = txtIid.getText();
        String name = txtName.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());

        try {


            OrderDetails orderDetails = new OrderDetails(oid,cid,did,iid,name,price,quantity);

            boolean isUpdated = OrderDetailModel.updateOrderItem(orderDetails);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Updated !\n wrong input(s)").show();
            }
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Not Updated !"+e).show();
        }
        loadStockData("");
    }

    public void DeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Oid = txtOid.getText();

        Alert alert = new Alert(Alert.AlertType.WARNING, "Deleted Selected ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            Boolean isDeleted = OrderDetailModel.deleteOrderDetailTransactions(Oid);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
                loadStockData("");
            } else new Alert(Alert.AlertType.WARNING, "Try Again!").show();
        }

        loadStockData("");
        txtCid.setText("");


    }

    public void itemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //String  id = combItemId.getValue();
        try {
            ResultSet set = OrderDetailModel.getitemmdetails(String.valueOf(combItemId.getValue()));
            if (set.next()) {
                txtIid.setText(set.getString(1));
                txtName.setText(set.getString(2));
                txtPrice.setText(set.getString(3));
                txtQuantity.setText(set.getString(4));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
    private void setRegIdS() {
        try {
            ArrayList<String> codes = OrderDetailModel.loadItemCodes();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String code : codes) {
                obList.add(code);
            }
            combItemId.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}



