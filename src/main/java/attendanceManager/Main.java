package attendanceManager;
import attendanceManager.ui.UserInterface;
import attendanceManager.security.SecurityManager;
import attendanceManager.database.Database;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        //встановлюємо кодування UTF-8 для виводу в консоль
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        SecurityManager securityManager = new SecurityManager();
        Database database = new Database();

        if (securityManager.authenticate()) {
            UserInterface ui = new UserInterface(database);
            ui.start();
        } else {
            System.out.println("Невірний пароль. Програма завершує роботу.");
        }
    }
}
