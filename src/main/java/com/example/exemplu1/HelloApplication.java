package com.example.exemplu1;

import com.example.exemplu1.Domain.*;
import com.example.exemplu1.Repository.*;
import com.example.exemplu1.Service.Service;
import com.example.exemplu1.UI.UI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    TextField idPacientTextField = new TextField();
    TextField firstNameTextField = new TextField();
    TextField lastNameTextField = new TextField();
    TextField ageTextField = new TextField();

    TextField idProgramareTextField = new TextField();
    TextField dataTextField = new TextField();
    TextField oraTextField = new TextField();
    TextField scopTextField = new TextField();
    TextField idPacientProgramariTextField = new TextField();
    Service<Programare> programareService;
    Service<Pacient> pacientService;
    //ListView<String> raport1ListView = new ListView<>();

    UI ui;
    TextArea reportTextArea = new TextArea();


    public HelloApplication() {

    }

    @Override
    public void start(Stage stage) throws IOException {

        BorderPane startPane = new BorderPane();
        Button startButton = new Button("Start");
        startPane.setCenter(startButton);

        Scene startScene = new Scene(startPane, 300, 200);

        startButton.setOnAction(e -> {
            try {
                initializeServices();
                displayApplication(stage);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        stage.setTitle("Hello!");
        stage.setScene(startScene);
        stage.show();

    }


    private void displayApplication(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        ToolBar toolBar = new ToolBar();
        Button pacientiButton = new Button("Pacienti");
        Button programariButton = new Button("Programari");
        Button raport1Button = new Button("Raport 1");
        Button raport2Button = new Button("Raport 2");
        Button raport3Button = new Button("Raport 3");
        Button raport4Button = new Button("Raport 4");
        toolBar.getItems().addAll(pacientiButton, programariButton, raport1Button, raport2Button, raport3Button, raport4Button);

        root.setTop(toolBar);

        StackPane contentPane = new StackPane();
        root.setCenter(contentPane);

        VBox pacientiVerticalBox = createPacientiScene();
        VBox programariVerticalBox = createProgramariScene();
        VBox raport1 = numarulProgramariPerPacient();
        VBox raport2 = numarulProgramariPeLuna();
        VBox raport3 = numarulZileDeLaUltimaProgramare();
        VBox raport4 = celeMaiAgloemrateLuni();

        pacientiVerticalBox.setVisible(true);
        programariVerticalBox.setVisible(false);
        raport1.setVisible(false);
        raport2.setVisible(false);
        raport3.setVisible(false);
        raport4.setVisible(false);

        contentPane.getChildren().addAll(pacientiVerticalBox, programariVerticalBox, raport1, raport2, raport3, raport4);

        pacientiButton.setOnAction(e -> {
            pacientiVerticalBox.setVisible(true);
            programariVerticalBox.setVisible(false);
            raport1.setVisible(false);
            raport2.setVisible(false);
            raport3.setVisible(false);
            raport4.setVisible(false);
        });
        programariButton.setOnAction(e -> {
            pacientiVerticalBox.setVisible(false);
            programariVerticalBox.setVisible(true);
            raport1.setVisible(false);
            raport2.setVisible(false);
            raport3.setVisible(false);
            raport4.setVisible(false);
        });
        raport1Button.setOnAction(e -> {
            pacientiVerticalBox.setVisible(false);
            programariVerticalBox.setVisible(false);
            raport1.setVisible(true);
            raport2.setVisible(false);
            raport3.setVisible(false);
            raport4.setVisible(false);
        });
        raport2Button.setOnAction(e -> {
            pacientiVerticalBox.setVisible(false);
            programariVerticalBox.setVisible(false);
            raport1.setVisible(false);
            raport2.setVisible(true);
            raport3.setVisible(false);
            raport4.setVisible(false);
        });
        raport3Button.setOnAction(e -> {
            pacientiVerticalBox.setVisible(false);
            programariVerticalBox.setVisible(false);
            raport1.setVisible(false);
            raport2.setVisible(false);
            raport3.setVisible(true);
            raport4.setVisible(false);
        });
        raport4Button.setOnAction(e -> {
            pacientiVerticalBox.setVisible(false);
            programariVerticalBox.setVisible(false);
            raport1.setVisible(false);
            raport2.setVisible(false);
            raport3.setVisible(false);
            raport4.setVisible(true);
        });
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private VBox celeMaiAgloemrateLuni(){
        VBox reportContent = new VBox();
        reportContent.setSpacing(10);

        ListView<String> raportListView = ui.celeMainAglomerateLuni();
        reportContent.getChildren().add(raportListView);

        return reportContent;
    }


    private VBox numarulZileDeLaUltimaProgramare() {
        VBox reportContent = new VBox();
        reportContent.setSpacing(10);

       ListView<String> raportListView = ui.nrZileUltimaProgramare();
       reportContent.getChildren().add(raportListView);

        return reportContent;
    }

    private VBox numarulProgramariPerPacient() {
        VBox reportContent = new VBox();
        reportContent.setSpacing(10);
        ListView<String> raport1ListView = ui.nrProgramariPerPacient();
        reportContent.getChildren().add(raport1ListView);

        return reportContent;
    }

    private VBox numarulProgramariPeLuna() {
        VBox reportContent = new VBox();
        reportContent.setSpacing(10);
        ListView<String> raportListView = ui.celeMainAglomerateLuni();
        reportContent.getChildren().add(raportListView);

        return reportContent;

    }

    private void initializeServices() {
        PacientRepositorySQL pacientRepositorySQL = new PacientRepositorySQL();
        pacientRepositorySQL.openConnection();
        ProgramareRepositorySQL programareIRepositorySQL = new ProgramareRepositorySQL();
        programareIRepositorySQL.openConnection();

        pacientService = new Service<>(pacientRepositorySQL);
        programareService = new Service<>(programareIRepositorySQL);

        ui = new UI(programareService, pacientService);
        if (ui.getNrProgramari() == 0) {
        try {
            ui.addRandomProgramari();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        }


    }

    private VBox createPacientiScene() {
        VBox mainVerticalBoxPacienti = new VBox();
        mainVerticalBoxPacienti.setPadding(new Insets(10));
        ObservableList<Pacient> pacienti = FXCollections.observableArrayList(pacientService.getAll());
        ListView<Pacient> pacientListView = new ListView<Pacient>(pacienti);
        mainVerticalBoxPacienti.getChildren().add(pacientListView);
        pacientListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Pacient pacient = pacientListView.getSelectionModel().getSelectedItem();
                idPacientTextField.setText(Integer.toString(pacient.getID()));
                firstNameTextField.setText(pacient.getFirst_name());
                lastNameTextField.setText(pacient.getSecond_name());
                ageTextField.setText(Integer.toString(pacient.getAge()));
            }
        });

        GridPane pacientGridPane = new GridPane();
        Label idLabel = new Label();
        idLabel.setText("Id: ");
        idLabel.setPadding(new Insets(10, 0, 10, 0));
        Label firstNameLabel = new Label();
        firstNameLabel.setText("First Name");
        Label lastNameLabel = new Label();
        lastNameLabel.setText("Last Name");
        Label ageLabel = new Label();
        ageLabel.setText("Age");

        pacientGridPane.add(idLabel, 0, 0);
        pacientGridPane.add(idPacientTextField, 1, 0);
        pacientGridPane.add(firstNameLabel, 0, 1);
        pacientGridPane.add(firstNameTextField, 1, 1);
        pacientGridPane.add(lastNameLabel, 0, 2);
        pacientGridPane.add(lastNameTextField, 1, 2);
        pacientGridPane.add(ageLabel, 0, 3);
        pacientGridPane.add(ageTextField, 1, 3);

        pacientGridPane.setHgap(3);
        pacientGridPane.setVgap(5);

        mainVerticalBoxPacienti.getChildren().add(pacientGridPane);

        HBox actionButtondHorizontalBox = new HBox();
        mainVerticalBoxPacienti.getChildren().add(actionButtondHorizontalBox);
        Button addPacientButton = new Button("Add");
        addPacientButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {int id = Integer.parseInt(idPacientTextField.getText());
                    String firstName = firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    int age = Integer.parseInt(ageTextField.getText());
                    Pacient pacient = new Pacient(id, firstName, lastName, age);
                    pacientService.add(pacient);
                    pacienti.setAll(pacientService.getAll());
                } catch (RepositoryException e) {
                    throw new RuntimeException(e);
                } catch (Exception e){
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        actionButtondHorizontalBox.getChildren().add(addPacientButton);

        Button updatePacientButton = new Button("Update");
        updatePacientButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    int id = Integer.parseInt(idPacientTextField.getText());
                    String firstName = firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    int age = Integer.parseInt(ageTextField.getText());
                    Pacient newPacient = new Pacient(id, firstName, lastName, age);
                    //pacientService.update(newPacient);
                    ui.updatePacintGUI(newPacient);
                    pacienti.setAll(pacientService.getAll());
                }catch (IllegalArgumentException e){
                        throw new RuntimeException(e);
                } catch (RuntimeException e){
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        actionButtondHorizontalBox.getChildren().add(updatePacientButton);

        Button deletePacientButton = new Button("Delete");
        deletePacientButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    int id = Integer.parseInt(idPacientTextField.getText());
                    String firstName = firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    int age = Integer.parseInt(ageTextField.getText());
                    Pacient newPacient = new Pacient(id, firstName, lastName, age);
                    pacientService.remove(newPacient.getID());
                    pacienti.setAll(pacientService.getAll());
                } catch (Exception e){
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });

        actionButtondHorizontalBox.getChildren().add(deletePacientButton);
        return mainVerticalBoxPacienti;
    }

    private VBox createProgramariScene() {
        VBox mainVerticalBoxProgramari = new VBox();
        mainVerticalBoxProgramari.setPadding(new Insets(10));
        ObservableList<Programare> programari = FXCollections.observableArrayList(programareService.getAll());
        ListView<Programare> programariListView = new ListView<>(programari);
        mainVerticalBoxProgramari.getChildren().add(programariListView);
        programariListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Programare programare = programariListView.getSelectionModel().getSelectedItem();
                idProgramareTextField.setText(Integer.toString(programare.getID()));
                idPacientProgramariTextField.setText(Integer.toString(programare.getPacient()));
                dataTextField.setText(programare.getData());
                oraTextField.setText(programare.getOra());
                scopTextField.setText(programare.getScop());
            }
        });

        GridPane programariGridPane = new GridPane();
        Label idLabel = new Label("Id: ");
        Label pacientLabel = new Label("Pacient");
        Label dataLabel = new Label("Data");
        Label oraLabel = new Label("Ora");
        Label scopLabel = new Label("Scop");

        programariGridPane.add(idLabel, 0, 0);
        programariGridPane.add(idProgramareTextField, 1, 0);
        programariGridPane.add(pacientLabel, 0, 1);
        programariGridPane.add(idPacientProgramariTextField, 1, 1);
        programariGridPane.add(dataLabel, 0, 2);
        programariGridPane.add(dataTextField, 1, 2);
        programariGridPane.add(oraLabel, 0, 3);
        programariGridPane.add(oraTextField, 1, 3);
        programariGridPane.add(scopLabel, 0, 4);
        programariGridPane.add(scopTextField, 1, 4);


        programariGridPane.setHgap(3);
        programariGridPane.setVgap(5);
        mainVerticalBoxProgramari.getChildren().add(programariGridPane);

        HBox actionProgramariButtondHorizontalBox = new HBox();
        mainVerticalBoxProgramari.getChildren().add(actionProgramariButtondHorizontalBox);

        Button addProgramareButton = new Button("Add");
        addProgramareButton.setOnMouseClicked(event -> {
            try {
                int id = Integer.parseInt(idProgramareTextField.getText());
                int pacientId = Integer.parseInt(idPacientProgramariTextField.getText());
                String data = dataTextField.getText();
                String ora = oraTextField.getText();
                String scop = scopTextField.getText();

                Programare programare = new Programare(id, pacientId, data, ora, scop);
                ///programareService.add(programare);
                ui.addProgramareScene(programare);
                programari.setAll(programareService.getAll());
            } catch (Exception e) {
                Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                errorPopUp.setTitle("Error");
                errorPopUp.setContentText(e.getMessage());
                errorPopUp.show();
            }
        });
        actionProgramariButtondHorizontalBox.getChildren().add(addProgramareButton);

        Button updateProgramariButton = new Button("Update");
        updateProgramariButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                int id = Integer.parseInt(idProgramareTextField.getText());
                int pacientInd = Integer.parseInt(idPacientProgramariTextField.getText());
                String data = dataTextField.getText();
                String ora = oraTextField.getText();
                String scop = scopTextField.getText();
                Programare programareNoua = new Programare(id, pacientInd, data, ora, scop);
                ui.updateProgramareGUI(programareNoua);
                //programareService.update(programareNoua);
                programari.setAll(programareService.getAll());
                } catch (RuntimeException e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        actionProgramariButtondHorizontalBox.getChildren().add(updateProgramariButton);

        Button deleteProgramareButton = new Button("Delete");
        deleteProgramareButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                int id = Integer.parseInt(idProgramareTextField.getText());
                int pacientInd = Integer.parseInt(idPacientProgramariTextField.getText());
                String data = dataTextField.getText();
                String ora = oraTextField.getText();
                String scop = scopTextField.getText();
                Programare programareNoua = new Programare(id, pacientInd, data, ora, scop);
                programareService.remove(programareNoua.getID());
                programari.setAll(programareService.getAll());
                } catch (Exception e) {
                    Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                    errorPopUp.setTitle("Error");
                    errorPopUp.setContentText(e.getMessage());
                    errorPopUp.show();
                }
            }
        });
        actionProgramariButtondHorizontalBox.getChildren().add(deleteProgramareButton);
        return mainVerticalBoxProgramari;
    }

    public static void main(String[] args) {
        launch();

    }
}