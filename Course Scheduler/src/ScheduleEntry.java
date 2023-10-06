/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author abhidesai
 */
import java.sql.Timestamp;

public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentID;
    private String status;
    private Timestamp timestamp;

    public ScheduleEntry(String semester, String studentID, String courseCode, String status, Timestamp timestamp) {
        this.semester = semester;
        this.studentID = studentID;
        this.courseCode = courseCode;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStatus() {
        if("s".equals(status)){
            return "s";
        }
        else {
            return "w";
        }
        
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    
}