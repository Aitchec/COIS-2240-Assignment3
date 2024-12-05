public class LibraryManagement {

    public static void main(String[] args) {
        // Create some books
        Book book1 = new Book("Programming", "George");
        Book book2 = new Book("Math", "George");
        Book book3 = new Book("AI", "Anne");
        Book book4 = new Book("Math", "Anne");

        // Create members
        Member member1 = new Member("Member 1");
        Member member2 = new Member("Member 2");

        // Members borrow books
        member1.borrowBook(book1);
        member1.borrowBook(book2);
        member2.borrowBook(book3);
        member2.borrowBook(book4);

        // Print borrowed books for each member
        System.out.println("Borrowed books for " + member1.getName() + ": " + member1.getBorrowedBooks());
        System.out.println("Borrowed books for " + member2.getName() + ": " + member2.getBorrowedBooks());

        // Member returns a book
        member1.returnBook(book2);

        // Print borrowed books after return
        System.out.println("Borrowed books for " + member1.getName() + ": " + member1.getBorrowedBooks());
        System.out.println("Borrowed books for " + member2.getName() + ": " + member2.getBorrowedBooks());
    }
}
