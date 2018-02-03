package pl.wat.wcy.prz.i5b4s1.controllers;

import com.aquafx_project.AquaFx;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import pl.wat.wcy.prz.i5b4s1.utils.AlertView;

import java.io.IOException;
import java.util.ResourceBundle;

import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.setUserAgentStylesheet;

public class Application {
    private final static Logger logger = Logger.getLogger(Application.class);
    private ResourceBundle bundle = ResourceBundle.getBundle("bundles.language");
    private AlertView alert = new AlertView();
    @FXML
    private BorderPane borderPane;
    @FXML
    private MenuItem skin1, skin2, skin3, edit, time, logout, information, changePassword;

    private void setCenter(String fxmlPath){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setResources(bundle);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            logger.error("Błąd podczas wyświetlania centralnej części aplikacji", e);
        }
        borderPane.setCenter(parent);
    }
    private EventHandler<ActionEvent> skin1Handler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            setUserAgentStylesheet(STYLESHEET_CASPIAN);
        }
    };
    private EventHandler<ActionEvent> skin2Handler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            setUserAgentStylesheet(STYLESHEET_MODENA);
        }
    };
    private EventHandler<ActionEvent> skin3Handler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            AquaFx.style();
        }
    };
    private EventHandler<ActionEvent> timeHandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            setCenter("/view/start.fxml");
        }
    };
    private EventHandler<ActionEvent> editHandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            setCenter("/view/laboperation.fxml");
        }
    };
    private EventHandler<ActionEvent> informationUserHandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            alert.infoAboutLoggedUser();
        }
    };
    private EventHandler<ActionEvent> changePasswordHandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            setCenter("/view/changepassword.fxml");
        }
    };
    private EventHandler<ActionEvent> logouthandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            Stage stage = (Stage) borderPane.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/log.fxml"));
            fxmlLoader.setResources(bundle);
            Parent root;
            Scene scene = null;
            try {
                root = fxmlLoader.load();
                scene = new Scene(root);
            } catch (IOException e) {
                logger.error("Błąd przy zmianie widoku przy wylogowaniu", e);
            }
            stage.setScene(scene);
            stage.show();
        }
    };

    @FXML
    public void initialize(){
        setCenter("/view/start.fxml");
        skin1.addEventHandler(ActionEvent.ACTION,skin1Handler);
        skin2.addEventHandler(ActionEvent.ACTION,skin2Handler);
        skin3.addEventHandler(ActionEvent.ACTION,skin3Handler);
        edit.addEventHandler(ActionEvent.ACTION,editHandler);
        time.addEventHandler(ActionEvent.ACTION,timeHandler);
        logout.addEventHandler(ActionEvent.ACTION,logouthandler);
        information.addEventHandler(ActionEvent.ACTION,informationUserHandler);
        changePassword.addEventHandler(ActionEvent.ACTION,changePasswordHandler);
    }
}
