package lk.ijse.aquarium.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.aquarium.util.Navigation;
import lk.ijse.aquarium.util.Routes;

import java.io.IOException;

public class MainFormController {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public AnchorPane pane;

    public void LoggingOnAction(ActionEvent actionEvent) throws IOException {
       if(txtUserName.getText().equals("admin") && txtPassword.getText().equals("1234")) {
            Navigation.navigate(Routes.DASHBOARD, pane);
       }
        else  new Alert(Alert.AlertType.ERROR, "Try again!").show();
    }

    public void CancelLogOnAction(ActionEvent actionEvent) {
       // System.exit(0);
    }
}
