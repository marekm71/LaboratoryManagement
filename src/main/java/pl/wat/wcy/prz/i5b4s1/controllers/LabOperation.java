package pl.wat.wcy.prz.i5b4s1.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.apache.log4j.Logger;
import pl.wat.wcy.prz.i5b4s1.exceptions.EmptyTextData;
import pl.wat.wcy.prz.i5b4s1.exceptions.IncorrectData;
import pl.wat.wcy.prz.i5b4s1.utils.AlertView;
import pl.wat.wcy.prz.i5b4s1.database.model.Equipment;
import pl.wat.wcy.prz.i5b4s1.database.model.Lab;
import pl.wat.wcy.prz.i5b4s1.database.service.EquipmentService;
import pl.wat.wcy.prz.i5b4s1.database.service.LabService;
import java.util.*;

public class LabOperation {
    private LabService labService = new LabService();
    private EquipmentService equipmentService = new EquipmentService();
    private AlertView alert = new AlertView();
    private final static Logger logger = Logger.getLogger(Log.class);
    private ResourceBundle bundle = ResourceBundle.getBundle("bundles.language");
    @FXML
    private ChoiceBox<String> list;
    @FXML
    private TableView<Equipment>  tableViewEquipment;
    @FXML
    private TableColumn<Equipment, Integer> id;
    @FXML
    private TableColumn<Equipment, String> name;
    @FXML
    private TableColumn<Equipment, String> serialNumber;
    @FXML
    private TextField serialNumberEquipment, nameEquipment;
    @FXML
    private Button buttonRemoveEquipment, buttonViewEquipment, buttonRemoveLab, buttonAddEquipment;

    private void refreshViewEquipment(){
        if(!list.getItems().isEmpty()) {
            Service<ObservableList<Equipment>> listElement = equipmentService.getListEquipmentByNumberLab(Integer.parseInt(list.getValue()));
            listElement.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    tableViewEquipment.setItems(listElement.getValue());
                    id.setCellValueFactory(new PropertyValueFactory<>("id"));
                    name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    name.setCellFactory(TextFieldTableCell.<Equipment>forTableColumn());
                    name.setOnEditCommit((TableColumn.CellEditEvent<Equipment, String> event) -> {
                        Equipment equipment = event.getTableView().getItems().get(event.getTablePosition().getRow());
                        equipment.setName(event.getNewValue());
                        Service update = equipmentService.updateEquipment(equipment);
                        update.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
                                alert.alertInformation("information","information","information.changename");
                            }
                        });
                        update.setOnFailed(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
                                alert.alertError("error","error","problem.changename");
                            }
                        });
                        update.start();
                    });
                    serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
                    serialNumber.setCellFactory(TextFieldTableCell.<Equipment>forTableColumn());
                    serialNumber.setOnEditCommit((TableColumn.CellEditEvent<Equipment, String> event) -> {
                        Equipment equipment = event.getTableView().getItems().get(event.getTablePosition().getRow());
                        equipment.setSerialNumber(event.getNewValue());
                        Service update = equipmentService.updateEquipment(equipment);
                        update.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
                                alert.alertInformation("information","information","information.changeserialnumber");
                            }
                        });
                        update.setOnFailed(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
                                alert.alertError("error","error","problem.changeserialnumber");
                            }
                        });
                        update.start();
                    });
                    if(tableViewEquipment.getItems().size()==0) buttonRemoveEquipment.setDisable(true);
                    else buttonRemoveEquipment.setDisable(false);
                }
            });
            listElement.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    alert.alertError("error","error","problem.getequipment");
                }
            });
            listElement.start();
        }
        else{
            ObservableList<Equipment> equipmentNote = FXCollections.observableList(new LinkedList<>());
            tableViewEquipment.setItems(equipmentNote);
            buttonRemoveEquipment.setDisable(true);
        }
    }

    private void refreshListLab(){
        list.getItems().removeAll(list.getItems());
        Service<List<Lab>> allLab = labService.allLab();
        allLab.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                int i=0;
                List<Lab> lab = allLab.getValue();
                for (Lab x : lab) {
                    list.getItems().add(Integer.toString(x.getNumberId()));
                    if (i == 0) list.setValue(Integer.toString(x.getNumberId()));
                    i++;
                }
                if(list.getItems().size()==0) {
                    buttonRemoveLab.setDisable(true);
                    buttonViewEquipment.setDisable(true);
                    buttonAddEquipment.setDisable(true);
                }
                else{
                    buttonRemoveLab.setDisable(false);
                    buttonViewEquipment.setDisable(false);
                    buttonAddEquipment.setDisable(false);
                }
                refreshViewEquipment();
            }
        });
        allLab.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                alert.alertError("error","error","problem.getlab");
            }
        });
        allLab.start();
    }

    @FXML
    public void addLab() {
        boolean ifNumber = true;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(bundle.getString("adding.laboratory"));
        dialog.setHeaderText(bundle.getString("adding.laboratory"));
        dialog.setContentText(bundle.getString("input.number"));
        Optional<String> numberLab = dialog.showAndWait();
        if (numberLab.isPresent()) {
            for (int i = 0; i < numberLab.get().length(); i++) {
                if (numberLab.get().charAt(i) > '9' || numberLab.get().charAt(i) < '0') {
                    ifNumber = false;
                }
            }
            if(numberLab.get().equals("")) ifNumber=false;
            if(ifNumber) {
                List<String> listItems = list.getItems();
                for(String x : listItems){
                    if(Integer.parseInt(x) == Integer.parseInt(numberLab.get())) ifNumber=false;
                }
                if (numberLab.get().length() == 0) ifNumber = false;
            }
            try {
                if (ifNumber) {
                    int numberL = Integer.parseInt(numberLab.get());
                    Lab lab = new Lab();
                    lab.setNumberId(numberL);
                    Service addLab = labService.addLab(lab);
                    addLab.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            refreshListLab();
                        }
                    });
                    addLab.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            alert.alertError("error", "error", "problem.addlab");
                        }
                    });
                    addLab.start();
                } else {
                    throw new IncorrectData("Błędne dane");
                }
            }
            catch (IncorrectData e){
                logger.error(e.getMessage(), e);
                alert.alertError("error", "error", "incorrect.data");
            }
        }
    }

    @FXML
    void removeLab(){
        if(!list.getItems().isEmpty()) {
            Service removeLab = labService.removeLabByNumber(Integer.parseInt(list.getValue()));
            Service removeEquipments = equipmentService.removeEquipmentsFromLab(Integer.parseInt(list.getValue()));
            removeEquipments.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    removeLab.start();
                }
            });
            removeLab.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    refreshListLab();
                }
            });
            removeEquipments.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    alert.alertError("error","error","problem.removeequipment");
                }
            });
            removeLab.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    alert.alertError("error","error","problem.removelab");
                }
            });
            removeEquipments.start();
        }
    }

    @FXML
    void viewAllEquipment(){
        refreshViewEquipment();
    }

    @FXML
    void removeEquipment(){
        if(tableViewEquipment.getSelectionModel().getSelectedItem()!=null) {
            System.out.println(tableViewEquipment.getSelectionModel().getSelectedItem().getId());
            Service removeEquipment = equipmentService.removeEquipment(tableViewEquipment.getSelectionModel().getSelectedItem().getId());
            removeEquipment.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    refreshViewEquipment();
                }
            });
            removeEquipment.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    alert.alertError("error","error","problem.removeequipment");
                }
            });
            removeEquipment.start();
        }
    }

    @FXML
    public void refresh(){
        refreshListLab();
    }

    @FXML
    public void addEquipment(){
        try {
            if (nameEquipment.getText().length() > 0 && serialNumberEquipment.getText().length() > 0 && !list.getItems().isEmpty()) {
                Service addEquipment = equipmentService.addEquipment(Integer.parseInt(list.getValue()), nameEquipment.getText(), serialNumberEquipment.getText());
                addEquipment.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        refreshViewEquipment();
                    }
                });
                addEquipment.setOnFailed(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        alert.alertError("error", "error", "problem.addequipment");
                    }
                });
                addEquipment.start();
            } else {
                if(!list.getItems().isEmpty()) throw new EmptyTextData("Puste pola z danymi");
                else alert.alertError("error", "error", "no.lab");
            }
        }
        catch(EmptyTextData e){
            logger.error(e.getMessage(),e);
            alert.alertError("error", "error", "no.data");
        }
    }

    @FXML
    public void initialize(){
        tableViewEquipment.setEditable(true);
        refreshListLab();
    }
}
