package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaperController {

    @FXML
    private Button exitButton;

    @FXML
    private TextField noteField;

    @FXML
    private Button addNoteButton;

    @FXML
    private TextArea notesField;

    @FXML
    void initialize() {
        getRecords(Username.getName());


        exitButton.setOnAction(event -> {
            Username.eraseName();
            openNewScene("/sample/sample.fxml");
        });

        addNoteButton.setOnAction(event -> {
            String note = noteField.getText();
            DatabaseHandler dbHandler = new DatabaseHandler();

            String select = "SELECT id FROM users WHERE username = ?";


            try {
                PreparedStatement prStCheck = dbHandler.getDbConnection().prepareStatement(select);
                prStCheck.setString(1, Username.getName());

                var result = prStCheck.executeQuery();
                result.next();
                int id = result.getInt("id");
                String insert = "INSERT INTO paper" + "(" + "id,text" +
                        ")" +
                        String.format(" VALUES(%d, ?)", id);
                prStCheck = dbHandler.getDbConnection().prepareStatement(insert);
                prStCheck.setString(1, note);
                prStCheck.executeUpdate();
                getRecords(Username.getName());


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private void getRecords(String username) {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String select = "SELECT text FROM paper p INNER JOIN users u ON username = ? WHERE u.id = p.id";


        try {
            PreparedStatement prStCheck = dbHandler.getDbConnection().prepareStatement(select);
            prStCheck.setString(1, username);

            var result = prStCheck.executeQuery();

            StringBuilder res = new StringBuilder();

            try {
                while (result.next())
                    res.append(result.getString("text")).append('\n');

            } catch (SQLException e) {
                e.printStackTrace();
            }


            notesField.setText(res.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openNewScene(String window) {

        Stage exitStage = (Stage) exitButton.getScene().getWindow();
        exitStage.close();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
