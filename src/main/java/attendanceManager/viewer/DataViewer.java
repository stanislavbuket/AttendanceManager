package attendanceManager.viewer;

import attendanceManager.database.Database;
import attendanceManager.models.AttendanceRecord;
import attendanceManager.models.Student;
import attendanceManager.models.Schedule;

import java.util.List;
import java.util.Scanner;

public class DataViewer {
    private Database database;
    private Scanner scanner;

    public DataViewer(Database database) {
        this.database = database;
        this.scanner = new Scanner(System.in);
    }

    public void viewData() {
        System.out.println("\nЩо ви хочете переглянути?");
        System.out.println("1. Таблицю відвідувань");
        System.out.println("2. Список студентів");
        System.out.println("3. Розклад занять");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                viewAttendanceTable();
                break;
            case "2":
                viewStudents();
                break;
            case "3":
                viewSchedule();
                break;
            default:
                System.out.println("Невірний вибір.");
        }
    }

    private void viewAttendanceTable() {
        List<AttendanceRecord> records = database.getAttendanceRecords();

        if (records.isEmpty()) {
            System.out.println("Дані про відвідування відсутні.");
            return;
        }

        System.out.println("\nТаблиця відвідувань:");
        for (AttendanceRecord record : records) {
            System.out.println("Студент ID: " + record.getStudentId() + ", Заняття ID: " + record.getClassId() + ", Присутній: " + (record.isPresent() ? "Так" : "Ні"));
        }
    }

    private void viewStudents() {
        List<Student> students = database.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("Список студентів порожній. Зверніться до адміністратора.");
            return;
        }

        System.out.println("\nСписок студентів:");
        for (Student student : students) {
            System.out.println("ID: " + student.getId() + ", Ім'я: " + student.getName());
        }
    }

    private void viewSchedule() {
        List<Schedule> schedules = database.getAllSchedules();

        if (schedules.isEmpty()) {
            System.out.println("Розклад відсутній. Зверніться до адміністратора.");
            return;
        }

        System.out.println("\nРозклад занять:");
        for (Schedule schedule : schedules) {
            System.out.println("ID заняття: " + schedule.getClassId() + ", Предмет: " + schedule.getSubject() + ", Дата та час: " + schedule.getDateTime());
        }
    }
}
