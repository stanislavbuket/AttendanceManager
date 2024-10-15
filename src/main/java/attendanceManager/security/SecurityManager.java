package attendanceManager.security;

import java.util.Scanner;

public class SecurityManager {

    public boolean authenticate() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть пароль для доступу до системи:");
        String password = scanner.nextLine();

        //хешування введеного пароля
        String hashedPassword = md5(password);

        assert hashedPassword != null;
        //md5("password")
        String PASSWORD_HASH = "5f4dcc3b5aa765d61d8327deb882cf99";
        if (hashedPassword.equals(PASSWORD_HASH)) {
            System.out.println("Авторизація успішна.");
            return true;
        } else {
            System.out.println("Невірний пароль.");
            return false;
        }
    }

    public String encryptData(String data) {
        //просте шифрування (наприклад, базове XOR)
        char[] chars = data.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= 'k'; //'k' - ключ шифрування
        }
        return new String(chars);
    }

    public String decryptData(String data) {
        //розшифрування (зворотнє до шифрування)
        return encryptData(data); //оскільки XOR симетричний
    }

    private String md5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("Помилка хешування пароля: " + e.getMessage());
        }
        return null;
    }
}
