package pl.wat.wcy.prz.i5b4s1.controllers;

import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import pl.wat.wcy.prz.i5b4s1.exceptions.ProblemAcceptingData;
import pl.wat.wcy.prz.i5b4s1.utils.AlertView;
import pl.wat.wcy.prz.i5b4s1.utils.Verification;
import pl.wat.wcy.prz.i5b4s1.database.model.User;
import pl.wat.wcy.prz.i5b4s1.database.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class Register {
    private Verification verification = new Verification();
    private UserService userService = new UserService();
    private AlertView alert = new AlertView();
    private ResourceBundle bundle = ResourceBundle.getBundle("bundles.language");
    private final static Logger logger = Logger.getLogger(Register.class);
    @FXML
    private GridPane gridPane;
    @FXML
    private Button registerButton, backButton;
    @FXML
    private TextField loginInput, emailInput;
    @FXML
    private PasswordField passwordInput, password2Input;

    private Stage waitForRegister(){
        Stage stage = new Stage();
        stage.setMinWidth(200);
        stage.setMinHeight(100);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/wait.fxml"));
        fxmlLoader.setResources(bundle);
        Parent root;
        Scene scene = null;
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            logger.error("Błąd podczas wyświetlania okienka oczekiwania na zarejestrowanie", e);
        }
        stage.setScene(scene);
        stage.show();
        return stage;
    }

    @FXML
    public void actionRegisterButton(){
        gridPane.setDisable(true);
        Stage stage1 = waitForRegister();
        Service<List<User>> allUser = userService.allUser();
        allUser.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                stage1.close();
                if (verification.verificationRegister(allUser.getValue(),loginInput.getText(),passwordInput.getText(),password2Input.getText(),emailInput.getText())) {
                    Service addUser = userService.addUser(new User(loginInput.getText(), passwordInput.getText(), emailInput.getText()));
                    addUser.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            alert.alertInformation("information","information","user.register");
                            Stage stage = (Stage) registerButton.getScene().getWindow();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/log.fxml"));
                            fxmlLoader.setResources(bundle);
                            Parent root;
                            Scene scene = null;
                            try {
                                root = fxmlLoader.load();
                                scene = new Scene(root);
                            } catch (IOException e) {
                                logger.error("Błąd podczas wyświetlenia okienka logowanie po udanej rejestracji", e);
                            }
                            stage.setScene(scene);
                            stage.show();
                        }
                    });
                    addUser.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            alert.alertError("error","error","problem.adduser");
                            gridPane.setDisable(false);
                        }
                    });
                    addUser.start();
                }
                else{
                    String tekst = "";
                    if (verification.existUserLogin(allUser.getValue(),loginInput.getText())) tekst += (bundle.getString("login.exists") + "\n");
                    if (!verification.verificationLoginLength(loginInput.getText())) tekst += (bundle.getString("incorrect.login") + "\n");
                    if (!verification.verificationPasswordLength(passwordInput.getText())) tekst += (bundle.getString("incorrect.password") + "\n");
                    if (!verification.verificationPasswords(passwordInput.getText(),password2Input.getText())) tekst += (bundle.getString("different.passwords") + "\n");
                    if (!verification.verificationEmailLength(emailInput.getText())) tekst += (bundle.getString("incorrect.mail") + "\n");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(bundle.getString("error"));
                    alert.setHeaderText(bundle.getString("incorrect.data"));
                    alert.setContentText(tekst);
                    alert.showAndWait();
                    gridPane.setDisable(false);
                    try{
                        if (verification.existUserLogin(allUser.getValue(),loginInput.getText())) throw new ProblemAcceptingData("Problem z weryfikacją danych, podany login już istnieje");
                    }
                    catch (ProblemAcceptingData e){
                        logger.error(e.getMessage(),e);
                    }
                }
            }
        });
        allUser.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                stage1.close();
                alert.alertError("error","error","problem.connect");
                gridPane.setDisable(false);
            }
        });
        allUser.start();
    }

    @FXML
    public void actionBackButton(){
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/log.fxml"));
        fxmlLoader.setResources(bundle);
        Parent root;
        Scene scene = null;
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            logger.error("Błąd podczas powrotu do okienka logowanie", e);
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void initialize() {
    }
}
