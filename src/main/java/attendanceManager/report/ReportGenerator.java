package attendanceManager.report;

import attendanceManager.database.Database;
import attendanceManager.models.AttendanceRecord;
import attendanceManager.models.Student;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ReportGenerator {
    private Database database;

    public ReportGenerator(Database database) {
        this.database = database;
    }

    public void generateReport() {
        List<Student> students = database.getAllStudents();
        List<AttendanceRecord> records = database.getAttendanceRecords();

        if (students.isEmpty()) {
            System.out.println("Немає студентів у базі даних. Зверніться до адміністратора.");
            return;
        }

        if (records.isEmpty()) {
            System.out.println("Дані про відвідування відсутні.");
            return;
        }

        Map<String, Integer> attendanceCount = new HashMap<>();

        for (AttendanceRecord record : records) {
            if (record.isPresent()) {
                attendanceCount.put(record.getStudentId(), attendanceCount.getOrDefault(record.getStudentId(), 0) + 1);
            }
        }

        System.out.println("\nЗвіт про відвідування:");
        for (Student student : students) {
            int count = attendanceCount.getOrDefault(student.getId(), 0);
            System.out.println("Студент: " + student.getName() + " - Присутність: " + count + " занять");
        }
    }
}
