/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author abhidesai
 */

public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudentID; //we will get rid of this method when we update gui to display name as well as student ID in combobox
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student(studentID, firstname, lastname) values (?, ?, ?)");
            addStudent.setString(1, student.getStudentID());
            
            addStudent.setString(2, student.getFirstName());
           
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> student = new ArrayList<StudentEntry>();
        try
        {
            getAllStudents = connection.prepareStatement("select * from app.student");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                String studentID = resultSet.getString("STUDENTID");
                String firstName = resultSet.getString("FIRSTNAME");
                String lastName = resultSet.getString("LASTNAME");
                StudentEntry currentStudent = new StudentEntry(studentID, firstName, lastName); 
                student.add(currentStudent);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
        
    }
    
    public static String getStudentID(String lastName){
        connection = DBConnection.getConnection();
        String id = "";
        try 
        {
            getStudent = connection.prepareStatement("Select StudentID from app.student where LastName = ?");
            getStudent.setString(1,lastName);
            resultSet = getStudent.executeQuery();
            
            while (resultSet.next()) {
                id = resultSet.getString(1);
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return id;
    }
    public static StudentEntry getStudent(String studentID) 
    {
        connection = DBConnection.getConnection();
        String firstName = "";
        String lastName = "";
        try
        {
            getStudent = connection.prepareStatement("select firstname, lastname from app.student where studentID = ?");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            while (resultSet.next()) {
                firstName = resultSet.getString(1);
                lastName = resultSet.getString(2);
                
            }
            return new StudentEntry(studentID, firstName, lastName);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return null;
    } 
    
    public static void dropStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from app.student where studentID = ?");  
            dropStudent.setString(1,studentID);
            dropStudent.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
   
}
