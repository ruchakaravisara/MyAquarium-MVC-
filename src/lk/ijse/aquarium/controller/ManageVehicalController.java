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
import lk.ijse.aquarium.model.VehicalModel;
import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.Vehical;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageVehicalController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtEid;
    public TableView tblVehical;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colEid;

    public void btnAddVehicalOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String eid = txtEid.getText();
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{4,}$");
        Matcher matcher = pattern.matcher(txtName.getText());
        boolean isMatches = matcher.matches();
        if(isMatches) {
            Vehical vehical = new Vehical(id, name, eid);
            try {
                boolean isAdded = VehicalModel.saveVehical(vehical);

                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Vehical Added!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Error!").show();
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            loadAllVehical();
        }else txtName.setFocusColor(Paint.valueOf("RED"));
    }

    public void btnUpdateVehicalOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String empid = txtEid.getText();


        Vehical vehical =new Vehical(id,name,empid);
        boolean isUpdated = false;
        try {
            isUpdated = VehicalModel.updateVehical(vehical, id);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            } else
                new Alert(Alert.AlertType.ERROR, " Not Updated!").show();
        } catch (SQLException e) {
            System.out.println("SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Class");
        }
        loadAllVehical();
    }

    public void btnDeleteVehicalOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        Vehical vehical =new Vehical(id);
        try {
            boolean isDeleted = VehicalModel.DeleteVehical(vehical,id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllVehical();

    }

    public void btnSearchVehicalOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            Vehical vehical = VehicalModel.searchVehical(id);
            if(vehical != null) {
                txtId.setText(vehical.getId());
                txtName.setText(vehical.getName());
                txtEid.setText(vehical.getEmpid());

            }
            else new Alert(Alert.AlertType.ERROR,"NOT FOUND!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize()  {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEid.setCellValueFactory(new PropertyValueFactory<>("empid"));


        loadAllVehical();
    }

    private void loadAllVehical() {
        ObservableList<Vehical> CList = FXCollections.observableArrayList();

        try {
            ArrayList<Vehical> vehicalData = VehicalModel.getVehicalData();

            for (Vehical st : vehicalData) {

                Vehical s = new Vehical(st.getId(), st.getName(), st.getEmpid());
                CList.add(s);

            }
        }catch (SQLException e){
            System.out.println("sql");
        }catch (ClassNotFoundException e){
            System.out.println("Class");
        }
        tblVehical.setItems(CList);
    }
    private String generateNextStockItemID(String currentItemID) {
        System.out.println(currentItemID);
        if (currentItemID != null) {
            String[] ids = currentItemID.split("V00");
            int id = Integer.parseInt(ids[1]);
            id += 1;

            return "V00" + id;
        }
        return "V001";
    }

    public void btnNewOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String nextID = generateNextStockItemID(VehicalModel.getCurrentItemID());
        txtId.setText(nextID);
    }
}
