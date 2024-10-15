package attendanceManager.database;

import attendanceManager.models.Student;
import attendanceManager.models.Schedule;
import attendanceManager.models.AttendanceRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection conn;

    public Database() {
        connect();
        createTables();
    }

    public boolean isEmpty() {
        String sql = "SELECT COUNT(*) AS total FROM students";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int total = rs.getInt("total");
            return total == 0;

        } catch (SQLException e) {
            System.out.println("Помилка перевірки бази даних: " + e.getMessage());
            return true;
        }
    }

    private void connect() {
        try {
            // Підключення до бази даних SQLite
            conn = DriverManager.getConnection("jdbc:sqlite:attendance.db");
        } catch (SQLException e) {
            System.out.println("Помилка підключення до бази даних: " + e.getMessage());
        }
    }

    private void createTables() {
        String createStudentsTable = "CREATE TABLE IF NOT EXISTS students ("
                + "id TEXT PRIMARY KEY,"
                + "name TEXT NOT NULL"
                + ");";

        String createScheduleTable = "CREATE TABLE IF NOT EXISTS schedule ("
                + "class_id TEXT PRIMARY KEY,"
                + "subject TEXT NOT NULL,"
                + "date_time TEXT NOT NULL"
                + ");";

        String createAttendanceTable = "CREATE TABLE IF NOT EXISTS attendance ("
                + "student_id TEXT NOT NULL,"
                + "class_id TEXT NOT NULL,"
                + "present INTEGER NOT NULL,"
                + "FOREIGN KEY(student_id) REFERENCES students(id),"
                + "FOREIGN KEY(class_id) REFERENCES schedule(class_id)"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createStudentsTable);
            stmt.execute(createScheduleTable);
            stmt.execute(createAttendanceTable);
        } catch (SQLException e) {
            System.out.println("Помилка створення таблиць: " + e.getMessage());
        }
    }

    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                students.add(new Student(rs.getString("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Помилка отримання списку студентів: " + e.getMessage());
        }

        return students;
    }

    public List<Schedule> getAllSchedules() {
        String sql = "SELECT * FROM schedule";
        List<Schedule> schedules = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                schedules.add(new Schedule(rs.getString("class_id"), rs.getString("subject"), rs.getString("date_time")));
            }
        } catch (SQLException e) {
            System.out.println("Помилка отримання розкладу: " + e.getMessage());
        }

        return schedules;
    }

    public void insertAttendance(AttendanceRecord record) {
        String sql = "INSERT INTO attendance(student_id, class_id, present) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, record.getStudentId());
            pstmt.setString(2, record.getClassId());
            pstmt.setInt(3, record.isPresent() ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Помилка додавання запису про відвідування: " + e.getMessage());
        }
    }

    public List<AttendanceRecord> getAttendanceRecords() {
        String sql = "SELECT * FROM attendance";
        List<AttendanceRecord> records = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                records.add(new AttendanceRecord(
                        rs.getString("student_id"),
                        rs.getString("class_id"),
                        rs.getInt("present") == 1
                ));
            }
        } catch (SQLException e) {
            System.out.println("Помилка отримання даних про відвідування: " + e.getMessage());
        }

        return records;
    }
}

