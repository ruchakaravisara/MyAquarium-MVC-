package lk.ijse.aquarium.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.aquarium.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;


import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class DashBoardFormController {
    public Label lblDate;
    public AnchorPane contex;
    public AnchorPane pane;
    public Label lblTime1;

    public void initialize(){
        LocalDate date  =LocalDate.now();
        lblDate.setText(String.valueOf(date));
        LocalTime time =LocalTime.now();
        lblTime1.setText(String.valueOf(time));
    }


    public void ManageEmpOnAction(ActionEvent actionEvent) throws IOException {
        setUi("/lk/ijse/aquarium/view/ManageEmployeeForm");
    }
    public void setUi(String ui) throws IOException {
        Parent node = FXMLLoader.load(getClass().getResource(ui+".fxml"));
        contex.getChildren().clear();
        contex.getChildren().add(node);
    }

    public void ManageSupplierOnAction(ActionEvent actionEvent) throws IOException {
        setUi("/lk/ijse/aquarium/view/ManageSupplier");
    }

    public void ManageItemOnAction(ActionEvent actionEvent) throws IOException {
        setUi("/lk/ijse/aquarium/view/ManageItemForm");
    }

    public void ManageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("/lk/ijse/aquarium/view/ManageCustomerForm");
    }

    public void ManageVehicalOnAction(ActionEvent actionEvent) throws IOException {
        setUi("/lk/ijse/aquarium/view/ManageVehical");
    }

    public void ManageDilivaryOnAction(ActionEvent actionEvent) throws IOException {
        setUi("/lk/ijse/aquarium/view/ManageDilivaryForm");
    }

    public void ManageOrdersOnAction(ActionEvent actionEvent) throws IOException {
        setUi("/lk/ijse/aquarium/view/ManageOrdersForm");
    }

    public void SuppllierPaymentOnAction(ActionEvent actionEvent) throws IOException {
        setUi("/lk/ijse/aquarium/view/ManageSupplierPaymentForm");
    }

    public void CustomerPaymentOnAction(ActionEvent actionEvent) throws IOException {
        setUi("/lk/ijse/aquarium/view/ManageCustomerPaymentForm");
    }

    public void ManageOrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        setUi("/lk/ijse/aquarium/view/PlaceOrderForm");
    }

    public void ManageSupplierDetailsOnAction(ActionEvent actionEvent) throws IOException {
        setUi("/lk/ijse/aquarium/view/SupplierItems");
    }

    public void ManageCustomerReportOnAction(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        HashMap<String, Object> h =new HashMap<>();
        h.put("cashierName","Ruchaka");
       // h.put("Table Name",txtG.getText());


        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/aquarium/view/CustomerReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(resource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,h, DBConnection.getDbConnection().getConnection());
        // JasperPrintManager.printReport(jasperPrint,true);
        JasperViewer.viewReport(jasperPrint,false);
    }

    public void ManageSupplierReportOnAction(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        HashMap<String, Object> h =new HashMap<>();
        h.put("cashierName","Ruchaka");
       // h.put("Table Name",txtG.getText());


        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/aquarium/view/SupplierReportForm.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(resource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,h,DBConnection.getDbConnection().getConnection());
        // JasperPrintManager.printReport(jasperPrint,true);
        JasperViewer.viewReport(jasperPrint,false);
    }
}
