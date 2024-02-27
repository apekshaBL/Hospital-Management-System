package HospitalManagementSystem;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url= "jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="apeksha8600";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch(ClassNotFoundException e){
            e.printStackTrace();

        }
        Scanner scanner= new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection,scanner);
            Doctors doctor=new Doctors(connection);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add patient");
                System.out.println("2.View patient");
                System.out.println("3.View Doctors");
                System.out.println("4.Book Appointment");
                System.out.println("5.Exit");
                System.out.println("Enter your choice :");
                int choice=scanner.nextInt();

                switch(choice){
                    case 1:
                        //Add patient
                        patient.addPatient();
                        System.out.println();
                    case 2:
                        //view patient
                        patient.viewPatients();
                        System.out.println();
                    case 3:
                        //view project
                        doctor.viewDoctors();
                        System.out.println();
                    case 4:
                        //Book appointment
                        bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                    case 5:
                        return;
                    default:
                        System.out.println("Enter the valid choice");
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void bookAppointment(Patient patient,Doctors doctor,Connection connection,Scanner scanner){
        System.out.print("Enter patient id :");
        int patientId=scanner.nextInt();
        System.out.print("Enter doctor id :");
        int doctorId=scanner.nextInt();
        System.out.print("Enter appointment date(YYYY -MM- DD):");
        String appointmentDate=scanner.next();
        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if(checkDoctorAvailability(doctorId,appointmentDate,connection)){
                String appointmentQuery= "INSERT into appointments(patient_id,doctor_id,appointment_date)VALUES(?,?,?)";
                try{
                  PreparedStatement preparedStatement= connection.prepareStatement(appointmentQuery);
                  preparedStatement.setInt(1,patientId);
                  preparedStatement.setInt(2,doctorId);
                  preparedStatement.setString(3,appointmentDate);
                  int rowsAffected=preparedStatement.executeUpdate();
                  if(rowsAffected>0){
                      System.out.println("Appointment booked");
                  }
                  else{
                      System.out.println("Failed to book appointment");
                  }
                }catch(SQLException e){
                    e.printStackTrace();

                }

            }else{
                System.out.println("Doctor is not available on this date");
            }

        }else{
            System.out.println("Either doctor or patient doesn't exits !!");
        }
    }
    public static boolean checkDoctorAvailability(int  doctorId,String appointmentDate,Connection connection){
        String query ="SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date =?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int count=resultSet.getInt(1);
                if(count==0){
                    return true;
                }
                else{
                    return false;
                }
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return  false;
    }


}
