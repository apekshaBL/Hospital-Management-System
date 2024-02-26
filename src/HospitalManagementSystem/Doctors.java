package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {

    private Connection connection;


    public Doctors(Connection connection, Scanner scanner) { //constructor
        this.connection = connection;

    }



    public void viewDoctors() {
        String query = "Select * from doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors : ");
            System.out.println("+------------+---------------------+-----------------------------+");
            System.out.println("| Doctor Id  | Name                |  Specialization             | ");
            System.out.println("+------------+---------------------+-------------+---------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String specialization = resultSet.getString("specialization");
                System.out.printf("|%-12s|%-20s|%-29s|\n",id,name,specialization);
                System.out.println("+------------+---------------------+-------------+---------------+");

            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }








}