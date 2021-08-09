package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Scene1Controller {
    @FXML
    private TextField UsernameInputBox;
    @FXML
    private TextField PasswordInputBox;

    public void Login(ActionEvent event) throws Exception {
        String username = UsernameInputBox.getText();
        String password = PasswordInputBox.getText();
        //Helper.DexLogin(username, password);
        switchToScene2(event);
    }


    public void switchToScene2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Fxml/Scene2.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setX(0);
        stage.setY(0);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}