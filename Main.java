import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные в следующем порядке (разделенные пробелом):");
        System.out.println("Фамилия Имя Отчество, дата рождения (формат dd.mm.yyyy), номер телефона (целое беззнаковое число), пол (f или m)");

        String userData = scanner.nextLine();
        scanner.close();

        try {
            processUserData(userData);
        } catch (UserDataFormatException e) {
            System.out.println("Ошибка в формате данных: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка чтения-записи файла: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processUserData(String userData) throws UserDataFormatException, IOException {
        String[] dataParts = userData.split(" ");
        if (dataParts.length != 6) {
            throw new UserDataFormatException("Неверное количество данных");
        }

        String fullName = dataParts[0] + " " + dataParts[1] + " " + dataParts[2];
        String dateOfBirth = dataParts[3];
        String phoneNumber = dataParts[4];
        char gender = dataParts[5].charAt(0);

        // Проверяем форматы данных
        if (!dateOfBirth.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            throw new UserDataFormatException("Неверный формат даты рождения");
        }

        try {
            Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            throw new UserDataFormatException("Неверный формат номера телефона");
        }

        if (gender != 'f' && gender != 'm') {
            throw new UserDataFormatException("Неверный формат пола");
        }

        // Создание и запись данных в файл
        try (FileWriter fileWriter = new FileWriter(dataParts[0] + ".txt", true)) {
            fileWriter.write(fullName + ", " + dateOfBirth + ", " + phoneNumber + ", " + gender + "\n");
        }

        System.out.println("Данные успешно обработаны и записаны в файл.");
    }
}