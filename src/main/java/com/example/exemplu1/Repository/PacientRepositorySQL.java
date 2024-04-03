package com.example.exemplu1.Repository;

import com.example.exemplu1.Domain.*;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.Iterator;

public class PacientRepositorySQL extends Repository<Pacient>{
    private static final String JDBC_URL = "jdbc:sqlite:pacienti.db";

    private Connection conn = null;

    public PacientRepositorySQL() {
        openConnection();
        createSchema();
        getAllPacienti();
    }

    public void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createSchema() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS Pacient(ID int PRIMARY KEY, FirstName varchar(50), LastName varchar(50),Age int )");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    public void add(Pacient pacient) {
        try {
            try (PreparedStatement preparedStatement = conn.prepareStatement("insert into Pacient values (?, ?, ?, ?)")) {

                preparedStatement.setInt(1, pacient.getID());
                preparedStatement.setString(2, pacient.getFirst_name());
                preparedStatement.setString(3, pacient.getSecond_name());
                preparedStatement.setInt(4, pacient.getAge());

                super.add(pacient);

                preparedStatement.executeUpdate();
            } catch (DuplicateObjectException e) {
                throw new RuntimeException(e);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {
        try {
            try (PreparedStatement preparedStatement = conn.prepareStatement("delete from Pacient where id = "+id)){
                preparedStatement.executeUpdate();
                super.remove(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Pacient pacient) {
        try {
            String updateSql = "update Pacient set FirstName = ?, LastName = ? , Age = ? where ID = "+pacient.getID();
            try(PreparedStatement preparedStatement = conn.prepareStatement(updateSql)){
                preparedStatement.setString(1, pacient.getFirst_name());
                preparedStatement.setString(2, pacient.getSecond_name());
                preparedStatement.setInt(3, pacient.getAge());

                super.update(pacient);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected>0){
                    System.out.println("Pacient updates successfully");
                } else
                    System.out.println("Pacient with ID: "+pacient.getID()+" not found");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        } catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void closeConnection() throws IOException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public Iterator<Pacient> iterator() {
        return super.iterator();
    }

    public void getAllPacienti(){
        entities.clear();
        try{
            try(PreparedStatement preparedStatement = conn.prepareStatement("Select * from Pacient")) {
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()){
                    Pacient pacient = new Pacient(rs.getInt("ID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getInt("Age"));
                    entities.add(pacient);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
