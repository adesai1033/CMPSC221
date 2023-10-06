
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

/**
 *
 * @author abhidesai
 */
public class ScheduleQueries 
{
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getScheduleByStudent; 
    private static PreparedStatement getScheduledStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentsByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static PreparedStatement dropScheduleByStudent;
    private static PreparedStatement getAllAvailable;
    
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule(semester, studentid, coursecode, status, timestamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1, entry.getSemester());
           
            addScheduleEntry.setString(2, entry.getStudentID());
            
            
           
            addScheduleEntry.setString(3, entry.getCourseCode());
            
            
            addScheduleEntry.setString(4, entry.getStatus());
            
            
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());
            addScheduleEntry.executeUpdate();
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode)   
    {
        connection = DBConnection.getConnection();
        int count = 0;
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and coursecode = ?" );
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            
            resultSet = getScheduledStudentCount.executeQuery();
            
            while(resultSet.next())
            {
                count = resultSet.getInt(1);
            }
                    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return count;
    }
    
     public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) 
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule where semester = ? and studentid = ?");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            
            while(resultSet.next())
            {
                String currstudentID = resultSet.getString("STUDENTID");
                String currsemester = resultSet.getString("SEMESTER");
                String courseCode = resultSet.getString("COURSECODE");
                String status = resultSet.getString("STATUS");
                Timestamp timestamp = resultSet.getTimestamp("TIMESTAMP");
                
                ScheduleEntry currentSchedule = new ScheduleEntry(currsemester, currstudentID, courseCode, status, timestamp); 
                schedule.add(currentSchedule);
            }
                    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return schedule;
        
    }
    
     //TODO:
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduledStudentsByCourse = connection.prepareStatement("select * from app.schedule where semester = ? and coursecode = ? and status = ?");
            getScheduledStudentsByCourse.setString(1,semester);
            getScheduledStudentsByCourse.setString(2, courseCode);
            getScheduledStudentsByCourse.setString(3, "s");
            
            resultSet = getScheduledStudentsByCourse.executeQuery();
            
            
            while(resultSet.next())
            {
                String currstudentID = resultSet.getString("STUDENTID");
                String status = resultSet.getString("STATUS");
                Timestamp timestamp = resultSet.getTimestamp("TIMESTAMP");
                
                ScheduleEntry currentSchedule = new ScheduleEntry(semester, currstudentID, courseCode, status, timestamp); 
                schedule.add(currentSchedule);
            }
            
            
                    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
    }
     
    //Todo:
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
        {
            getWaitlistedStudentsByCourse = connection.prepareStatement("select * from app.schedule where semester = ? and coursecode = ? and status = ?");
            getWaitlistedStudentsByCourse.setString(1,semester);
            getWaitlistedStudentsByCourse.setString(2, courseCode);
            getWaitlistedStudentsByCourse.setString(3, "w");
            
            resultSet = getWaitlistedStudentsByCourse.executeQuery();
            
            
            while(resultSet.next())
            {
                String currstudentID = resultSet.getString("STUDENTID");
                String status = resultSet.getString("STATUS");
                Timestamp timestamp = resultSet.getTimestamp("TIMESTAMP");
                
                ScheduleEntry currentSchedule = new ScheduleEntry(semester, currstudentID, courseCode, status, timestamp); 
                schedule.add(currentSchedule);
            }
            
            
                    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
    }
    
    //TODO:
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {            
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and studentId = ? and courseCode = ?");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2,studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            
            
            dropStudentScheduleByCourse.executeUpdate();
            
            
            
           
            //updateScheduleEntry(semester, new ScheduleEntry(semester, studentID, courseCode, "s", resultSet.getTimestamp("TIMESTAMP")));
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode) 
    {
        connection = DBConnection.getConnection();
        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and courseCode = ?");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();
                    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
  
    public static void updateScheduleEntry(String semester, ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            getScheduleByStudent = connection.prepareStatement("update app.schedule set status = 's' where semester = ? and studentid = ? and coursecode = ?");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, entry.getStudentID());
            getScheduleByStudent.setString(3, entry.getCourseCode());
            getScheduleByStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    /**
    public static void dropScheduleByStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        try 
        {
            dropScheduleByStudent = connection.prepareStatement("delete from app.schedule where studentid = ? ");
            dropScheduleByStudent.setString(1, studentID); 
            getAllAvailable = connection.prepareStatement("select * from app.schedule where studentid = ?");
            getAllAvailable.setString(1, studentID);
            resultSet = getAllAvailable.executeQuery();
            while (resultSet.next())
            {
                updateScheduleEntry(resultSet.getString("SEMESTER"), resultSet.getString("COURSECODE"));
            }
            dropScheduleByStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    **/
}

