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
import lk.ijse.aquarium.model.CustomerPaymentModel;
import lk.ijse.aquarium.model.SupplierPaymentModel;
import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.CustomerPayment;
import lk.ijse.aquarium.to.SupplierPayment;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageCustomerPaymentFormController {

    public TableView tableCustomerPayment;
    public TableColumn colPaymentId;
    public TableColumn colValue;
    public TableColumn colCusId;
    public JFXTextField txtcustomer;
    public JFXTextField txtValue;
    public JFXTextField txtId;

    public void btnAddOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        Double value = Double.valueOf(txtValue.getText());
        String cid = txtcustomer.getText();


        CustomerPayment customerPayment = new CustomerPayment(id, value, cid);
        try {
            boolean isAdded = CustomerPaymentModel.save(customerPayment);

            if(isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Pay Added!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Error!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllCustomerPayment();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        Double value = Double.valueOf(txtValue.getText());
        String cid = txtcustomer.getText();


        CustomerPayment customerPayment =new CustomerPayment(id,value,cid);
        boolean isUpdated = false;
        try {
            isUpdated = CustomerPaymentModel.updateCustomerPayment(customerPayment, id);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            } else
                new Alert(Alert.AlertType.ERROR, " Not Updated!").show();
        } catch (SQLException e) {
            System.out.println("SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Class");
        }
        loadAllCustomerPayment();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        CustomerPayment customerPayment =new CustomerPayment(id);
        try {
            boolean isDeleted = CustomerPaymentModel.DeleteCustomerPayment(customerPayment,id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllCustomerPayment();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            CustomerPayment customerPayment = CustomerPaymentModel.searchCustomerPayment(id);
            if(customerPayment != null) {
                txtId.setText(customerPayment.getId());
                txtValue.setText(String.valueOf(customerPayment.getValue()));
                txtcustomer.setText(customerPayment.getcId());

            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize()  {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("cId"));


        loadAllCustomerPayment();
    }

    private void loadAllCustomerPayment() {
        ObservableList<CustomerPayment> CList = FXCollections.observableArrayList();

        try {
            ArrayList<CustomerPayment> customerrData = CustomerPaymentModel.getCustomerPaymentData();

            for (CustomerPayment st : customerrData) {

                CustomerPayment s = new CustomerPayment(st.getId(), st.getValue(), st.getcId());
                CList.add(s);

            }
        }catch (SQLException e){
            System.out.println("sql");
        }catch (ClassNotFoundException e){
            System.out.println("Class");
        }
        tableCustomerPayment.setItems(CList);
    }
}
