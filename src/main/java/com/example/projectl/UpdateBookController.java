package com.example.projectl;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;


    public class UpdateBookController {
        @FXML
        private TextField nameField;

        @FXML
        private TextField authorField;

        @FXML
        private DatePicker dateField;

        @FXML
        private TextField priceField;

        private Book book;

        public UpdateBookController(Book book) {
            this.book = book;
        }

        public UpdateBookController() {

        }

        private Runnable updateCallback;
        public void setBook(Book book) {
            this.book = book;
            nameField.setText(book.getName());
            authorField.setText(book.getAuthor());

            Instant instant = book.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            java.util.Date utilDate = java.util.Date.from(instant);
            dateField.setValue(utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            priceField.setText(Float.toString(book.getPrice()));
        }

        @FXML
        void updateBook() {
            String name = nameField.getText();
            String author = authorField.getText();
            LocalDate date = dateField.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(date);
            float price = Float.parseFloat(priceField.getText());

            DBconnect connect = new DBconnect();
            Connection connection = connect.getConnection();
            try{
                String query = "UPDATE tbooks SET name=?, author=?, date=?, price=? WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, author);
                preparedStatement.setDate(3, sqlDate);
                preparedStatement.setFloat(4, price);
                preparedStatement.setInt(5, book.getId());
                preparedStatement.executeUpdate();
                connection.close();
                if (updateCallback != null) {
                    updateCallback.run();
                }

                Stage stage = (Stage) nameField.getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public void setUpdateCallback(Runnable callback) {
            this.updateCallback = callback;
        }
    }
