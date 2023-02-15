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
import lk.ijse.aquarium.model.OrderDetailModel;
import lk.ijse.aquarium.to.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageCustomerFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableView tblCustomer;

    public void btnAddOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
//////
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{4,}$");
        Matcher matcher = pattern.matcher(txtName.getText());

        Pattern tel = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$");
        Matcher telep = pattern.matcher(txtContact.getText());
        boolean isMatches = matcher.matches();
        boolean isMatch = telep.matches();
        if (isMatches && isMatch) {

        Customer customer = new Customer(id, name, address, contact);
        try {
            boolean isAdded = CustomerModel.save(customer);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Error!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllCustomers();
    } else if (!isMatches) {
            txtName.setFocusColor(Paint.valueOf("RED"));
        }
        else txtContact.setFocusColor(Paint.valueOf("RED"));
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact =txtContact.getText();

        Customer customer =new Customer(id,name,address,contact);
        boolean isUpdated = false;
        try {
            isUpdated = CustomerModel.updateCustomer(customer, id);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            } else
                new Alert(Alert.AlertType.ERROR, " Not Updated!").show();
        } catch (SQLException e) {
            System.out.println("SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Class");
        }
        loadAllCustomers();

    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            Customer customer = CustomerModel.searchCustomer(id);
            if(customer != null) {
                txtId.setText(customer.getId());
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtContact.setText(customer.getContact());
            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        Customer customer =new Customer(id);
        try {
            boolean isDeleted = CustomerModel.DeleteCustomer(customer,id);

            if(isDeleted) {
               new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllCustomers();
    }


    public void initialize()  {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        loadAllCustomers();
    }

    private void loadAllCustomers() {
        ObservableList<Customer> CList = FXCollections.observableArrayList();

       try {
           ArrayList<Customer> customerData = CustomerModel.getCustomerData();

           for (Customer st : customerData) {

               Customer s = new Customer(st.getId(), st.getName(), st.getAddress(), st.getContact());
                    CList.add(s);

           }
       }catch (SQLException e){
           System.out.println("sql");
       }catch (ClassNotFoundException e){
           System.out.println("Class");
       }
        tblCustomer.setItems(CList);
    }

    private String generateNextStockItemID(String currentItemID) {
        System.out.println(currentItemID);
        if (currentItemID != null) {
            String[] ids = currentItemID.split("C00");
            int id = Integer.parseInt(ids[1]);
            id += 1;

            return "C00" + id;
        }
        return "C001";
    }

    public void btnNewOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String nextID = generateNextStockItemID(CustomerModel.getCurrentItemID());
        txtId.setText(nextID);
    }
}
