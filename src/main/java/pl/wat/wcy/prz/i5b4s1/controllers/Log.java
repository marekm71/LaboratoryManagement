package pl.wat.wcy.prz.i5b4s1.controllers;

import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.event.*;
import org.apache.log4j.Logger;
import pl.wat.wcy.prz.i5b4s1.utils.AlertView;
import pl.wat.wcy.prz.i5b4s1.utils.Verification;
import pl.wat.wcy.prz.i5b4s1.database.model.User;
import pl.wat.wcy.prz.i5b4s1.database.service.UserService;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Log {
    private static User user = new User();
    private UserService userService = new UserService();
    private Verification verification = new Verification();
    private AlertView alert = new AlertView();
    private ResourceBundle bundle = ResourceBundle.getBundle("bundles.language");
    private final static Logger logger = Logger.getLogger(Log.class);
    @FXML
    private GridPane gridPane;
    @FXML
    private Button loginButton, registerButton;
    @FXML
    private TextField loginInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private ChoiceBox<String> listLanguage;

    public static User getCurrentUser(){
        return user;
    }
    private static void setCurrentUser(User u){
        user=u;
    }

    private Stage waitForLog(){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/wait.fxml"));
        fxmlLoader.setResources(bundle);
        stage.setMinWidth(200);
        stage.setMinHeight(100);
        Parent root;
        Scene scene = null;
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            logger.error("Błąd podczas wyświetlania okienka oczekiwania na zalogowanie", e);
        }
        stage.setScene(scene);
        stage.show();
        return stage;
    }

    @FXML
    public void actionLoginButton(){
        gridPane.setDisable(true);
        Stage stage1 = waitForLog();
        Service<User> user = userService.getUserByLogin(loginInput.getText());
        user.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                stage1.close();
                if(verification.verificationLog(user.getValue(),passwordInput.getText())) {
                    Log.setCurrentUser(user.getValue());
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/application.fxml"));
                    fxmlLoader.setResources(bundle);
                    Parent root;
                    Scene scene = null;
                    try {
                        root = fxmlLoader.load();
                        scene = new Scene(root);
                    } catch (IOException e) {
                        logger.error("Błąd przy przeładowanie widoku po porawnym zalogowaniu", e);
                    }
                    stage.setScene(scene);
                    stage.show();
                }
                else{
                    alert.alertError("error","error","incorrect.data");
                    gridPane.setDisable(false);
                }
            }
        });
        user.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                stage1.close();
                alert.alertError("error","error","problem.connect");
                gridPane.setDisable(false);
            }
        });
        user.start();
    }

    @FXML
    public void actionRegisterButton(){
        Stage stage = (Stage) registerButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/register.fxml"));
        fxmlLoader.setResources(bundle);
        Parent root;
        Scene scene = null;
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            logger.error("Błąd przy przeładowanie widoku z logowania na rejestrację", e);
        }
        stage.setScene(scene);
        stage.show();
    }

    private void reload(){
        Stage primaryStage = (Stage) registerButton.getScene().getWindow();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.language");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/log.fxml"));
        fxmlLoader.setResources(resourceBundle);
        Parent root;
        Scene scene = null;
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            logger.error("Błąd przy przeładowaniu widoku po zmianie języka", e);
        }
        primaryStage.setTitle(resourceBundle.getString("tittle.application"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private EventHandler<ActionEvent> language = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            if(listLanguage.getValue().equals(bundle.getString("polish"))){
                Locale.setDefault(new Locale("pl"));
                reload();
            }
            else{
                Locale.setDefault(new Locale("en"));
                reload();
            }
        }
    };

    @FXML
    void initialize(){
        listLanguage.getItems().addAll(bundle.getString("polish"),bundle.getString("english"));
        listLanguage.addEventHandler(ActionEvent.ACTION,language);
    }

}
