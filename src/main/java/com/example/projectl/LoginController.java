package com.example.projectl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    @FXML
    private Label loginMessageLabel;

    @FXML
    private Button outBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void loginBTn(ActionEvent event) {
       if (usernameField.getText().isBlank() == false && passwordField.getText().isBlank() == false){
           loginMessageLabel.setText("You try to login !");
           validationLogin();
       }else {
           loginMessageLabel.setText("Please enter your username and password");
       }
    }

    @FXML
    void outBtn(ActionEvent event) {
        Stage stage = (Stage) outBtn.getScene().getWindow();
        stage.close();
    }

    public void validationLogin(){
        DBconnect connection  = new DBconnect();
        Connection connectDB = connection.getConnection();

        String verifyLogin = "SELECT count(1) FROM useracount WHERE username= '" + usernameField.getText() + "' AND password = '"+ passwordField.getText() +"'";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if (queryResult.getInt(1) == 1){
                    loginMessageLabel.setText("Welcome!");
                    loadMain();
                }else {
                    loginMessageLabel.setText("Ivalid Login. Please try again");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadMain(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) outBtn.getScene().getWindow();
            loginStage.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
