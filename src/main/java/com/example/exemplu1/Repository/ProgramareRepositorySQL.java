package com.example.exemplu1.Repository;

import com.example.exemplu1.Domain.Pacient;
import com.example.exemplu1.Domain.Programare;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.*;

public class ProgramareRepositorySQL extends Repository<Programare>{
    private static final String JDBC_URL = "jdbc:sqlite:pacienti.db";

    private Connection conn = null;

    public ProgramareRepositorySQL() {
        openConnection();
        createSchema();
        getAllProgramari();
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
                stmt.execute("CREATE TABLE IF NOT EXISTS Programare(ID_Programare int, ID_Pacient int, Date varchar(50), Hour varchar(50), Purpose varchar(50))");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
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
    public void add(Programare programare) throws DuplicateObjectException {
        try {
            try(PreparedStatement preparedStatement = conn.prepareStatement("Insert into Programare values (?, ?, ?, ?, ?)")){
                preparedStatement.setInt(1, programare.getID());
                preparedStatement.setInt(2, programare.getPacient());
                preparedStatement.setString(3, programare.getData());
                preparedStatement.setString(4, programare.getOra());
                preparedStatement.setString(5, programare.getScop());

                super.add(programare);
                preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id){
        try{
            try(PreparedStatement preparedStatement = conn.prepareStatement("delete from Programare where ID_Programare ="+id)){
                super.remove(id);
                preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Programare newprogramare) {
        try {
            String updateSQL = "update Programare set ID_Pacient = ?, Date = ?, Hour = ?, Purpose = ? where ID_Programare = "+newprogramare.getID();
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)){
                preparedStatement.setInt(1, newprogramare.getPacient());
                preparedStatement.setString(2, newprogramare.getData());
                preparedStatement.setString(3, newprogramare.getOra());
                preparedStatement.setString(4, newprogramare.getScop());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected>0)
                    System.out.println("Programare updated successfully");
                else
                    System.out.println("Programare with ID:"+newprogramare.getID()+" not found");

                super.update(newprogramare);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllProgramari() {
        entities.clear();
        try {
            try (PreparedStatement preparedStatement = conn.prepareStatement("Select * from Programare")) {
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Programare programare = new Programare(rs.getInt("ID_Programare"), rs.getInt("ID_Pacient"), rs.getString("Date"), rs.getString("Hour"), rs.getString("Purpose"));
                    entities.add(programare);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
