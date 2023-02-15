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
import lk.ijse.aquarium.model.SupplierModel;
import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageSupplierController {
    public JFXTextField txtId;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtName;
    public TableView tblSupplier;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;

    public void btnAddSupplierOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact =txtContact.getText();

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{4,}$");
        Matcher matcher = pattern.matcher(txtName.getText());

        Pattern tel = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$");
        Matcher telep = pattern.matcher(txtContact.getText());
        boolean isMatches = matcher.matches();
        boolean isMatch = telep.matches();

        if(isMatch && isMatches) {
            Supplier supplier = new Supplier(id, name, address, contact);
            try {
                boolean isAdded = SupplierModel.saveSupplier(supplier);

                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Error!").show();
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            loadAllSupplier();
        }else if (!isMatches) {
            txtName.setFocusColor(Paint.valueOf("RED"));
        }
        else txtContact.setFocusColor(Paint.valueOf("RED"));
    }

    public void btnUpdateSupplierOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact =txtContact.getText();

        Supplier supplier =new Supplier(id,name,address,contact);
        boolean isUpdated = false;
        try {
            isUpdated = SupplierModel.updateSupplier(supplier, id);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            } else
                new Alert(Alert.AlertType.ERROR, " Not Updated!").show();
        } catch (SQLException e) {
            System.out.println("SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Class");
        }
        loadAllSupplier();

    }

    public void btnSearchSupplierOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            Supplier supplier = SupplierModel.searchSupplier(id);
            if(supplier != null) {
                txtId.setText(supplier.getId());
                txtName.setText(supplier.getName());
                txtAddress.setText(supplier.getAddress());
                txtContact.setText(supplier.getContact());
            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnLoadAllOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteSupplierOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        Supplier supplier =new Supplier(id);
        try {
            boolean isDeleted = SupplierModel.DeleteSupplier(supplier,id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllSupplier();
    }
    public void initialize()  {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        loadAllSupplier();
    }

    private void loadAllSupplier() {
        ObservableList<Supplier> CList = FXCollections.observableArrayList();

        try {
            ArrayList<Supplier> supplierData = SupplierModel.getSupplierData();

            for (Supplier st : supplierData) {

                Supplier s = new Supplier(st.getId(), st.getName(), st.getAddress(), st.getContact());
                CList.add(s);

            }
        }catch (SQLException e){
            System.out.println("sql");
        }catch (ClassNotFoundException e){
            System.out.println("Class");
        }
        tblSupplier.setItems(CList);
    }

    private String generateNextStockItemID(String currentItemID) {
        System.out.println(currentItemID);
        if (currentItemID != null) {
            String[] ids = currentItemID.split("S00");
            int id = Integer.parseInt(ids[1]);
            id += 1;

            return "S00" + id;
        }
        return "S001";
    }
    public void btnNewOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String nextID = generateNextStockItemID(SupplierModel.getCurrentItemID());
        txtId.setText(nextID);
    }
}
