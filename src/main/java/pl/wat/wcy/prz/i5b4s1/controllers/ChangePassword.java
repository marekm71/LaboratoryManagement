package pl.wat.wcy.prz.i5b4s1.controllers;

import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.log4j.Logger;
import pl.wat.wcy.prz.i5b4s1.exceptions.IncorrectOldPassword;
import pl.wat.wcy.prz.i5b4s1.utils.AlertView;
import pl.wat.wcy.prz.i5b4s1.utils.Verification;
import pl.wat.wcy.prz.i5b4s1.database.model.User;
import pl.wat.wcy.prz.i5b4s1.database.service.UserService;

public class ChangePassword {
    private Verification verification = new Verification();
    private AlertView alert = new AlertView();
    private final static Logger logger = Logger.getLogger(Log.class);
    private UserService userService = new UserService();
    @FXML
    private ImageView image;
    @FXML
    private TextField oldPasswordInput;
    @FXML
    private PasswordField newPasswordInput, newPasswordRepeatInput;
    @FXML
    public void change(){
        User user = Log.getCurrentUser();
        if(verification.verificationChangePassword(user,oldPasswordInput.getText(),newPasswordInput.getText(),newPasswordRepeatInput.getText())) {
            user.setPassword(newPasswordInput.getText());
            Service updateUser = userService.updateUser(user);
            updateUser.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    alert.alertInformation("information","information","change.password");
                }
            });
            updateUser.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    alert.alertError("error","error","problem.changepassword");
                }
            });
            updateUser.start();
        }
        else{
            alert.alertError("error", "error", "incorrect.data");
            try {
                if(!verification.verificationOldPassword(user,oldPasswordInput.getText())) throw new IncorrectOldPassword("Błędnie wprowadzono aktualne hasło");
            }
            catch (IncorrectOldPassword e){
                logger.error(e.getMessage(), e);
            }
        }
    }
    @FXML
    public void initialize(){
        Image logo = new Image("file:src/image/watlogo.jpg");
        image.setImage(logo);
    }
}
