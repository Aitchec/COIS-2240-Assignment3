import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Transaction {
    private static Transaction transactionInstance;

    private Transaction() {
        // private constructor to prevent direct instantiation
    }

    public static Transaction getTransaction() {
        if (transactionInstance == null) {
            transactionInstance = new Transaction();
        }
        return transactionInstance;
    }

    public void saveTransaction(String transactionDetails) {
        try (FileWriter writer = new FileWriter("transactions.txt", true)) {
            writer.write(transactionDetails + "\n");
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    public void borrowBook(Book book, Member member) {
        member.borrowBook(book);
        saveTransaction("Borrowed: " + book.getTitle() + " by " + member.getName());
    }

    public void returnBook(Book book, Member member) {
        member.returnBook(book);
        saveTransaction("Returned: " + book.getTitle() + " by " + member.getName());
    }

    public void displayTransactionHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading transaction history: " + e.getMessage());
        }
    }
}