package com.example.projectl;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class addBookController {

    @FXML
    private TextField authorField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    String query = null;
    Connection conn = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    private boolean update;

    int bookID;
    Book book =null;


    @FXML
    void clearBtn(MouseEvent event) {

    }

    @FXML
    private void saveBtn(MouseEvent event) {
        conn = DBconnect.getConnection();
        String name = nameField.getText();
        String address = authorField.getText();
        String birth = String.valueOf(dateField.getValue());
        float price = Float.parseFloat(priceField.getText());

        if(name.isEmpty() || birth.isEmpty() || address.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill all data");
            alert.showAndWait();
        }else {
            getQuery();
            insert();
            clearField();
        }
    }

    private void insert() {
        try{
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, bookID);
            preparedStatement.setString(2, nameField.getText());
            preparedStatement.setString(3, authorField.getText());
            preparedStatement.setString(4, String.valueOf(dateField.getValue()));
            preparedStatement.setFloat(5, Float.parseFloat(priceField.getText()));
            preparedStatement.execute();

        }catch (SQLException e){
            Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void clearField() {
        nameField.setText(null);
        authorField.setText(null);
        dateField.setValue(null);
        priceField.setText(null);
    }

    private void getQuery() {
        if (update == false) {

            query = "INSERT INTO `tbooks`(`id`, `name`, `author`, `date`, `price`) VALUES (?, ?, ?, ?, ?)";

        }else{
            query = "UPDATE `tbooks` SET "
                    + "`name`=?,"
                    + "`author`=?,"
                    + "`date`=?,"
                    + "`price`= ? WHERE id = '"+bookID+"'";
        }
    }
}
