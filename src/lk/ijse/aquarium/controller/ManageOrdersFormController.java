package lk.ijse.aquarium.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.aquarium.model.CustomerModel;
import lk.ijse.aquarium.model.OrdersModel;
import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrdersFormController {
    public JFXTextField txtId;
    public JFXTextField txtTime;
    public JFXTextField txtCusId;
    public JFXTextField txtDiliId;
    public TableView tblOrder;
    public TableColumn colId;
    public TableColumn colCusId;
    public TableColumn colDid;
    public TableColumn colTime;

    public void btnAddOrdersOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        String cid = txtCusId.getText();
        String did =txtDiliId.getText();

        Orders orders = new Orders(id , cid, did);
        try {
            boolean isAdded = OrdersModel.saveOrders(orders);

            if(isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Added!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Error!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllOrders();
    }

    public void btnUpdateOrdersOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String cid = txtCusId.getText();
        String did =txtDiliId.getText();

        Orders orders =new Orders(id,cid,did);
        boolean isUpdated = false;
        try {
            isUpdated = OrdersModel.updateOrders(orders, id);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            } else
                new Alert(Alert.AlertType.ERROR, " Not Updated!").show();
        } catch (SQLException e) {
            System.out.println("SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Class");
        }

        loadAllOrders();
    }

    public void btnDeleteOrderOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        Orders orders =new Orders(id);
        try {
            boolean isDeleted = OrdersModel.DeleteOrders(orders,id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllOrders();
    }

    public void btnSearchOrderOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            Orders orders = OrdersModel.searchOrders(id);
            if(orders != null) {
                txtId.setText(orders.getId());
                txtCusId.setText(orders.getCid());
                txtDiliId.setText(orders.getDid());
            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize()  {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDid.setCellValueFactory(new PropertyValueFactory<>("cid"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("did"));

        loadAllOrders();
    }

    private void loadAllOrders() {
        ObservableList<Orders> CList = FXCollections.observableArrayList();

        try {
            ArrayList<Orders> orderData = OrdersModel.getOrderData();

            for (Orders st : orderData) {

                Orders s = new Orders(st.getId(), st.getCid(), st.getDid());
                CList.add(s);

            }
        }catch (SQLException e){
            System.out.println("sql");
        }catch (ClassNotFoundException e){
            System.out.println("Class");
        }
        tblOrder.setItems(CList);
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

    public void btnNewOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String nextID = generateNextStockItemID(OrdersModel.getCurrentItemID());
        txtId.setText(nextID);
    }
}
