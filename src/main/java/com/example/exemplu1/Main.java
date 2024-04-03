package com.example.exemplu1;

import com.example.exemplu1.Domain.Pacient;
import com.example.exemplu1.Domain.PacientConverter;
import com.example.exemplu1.Domain.Programare;
import com.example.exemplu1.Domain.ProgramareConverter;
import com.example.exemplu1.Repository.*;
import com.example.exemplu1.Service.Service;
import com.example.exemplu1.UI.UI;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main {
public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
    if (args[0].equals("console")){
        runConsole();
    } else if (args[0].equals("gui")) {
        runGUI();
    }
}

    private static void runGUI() {
        Application.launch(HelloApplication.class);
    }

    private static void runConsole() throws FileNotFoundException, ClassNotFoundException {
        ProgramareConverter programareConverter = new ProgramareConverter();
        PacientConverter pacientConverter = new PacientConverter();

        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(("/Users/anastasiacutulima/Desktop/a1-anastasia402/stomatologieCorect/src/settings.properties"))) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }


    String repoType = properties.getProperty("Repository");
    String fisierPacienti = properties.getProperty("Pacienti");
    String fisierProgramari = properties.getProperty("Programari");

    Service<Pacient> pacientService;
    Service<Programare> programareService;


    if ("db".equals(repoType)) {
        PacientRepositorySQL pacientRepositorySQL = new PacientRepositorySQL();
        ProgramareRepositorySQL programareRepositorySQL = new ProgramareRepositorySQL();

        pacientRepositorySQL.openConnection();
        programareRepositorySQL.openConnection();

        pacientService = new Service<>(pacientRepositorySQL);
        programareService = new Service<>(programareRepositorySQL);

        UI ui = new UI(programareService, pacientService);

        ui.start();
    } else {
        Repository<Pacient> pacientRepository;
        Repository<Programare> programareRepository;

        if ("binary".equals(repoType)) {
            pacientRepository = new BinaryFileRepository<>(fisierPacienti);
            programareRepository = new BinaryFileRepository<>(fisierProgramari);
        } else if ("text".equals(repoType)) {
            pacientRepository = new TextFileRepository<>(fisierPacienti, pacientConverter);
            programareRepository = new TextFileRepository<>(fisierProgramari, programareConverter);
        } else {
            throw new IllegalArgumentException("Invalid repository type");
        }

        pacientService = new Service<>(pacientRepository);
        programareService = new Service<>(programareRepository);

        UI ui = new UI(programareService, pacientService);
        ui.start();
    }
    }

}
