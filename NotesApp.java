import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class NotesApp {

    private static final String FILE_NAME = "notes.txt";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            printMenu();
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addNote(scanner);
                    break;
                case 2:
                    viewNotes();
                    break;
                case 3:
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 3);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n==== Java Notes App ====");
        System.out.println("1. Add Note");
        System.out.println("2. View Notes");
        System.out.println("3. Exit");
    }

    private static void addNote(Scanner scanner) {

        System.out.println("Enter your note:");
        String note = scanner.nextLine();

        String timeStamp = LocalDateTime.now().format(FORMATTER);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write("[" + timeStamp + "]");
            writer.newLine();
            writer.write(note);
            writer.newLine();
            writer.write("----------------------------------------");
            writer.newLine();
            System.out.println("Note saved successfully!");

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void viewNotes() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No notes found. File does not exist.");
            return;
        }

        System.out.println("\n==== Saved Notes ====");

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
