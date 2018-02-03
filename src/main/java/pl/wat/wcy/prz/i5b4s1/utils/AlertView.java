package pl.wat.wcy.prz.i5b4s1.utils;

import javafx.scene.control.Alert;
import pl.wat.wcy.prz.i5b4s1.controllers.Log;

import java.util.ResourceBundle;

public class AlertView {
    private ResourceBundle bundle = ResourceBundle.getBundle("bundles.language");
    public void infoAboutLoggedUser(){
        String userAll = (bundle.getString("id") +": " + Log.getCurrentUser().getId() + "\n");
        userAll += (bundle.getString("login") +": " + Log.getCurrentUser().getLogin() + "\n");
        userAll += (bundle.getString("mail") +": " + Log.getCurrentUser().getEmail() + "\n");
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("information"));
        alert.setHeaderText(bundle.getString("information.user"));
        alert.setContentText(userAll);
        alert.showAndWait();
    }
    public void alertInformation(String titleKey, String headerTextKey, String contentTextKey){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString(titleKey));
        alert.setHeaderText(bundle.getString(headerTextKey));
        alert.setContentText(bundle.getString(contentTextKey));
        alert.showAndWait();
    }
    public void alertError(String titleKey, String headerTextKey, String contentTextKey) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(bundle.getString(titleKey));
        alert.setHeaderText(bundle.getString(headerTextKey));
        alert.setContentText(bundle.getString(contentTextKey));
        alert.showAndWait();
    }
}
