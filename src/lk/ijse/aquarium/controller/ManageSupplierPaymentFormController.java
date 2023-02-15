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
import lk.ijse.aquarium.model.SupplierPaymentModel;
import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.SupplierPayment;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageSupplierPaymentFormController {
    public JFXTextField txtId;
    public JFXTextField txtValue;
    public JFXTextField txtSid;
    public TableView tblSupplierPayment;
    public TableColumn colSPayId;
    public TableColumn colValue;
    public TableColumn colSupplierId;

    public void btnAddOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        Double value = Double.valueOf(txtValue.getText());
        String sid = txtSid.getText();


        SupplierPayment supplierPayment = new SupplierPayment(id, value, sid);
        try {
            boolean isAdded = SupplierPaymentModel.save(supplierPayment);

            if(isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Pay Added!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Error!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllSupplierPayment();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        Double value = Double.valueOf(txtValue.getText());
        String sid = txtSid.getText();

       SupplierPayment supplierPayment =new SupplierPayment(id,value,sid);
        boolean isUpdated = false;
        try {
            isUpdated = SupplierPaymentModel.updateSupplierPayment(supplierPayment, id);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            } else
                new Alert(Alert.AlertType.ERROR, " Not Updated!").show();
        } catch (SQLException e) {
            System.out.println("SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Class");
        }
        loadAllSupplierPayment();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        SupplierPayment supplierPayment =new SupplierPayment(id);
        try {
            boolean isDeleted = SupplierPaymentModel.DeleteSupplierPayment(supplierPayment,id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllSupplierPayment();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            SupplierPayment supplierPayment = SupplierPaymentModel.searchSupplierPayment(id);
            if(supplierPayment != null) {
                txtId.setText(supplierPayment.getId());
                txtValue.setText(String.valueOf(supplierPayment.getValue()));
                txtSid.setText(supplierPayment.getSid());

            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize()  {
        colSPayId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("sid"));


        loadAllSupplierPayment();
    }

    private void loadAllSupplierPayment() {
        ObservableList<SupplierPayment> CList = FXCollections.observableArrayList();

        try {
            ArrayList<SupplierPayment> supplierData = SupplierPaymentModel.getSupplierPaymentData();

            for (SupplierPayment st : supplierData) {

               SupplierPayment s = new SupplierPayment(st.getId(), st.getValue(), st.getSid());
                CList.add(s);

            }
        }catch (SQLException e){
            System.out.println("sql");
        }catch (ClassNotFoundException e){
            System.out.println("Class");
        }
        tblSupplierPayment.setItems(CList);
    }
}
