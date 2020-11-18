package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {

        signUpButton.setOnAction(event -> {
            String userName = loginField.getText().trim();
            String password = passwordField.getText().trim();
            signUpNewUser(userName, password);
        });

    }

    public void signUpNewUser(String userName, String password) {
        DatabaseHandler dbHandler = new DatabaseHandler();

        if (userName.length() >= 4 && userName.length() <= 12
                && password.length() >= 6 && password.length() <= 12) {
            User user = new User(userName, password);
            if (dbHandler.signUpUser(user)) {
                Username.getInstance();
                Username.setName(userName);
                openNewScene("/sample/paper.fxml");
            }
        } else
            Controller.showAlert("Некорректная длина пароля или логина.");
    }

    public void openNewScene(String window) {
        Stage exitStage = (Stage) signUpButton.getScene().getWindow();
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
