package com.example.exemplu1;

import com.example.exemplu1.Domain.Pacient;
import com.example.exemplu1.Domain.Programare;
import com.example.exemplu1.Repository.IRepository;
import com.example.exemplu1.Repository.PacientRepositorySQL;
import com.example.exemplu1.Repository.ProgramareRepositorySQL;
import com.example.exemplu1.Repository.RepositoryException;
import com.example.exemplu1.Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}