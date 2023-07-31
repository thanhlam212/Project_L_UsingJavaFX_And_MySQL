package com.example.projectl;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.util.Date;
import java.util.ResourceBundle;



public class MainController implements Initializable {

    @FXML
    private TableColumn<Book, Integer> idCol;

    @FXML
    private TableColumn<Book, String> nameCol;

    @FXML
    private TableColumn<Book, String > authorCol;

    @FXML
    private TableColumn<Book, Date> dateCol;

    @FXML
    private TableColumn<Book, Float> priceCol;

    @FXML
    private TableView<Book> booksTable;

    private ObservableList<Book> bookList;

    private Runnable updateCallback;
    String query = null;
    Connection conn = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Book book = null;

    @FXML
    void getAddView(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("addBooks.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void getDeletetn() {
        Book selectBook = (Book) booksTable.getSelectionModel().getSelectedItem();
        if (selectBook != null) {
            bookList.remove(selectBook);
            getDeleteData(selectBook);
        }
    }

    private void getDeleteData(Book book) {
        DBconnect connect = new DBconnect();
        Connection connectDB = connect.getConnection();

        try {
            PreparedStatement statement = connectDB.prepareStatement("DELETE FROM tbooks WHERE id = ?");
            statement.setInt(1, book.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void getRefreshView() throws SQLException {
        conn = DBconnect.getConnection();
        bookList.clear();

        query = "SELECT * FROM tbooks";
        preparedStatement = conn.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            bookList.add(new Book(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("author"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getFloat("price")));
            booksTable.setItems(bookList);
        }
    }

    @FXML
    void getUpdateView(MouseEvent event) {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if(selectedBook != null){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateBook.fxml"));
                Parent root = loader.load();
                UpdateBookController updateBookController = loader.getController();
                updateBookController.setUpdateCallback(() -> {
                    try {
                        getRefreshView();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                updateBookController.setBook(selectedBook);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }

    public void setUpdateCallback(Runnable callback){
        this.updateCallback = callback;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        bookList = FXCollections.observableArrayList();

        try {
            getRefreshView();
//            studentList.addAll(getDataFromDatabase());
            booksTable.setItems(bookList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




