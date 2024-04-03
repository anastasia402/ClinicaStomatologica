package com.example.exemplu1.UI;


import com.example.exemplu1.Domain.*;
import com.example.exemplu1.Repository.*;
import com.example.exemplu1.Service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UI {
    private Service<Programare> serviceProgramare;
    private Service<Pacient> servicePacient;

    public UI(Service<Programare> serviceProgramare, Service<Pacient> servicePacient) {
        this.serviceProgramare = serviceProgramare;
        this.servicePacient = servicePacient;
    }


    public void menu() {
        System.out.println("Menu: ");
        System.out.println("1. Add a new appointment");
        System.out.println("2. Add a new patient");
        System.out.println("3. Update an appointment");
        System.out.println("4. Update a patient");
        System.out.println("5. Delete an appointment");
        System.out.println("6. Delete a patient");
        System.out.println("7. Show all appointments");
        System.out.println("8. Show all patients");
        System.out.println("9. Add firts patients and appointments");
        System.out.println("10. Raport 1: Numărul de programări pentru fiecare pacient în parte");
        System.out.println("11. Raport 2: Numărul total de programări pentru fiecare lună a anulu");
        System.out.println("12. Raport 3: Numărul de zile trecute de la ultima programare a fiecărui pacient");
        System.out.println("13. Raport 4: Cele mai aglomerate luni ale anului");
        System.out.println("0. Add 100 instances");
    }

    public void addPacient() throws RepositoryException {
        Scanner newPacient = new Scanner(System.in);
        int idP = newPacient.nextInt();
        newPacient.nextLine();
        String FirstName = newPacient.nextLine();
        String LastName = newPacient.nextLine();
        int age = newPacient.nextInt();
        Pacient pacient = new Pacient(idP, FirstName, LastName, age);
        try {
            this.servicePacient.add(pacient);
        } catch (DuplicateObjectException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void addProgramare() throws Exception {
        System.out.println("Enter patient's id: ");
        Scanner newPacient = new Scanner(System.in);
        int idP = newPacient.nextInt();
        newPacient.nextLine();

        System.out.println("Now make an appointment: Id, Date, Hour, Purpose");

        Scanner newProgramare = new Scanner(System.in);
        int idprogrm = newProgramare.nextInt();
        newProgramare.nextLine();
        String data = newProgramare.nextLine();
        String ora = newProgramare.nextLine();
        String scop = newProgramare.nextLine();

        Programare programare = new Programare(idprogrm, idP, data, ora, scop);
        if (verificaOra(programare)) {
            try {
                this.serviceProgramare.add(programare);
            } catch (DuplicateObjectException e) {

            }catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addProgramareScene(Programare programare) throws Exception {
        if (verificaOra(programare)) {
            try {
                this.serviceProgramare.add(programare);
            } catch (DuplicateObjectException e) {

            }catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private String generateRandomDate() {
        Random random = new Random();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, random.nextInt(21));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(calendar.getTime());
    }

    public List<Pacient> generateRandomUniquePacients(int n) {
        List<Pacient> pacients = new ArrayList<>();
        Set<String> generatedIds = new HashSet<>();
        Random rand = new Random();
        String[] FIRST_NAMES = {"John", "Jane", "Michael", "Emily", "Christopher", "Emma", "William", "Olivia", "Yuki", "Mohammed", "Sofia", "Chen", "Maria", "Ahmed"};
        String[] SECOND_NAMES = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Tanaka", "Lee", "Kim", "Singh", "García", "Nguyen"};

        for (int i = 1; i <= n; i++) {
            try {
                int pacientId;
                do {
                    pacientId = i;
                } while (!generatedIds.add(Integer.toString(pacientId)));
                String nume = FIRST_NAMES[rand.nextInt(FIRST_NAMES.length)];
                String prenume = SECOND_NAMES[rand.nextInt(SECOND_NAMES.length)];
                int age = rand.nextInt(80) + 18;
                Pacient pacient = new Pacient(pacientId, nume, prenume, age);
                this.servicePacient.add(pacient);
                pacients.add(pacient);
            } catch (Exception e) {
                System.out.println("Exception during Pacient generation: " + e.getMessage());

            }
        }

        return pacients;
    }

    private int generateRandomId() {
        Random random = new Random();
        return random.nextInt(100) + 1;

    }

    private String generateRandomHour() {
        Random rand = new Random();
        int hour = rand.nextInt(10) + 8;
        return String.format("%02d:00", hour);
    }
    private String generateRandomPurpose() {
        String[] purposes = {"Checkup", "Consultation", "Treatment", "Follow-up", "Vaccination", "Other"};
        Random rand = new Random();
        return purposes[rand.nextInt(purposes.length)];
    }
    private int generateRandomIdProgramare() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }

    public void addRandomProgramari() {
        List<Pacient> pacients = generateRandomUniquePacients(70);
        Random rand = new Random();
        int i = 0;
        while(i < 100) {
            try {
                Pacient selectedPacient = pacients.get(rand.nextInt(pacients.size()));
                int id = generateRandomIdProgramare();
                String data = generateRandomDate();
                String ora = generateRandomHour();
                String scop = generateRandomPurpose();
                Programare programare = new Programare(id, selectedPacient.getID(), data, ora, scop);
                if (!existaProgramare(programare)){
                    this.serviceProgramare.add(programare);
                    i++;
            } }catch (IllegalArgumentException e) {
            } catch (DuplicateObjectException e) {
                System.out.println("DuplicateObjectException: " + e.getMessage());
            } catch (Exception e) {
                //System.out.println("Exception: " + e.getMessage());
            }
        }
        }

private boolean existaProgramare(Programare programare){
        for (Programare p: this.serviceProgramare.getAll()){
            if (p.getData().equals(programare.getData()) && !verificaOra(programare)) {
                return true;
            }
        }
        return false;
    }


    public boolean verificaOra(Programare programare) {
        for (Programare p : this.serviceProgramare.getAll()) {
            if (p.getData().equals(programare.getData())) {
                LocalTime existingStartTime = LocalTime.parse(p.getOra());
                LocalTime existingEndTime = existingStartTime.plusMinutes(60); // Assuming appointments are 1 hour long

                LocalTime newStartTime = LocalTime.parse(programare.getOra());
                LocalTime newEndTime = newStartTime.plusMinutes(60);

                boolean overlaps = (newStartTime.isBefore(existingEndTime) && newEndTime.isAfter(existingStartTime));
                if (overlaps) {
                    return false;
                }
            }
        }
        return true;
    }


    public void updatePacient(){
        Scanner newPacient = new Scanner(System.in);
        int idP = newPacient.nextInt();
        newPacient.nextLine();
        String FirstName = newPacient.nextLine();
        String LastName = newPacient.nextLine();
        int age = newPacient.nextInt();
        Pacient pacient = new Pacient(idP, FirstName, LastName, age);
        try {
            this.servicePacient.update(pacient);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void updateProgramare(){
        System.out.println("Enter patient's id: ");
        Scanner newPacient = new Scanner(System.in);
        int idP = newPacient.nextInt();
        newPacient.nextLine();

        System.out.println("Now make an appointment: Id, Date, Hour, Purpose");

        Scanner newProgramare = new Scanner(System.in);
        int idprogrm = newProgramare.nextInt();
        newProgramare.nextLine();
        String data = newProgramare.nextLine();
        String ora = newProgramare.nextLine();
        String scop = newProgramare.nextLine();

        Programare programare = new Programare(idprogrm, idP, data, ora, scop);
        try {
            this.serviceProgramare.update(programare);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    public void updateProgramareGUI(Programare programarenoua){
        int cnt = 0;
        for (Pacient p: this.servicePacient.getAll()){
            if(p.getID() == programarenoua.getPacient()){
                cnt++;
                serviceProgramare.update(programarenoua);
            }
        }
        if (cnt==0){
            throw new RuntimeException("Nu exista entitate cu acest id");
        }
    }

    public void updatePacintGUI(Pacient pacientnou){
        int cnt = 0;
        for (Pacient p: this.servicePacient.getAll()){
            if(p.getID() == pacientnou.getID()){
                cnt++;
                servicePacient.update(pacientnou);
            }
        }
        if (cnt==0){
            throw new RuntimeException("Nu exista entitate cu acest id");
        }
    }

    public void deleteAppointment(){
        Scanner scanner = new Scanner(System.in);
        int idPr = scanner.nextInt();
        this.serviceProgramare.remove(idPr);
    }

    public void deletePatient(){
        Scanner scanner = new Scanner(System.in);
        int idP = scanner.nextInt();
        this.servicePacient.remove(idP);
        int idProgramareDeSters = 0;
        int cnt = 0;
        for (Programare p: this.serviceProgramare.getAll()){
            if (p.getPacient() == idP) {
                cnt++;
                idProgramareDeSters = p.getID();
                this.serviceProgramare.remove(idProgramareDeSters);
            }
        }
    }

    public void getAllProgramari(){
        for (Programare p:this.serviceProgramare.getAll()){
            System.out.println(p);
            System.out.println();
        }
    }

    public void getAllPacienti(){
        for (Pacient p: this.servicePacient.getAll()){
            System.out.println(p);
            System.out.println();
        }
    }

    public void addFirts() throws Exception {
        Pacient patient4 = new Pacient(4, "George", "Muntean", 22);
        Pacient patient5 = new Pacient(5, "Oana", "Frunze", 21);

        Programare programare4 = new Programare(4, patient5.getID(), "12-09-2023", "15:00", "Consultatie");
        Programare programare5 = new Programare(5, patient4.getID(), "12-09-2023", "16:00", "Consultatie");

        try {
            this.servicePacient.add(patient4);
            this.servicePacient.add(patient5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.serviceProgramare.add(programare4);
        this.serviceProgramare.add(programare5);
    }

    private String getMonth(String date) {
        String[] parts = date.split("-");
        return parts[1];
    }

    public ListView<String> celeMainAglomerateLuni(){
        Map<String, Long> reportData = serviceProgramare.getAll().stream()
                .collect(Collectors.groupingBy(
                        programare -> getMonth(programare.getData()),
                        Collectors.counting()
                ));

        // Sortăm rezultatele în ordine descrescătoare a numărului de programări
        List<Map.Entry<String, Long>> sortedReportData = reportData.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        ObservableList<String> observableList_PacientiProgramari = FXCollections.observableArrayList();

        for (Map.Entry<String, Long> entry : sortedReportData) {
            String month = entry.getKey();
            Long nr = entry.getValue();
            observableList_PacientiProgramari.add(month + ": " + nr + " programări");
        }

        ListView<String> raportListView = new ListView<>();
        raportListView.setItems(observableList_PacientiProgramari);
        return raportListView;
    }

    public ListView<String> nrProgramariPerPacient(){
        Map<Pacient, Long> reportData = serviceProgramare.getAll().stream()
                .collect(Collectors.groupingBy(
                        programare -> servicePacient.find(programare.getPacient()),
                        Collectors.counting()
                ));

        // Sortăm rezultatele în ordine descrescătoare a numărului de programări
        List<Map.Entry<Pacient, Long>> sortedReportData = reportData.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        ObservableList<String> observabled_PacientiProgramari = FXCollections.observableArrayList();

        for (Map.Entry<Pacient, Long> entry : sortedReportData) {
            Pacient tokens = entry.getKey();
            Long nr = entry.getValue();
            observabled_PacientiProgramari.add(tokens+" "+nr);

        }
        ListView<String> raportListView = new ListView<>();
        raportListView.setItems(observabled_PacientiProgramari);
        return raportListView;
    }

    private int calculateDaysSinceLastAppointment(Pacient pacient) {
        List<Programare> programari = serviceProgramare.getAll().stream()
                .filter(programare -> programare.getPacient() == pacient.getID())
                .sorted(Comparator.comparing(Programare::getData).reversed())
                .collect(Collectors.toList());

        if (!programari.isEmpty()) {
            LocalDate currentDate = LocalDate.parse("2024-02-01");
            LocalDate lastAppointmentDate = LocalDate.parse(programari.get(0).getData());
            return (int) ChronoUnit.DAYS.between(lastAppointmentDate, currentDate);
        } else {
            return -1;
        }
    }

    private String getLastAppointmentDate(Pacient pacient) {
        List<Programare> programari = serviceProgramare.getAll().stream()
                .filter(programare -> programare.getPacient() == pacient.getID())
                .sorted(Comparator.comparing(Programare::getData).reversed())
                .collect(Collectors.toList());

        if (programari.isEmpty()) {
            return "";
        } else {
            return programari.get(0).getData();
        }
    }

    public ListView<String> nrZileUltimaProgramare(){
        Map<Pacient, Integer> reportData = servicePacient.getAll().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        pacient -> calculateDaysSinceLastAppointment(pacient),
                        (existing, replacement) -> existing
                ));

        // Sortăm rezultatele în ordine descrescătoare a numărului de zile trecute
        List<Map.Entry<Pacient, Integer>> sortedReportData = reportData.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        ObservableList<String> observableList_ZileTrecute = FXCollections.observableArrayList();

        for (Map.Entry<Pacient, Integer> entry : sortedReportData) {
            Pacient pacient = entry.getKey();
            int daysSinceLastAppointment = entry.getValue();
            observableList_ZileTrecute.add(String.format("%s %s - Ultima programare: %s, Zile trecute: %d",
                    pacient.getFirst_name(), pacient.getSecond_name(),
                    getLastAppointmentDate(pacient), daysSinceLastAppointment));
        }

        ListView<String> raportListView = new ListView<>();
        raportListView.setItems(observableList_ZileTrecute);
        return raportListView;

    }

    public void raport4(){
        Map<String, Long> reportData = serviceProgramare.getAll().stream()
                .collect(Collectors.groupingBy(
                        programare -> getMonth(programare.getData()),
                        Collectors.counting()
                ));

        // Sortăm rezultatele în ordine descrescătoare a numărului de programări
        List<Map.Entry<String, Long>> sortedReportData = reportData.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        ObservableList<String> observableList_PacientiProgramari = FXCollections.observableArrayList();

        for (Map.Entry<String, Long> entry : sortedReportData) {
            String month = entry.getKey();
            Long nr = entry.getValue();
            observableList_PacientiProgramari.add(month + ": " + nr + " programări");
        }

        for (String item: observableList_PacientiProgramari){
            System.out.println(item);
        }
    }

    public void raport3(){
        Map<Pacient, Integer> reportData = servicePacient.getAll().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        pacient -> calculateDaysSinceLastAppointment(pacient),
                        (existing, replacement) -> existing
                ));

        // Sortăm rezultatele în ordine descrescătoare a numărului de zile trecute
        List<Map.Entry<Pacient, Integer>> sortedReportData = reportData.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        ObservableList<String> observableList_ZileTrecute = FXCollections.observableArrayList();

        for (Map.Entry<Pacient, Integer> entry : sortedReportData) {
            Pacient pacient = entry.getKey();
            int daysSinceLastAppointment = entry.getValue();
            observableList_ZileTrecute.add(String.format("%s %s - Ultima programare: %s, Zile trecute: %d",
                    pacient.getFirst_name(), pacient.getSecond_name(),
                    getLastAppointmentDate(pacient), daysSinceLastAppointment));
        }

        for (String item : observableList_ZileTrecute) {
            System.out.println(item);
        }
    }

    public void raport1(){
        Map<Pacient, Long> reportData = serviceProgramare.getAll().stream()
                .collect(Collectors.groupingBy(
                        programare -> servicePacient.find(programare.getPacient()),
                        Collectors.counting()
                ));

        // Sortăm rezultatele în ordine descrescătoare a numărului de programări
        List<Map.Entry<Pacient, Long>> sortedReportData = reportData.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        ObservableList<String> observabled_PacientiProgramari = FXCollections.observableArrayList();

        for (Map.Entry<Pacient, Long> entry : sortedReportData) {
            Pacient tokens = entry.getKey();
            Long nr = entry.getValue();
            observabled_PacientiProgramari.add(tokens+" "+nr);
        }

        for (String item: observabled_PacientiProgramari){
            System.out.println(item);
        }
    }

    public void raport2(){
        Map<Pacient, Integer> reportData = servicePacient.getAll().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        pacient -> calculateDaysSinceLastAppointment(pacient),
                        (existing, replacement) -> existing
                ));

        // Sortăm rezultatele în ordine descrescătoare a numărului de zile trecute
        List<Map.Entry<Pacient, Integer>> sortedReportData = reportData.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        ObservableList<String> observableList_ZileTrecute = FXCollections.observableArrayList();

        for (Map.Entry<Pacient, Integer> entry : sortedReportData) {
            Pacient pacient = entry.getKey();
            int daysSinceLastAppointment = entry.getValue();
            observableList_ZileTrecute.add(String.format("%s %s - Ultima programare: %s, Zile trecute: %d",
                    pacient.getFirst_name(), pacient.getSecond_name(),
                    getLastAppointmentDate(pacient), daysSinceLastAppointment));
        }

        for (String item : observableList_ZileTrecute) {
            System.out.println(item);
        }
    }



    public int getNrProgramari(){
        return this.serviceProgramare.getAll().size();
    }


    public void start(){
        this.menu();
        int cmd = 0;
        try{
            while (true){
                this.menu();
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter your command: ");
                cmd = sc.nextInt();
                if (cmd == 1)
                    this.addProgramare();
                else if (cmd==2) {
                    this.addPacient();
                } else if (cmd==3) {
                    this.updateProgramare();
                } else if (cmd==4) {
                    this.updatePacient();
                } else if (cmd==5) {
                    this.deleteAppointment();
                } else if (cmd==6) {
                    this.deletePatient();
                } else if (cmd==7) {
                    this.getAllProgramari();
                } else if (cmd==8) {
                    this.getAllPacienti();
                } else if (cmd==9) {
                    this.addFirts();
                } else if (cmd==0) {
                    this.addRandomProgramari();
                } else if (cmd == 10) {
                    this.raport1();
                } else if (cmd==11) {
                    this.raport2();
                } else if (cmd==12) {
                    this.raport3();
                } else if (cmd==13) {
                    this.raport4();
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
