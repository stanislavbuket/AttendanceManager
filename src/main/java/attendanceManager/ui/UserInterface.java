package attendanceManager.ui;

import attendanceManager.attendance.AttendanceManager;
import attendanceManager.report.ReportGenerator;
import attendanceManager.viewer.DataViewer;
import attendanceManager.database.Database;

import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private AttendanceManager attendanceManager;
    private ReportGenerator reportGenerator;
    private DataViewer dataViewer;
    private Database database;

    public UserInterface(Database database) {
        this.scanner = new Scanner(System.in);
        this.database = database;
        this.attendanceManager = new AttendanceManager(database);
        this.reportGenerator = new ReportGenerator(database);
        this.dataViewer = new DataViewer(database);
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\nВиберіть опцію:");
            System.out.println("1. Ввести дані про відвідування");
            System.out.println("2. Переглянути дані");
            System.out.println("3. Згенерувати звіт");
            System.out.println("4. Вийти");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    attendanceManager.recordAttendance();
                    break;
                case "2":
                    dataViewer.viewData();
                    break;
                case "3":
                    reportGenerator.generateReport();
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }
}
