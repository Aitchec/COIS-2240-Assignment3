import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class LibraryManagementTest {

    private LibraryManagement library;
    private Transaction transaction;
    private Book book1;
    private Book book2;
    private Member member;

    @BeforeEach
    public void setUp() {
        // Initialize objects before each test
        library = new LibraryManagement();
        transaction = Transaction.getTransaction(); // Singleton instance
        book1 = new Book(100, "Java Programming");
        book2 = new Book(999, "Artificial Intelligence");
        member = new Member(1234, "John Doe");
    }

    @Test
    public void testBookId() {
        try {
            // Valid cases
            Book validBook1 = new Book(100, "Valid Book 100");
            Book validBook2 = new Book(999, "Valid Book 999");
            assertNotNull(validBook1);
            assertNotNull(validBook2);

            // Invalid case: ID greater than 999
            assertThrows(IllegalArgumentException.class, () -> new Book(1000, "Invalid Book 1000"));

            // Invalid case: ID less than 100
            assertThrows(IllegalArgumentException.class, () -> new Book(99, "Invalid Book 99"));

        } catch (Exception e) {
            fail("Exception should not be thrown for valid IDs");
        }
    }

    @Test
    public void testBorrowReturn() {
        // Test borrowing and returning books
        assertTrue(library.addBook(book1)); // Add book
        assertTrue(library.addMember(member)); // Add member
        assertTrue(member.getBorrowedBooks().isEmpty()); // Initially, member has no borrowed books

        // Borrow book
        transaction.borrowBook(book1, member);
        assertFalse(member.getBorrowedBooks().isEmpty()); // Member should have 1 borrowed book
        assertTrue(member.getBorrowedBooks().contains(book1)); // Ensure the correct book was borrowed

        // Try borrowing the same book again (should fail)
        transaction.borrowBook(book1, member);
        assertEquals(1, member.getBorrowedBooks().size()); // Member still has only 1 borrowed book

        // Return the book
        transaction.returnBook(book1, member);
        assertTrue(member.getBorrowedBooks().isEmpty()); // Member should have no borrowed books now

        // Try returning the book again (should fail)
        assertFalse(transaction.returnBook(book1, member)); // Should fail as the book is not borrowed anymore
    }

    @Test
    public void testSingletonTransaction() {
        try {
            // Access the constructor of the Transaction class via reflection
            Constructor<Transaction> constructor = Transaction.class.getDeclaredConstructor();
            constructor.setAccessible(true); // Make the constructor accessible

            // Check if the constructor is private
            int modifiers = constructor.getModifiers();
            assertEquals(Modifier.PRIVATE, modifiers); // Validate that the constructor is private

            // Try to create a new instance (should throw an exception)
            assertThrows(IllegalAccessException.class, () -> constructor.newInstance());

            // Ensure the Singleton behavior works by checking the instance
            Transaction transaction1 = Transaction.getTransaction();
            Transaction transaction2 = Transaction.getTransaction();
            assertSame(transaction1, transaction2); // Both references should point to the same instance

        } catch (Exception e) {
            fail("Reflection test failed: " + e.getMessage());
        }
    }
}
