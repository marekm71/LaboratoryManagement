package pl.wat.wcy.prz.i5b4s1.controllers;

import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.log4j.Logger;
import pl.wat.wcy.prz.i5b4s1.exceptions.EmptyList;
import pl.wat.wcy.prz.i5b4s1.utils.Time;
import pl.wat.wcy.prz.i5b4s1.utils.Parser;
import pl.wat.wcy.prz.i5b4s1.utils.TimeUser;

import java.util.List;

public class Start {
    private Parser parser = new Parser();
    private TimeUser timeUser = new TimeUser();
    private Service<Time> timeService = timeUser.getTime();
    private final static Logger logger = Logger.getLogger(Log.class);
    @FXML
    private ImageView image;
    @FXML
    private ChoiceBox<String> listAuthor;
    @FXML
    private Label country, datetime;

    private void refresh(){
        timeService.restart();
    }
    @FXML
    public void refreshTime(){
        refresh();
    }
    @FXML
    public void initialize(){
        Image logo = new Image("file:src/image/watlogo.jpg");
        image.setImage(logo);
        Service<List<String>> parserList = parser.list();
        parserList.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                listAuthor.getItems().addAll(parserList.getValue());
                try{
                    if(listAuthor.getItems().size()==0) throw new EmptyList("Lista autorów aplikacji jest pusta");
                }
                catch (EmptyList e){
                    logger.error(e.getMessage(),e);
                }
            }
        });
        timeService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                country.setText(timeService.getValue().getTimezone());
                datetime.setText(timeService.getValue().getFulldate());
            }
        });
        parserList.start();
        timeService.start();
    }
}
