package pl.wat.wcy.prz.i5b4s1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.wat.wcy.prz.i5b4s1.database.dbutil.HibernateUtil;

import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.language");
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(350);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/log.fxml"));
        fxmlLoader.setResources(bundle);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle(bundle.getString("tittle.application"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception{
        HibernateUtil.closeSessionFactory();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
