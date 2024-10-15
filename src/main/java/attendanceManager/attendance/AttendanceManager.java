package attendanceManager.attendance;

import attendanceManager.database.Database;
import attendanceManager.models.AttendanceRecord;
import attendanceManager.models.Student;
import attendanceManager.models.Schedule;
import attendanceManager.security.SecurityManager;

import java.util.List;
import java.util.Scanner;

public class AttendanceManager {
    private Database database;
    private Scanner scanner;
    private SecurityManager securityManager;

    public AttendanceManager(Database database) {
        this.database = database;
        this.scanner = new Scanner(System.in);
        this.securityManager = new SecurityManager();
    }

    public void recordAttendance() {
        List<Student> students = database.getAllStudents();
        List<Schedule> schedules = database.getAllSchedules();

        if (students.isEmpty()) {
            System.out.println("Немає студентів у базі даних. Зверніться до адміністратора.");
            return;
        }

        if (schedules.isEmpty()) {
            System.out.println("Розклад відсутній у базі даних. Зверніться до адміністратора.");
            return;
        }

        System.out.println("Виберіть заняття:");
        for (int i = 0; i < schedules.size(); i++) {
            System.out.println((i + 1) + ". " + schedules.get(i).getSubject() + " - " + schedules.get(i).getDateTime());
        }

        int classChoice = Integer.parseInt(scanner.nextLine()) - 1;
        Schedule selectedSchedule = schedules.get(classChoice);

        for (Student student : students) {
            System.out.println("Студент: " + student.getName() + " (присутній? y/n):");
            String input = scanner.nextLine();
            boolean isPresent = input.equalsIgnoreCase("y");

            AttendanceRecord record = new AttendanceRecord(
                    student.getId(),
                    selectedSchedule.getClassId(),
                    isPresent
            );

            database.insertAttendance(record);
        }

        System.out.println("Дані про відвідування збережено.");
    }
}
