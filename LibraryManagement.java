/*Group number 4 members
 *  1.Kiara Mitchelle 23/07701
 * 2.Erick Steve Oyugi 23/04842
 * 3.Mureithi John 23/06541
 * 4.Trinity Ebotsa 23/05086
 * 5.Rayan Abdi 23/06180
 * 6.Gabriel Kalila 23/04913
 * 7.Simon Lemaiyan 23/04635
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

 /* Book Class
 Represents a book in the library with attributes like title, author, ISBN, and status                                                                                       v                                            bnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
A Book represents an individual item in the library with key attributes: the title (name of the book)                                      vb,
the author (who wrote it), the ISBN (a unique identifier for the book edition), and the status
(indicating whether it's "available" or "checked out"). The title and author help users identify the
book, while the ISBN ensures the correct edition is tracked. The status helps the library manage
availability, indicating if a book is ready for checkout or currently borrowed by a member.*/
class Book {
    String title;
    String author;
    String ISBN;
    String status;

    public Book(String bookTitle, String bookAuthor, String bookISBN, String bookStatus) {
        title = bookTitle;
        author = bookAuthor;
        ISBN = bookISBN;
        status = bookStatus;
    }
}

// Member Class
/*A Member represents an individual registered in the library system. 
Each member has a unique ID, a name, and a list of borrowedBooks to track the books they have currently checked out. 
The ID is used to identify the member, while the name helps personalize interactions. 
The borrowedBooks list keeps track of the books the member has borrowed,
 allowing the library to manage checkouts and returns effectively. */
class Member {
    int id;
    String name;
    ArrayList<Book> borrowedBooks;

    public Member(int memberId, String memberName) {
        id = memberId;
        name = memberName;
        borrowedBooks = new ArrayList<>();
    }
}

// Transaction Class
/*A Transaction represents the borrowing and returning of a book in the library.
 It has two key attributes: the borrowDate, which records when the book was checked out, 
 and the returnDate, which records when the book was returned.
This class helps track the duration of a book's checkout and is useful for calculating due dates and late fees.
 Transactions are linked to specific books and members, 
ensuring accurate record-keeping of all borrowing activities. */
class Transaction {
    Date borrowDate;
    Date returnDate;

    public Transaction(Date dateBorrowed, Date dateReturned) {
        borrowDate = dateBorrowed;
        returnDate = dateReturned;
    }
}

// Library Class
/*The Library class manages the overall functionality of the library system.
 It maintains collections of books and members, allowing for the addition of new books and members. 
 The class provides methods to search for books by title or author, check out and return books, 
 and calculate due dates and late fees. It also generates member reports to show which books a member has borrowed,
 ensuring efficient management of library operations. */
class Library {
    ArrayList<Book> bookCollection;
    ArrayList<Member> members;

    public Library() {
        bookCollection = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void addBook(Book book) {
        bookCollection.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    // Search Books by Title/Author
    public ArrayList<Book> searchBooks(String keyword) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book book : bookCollection) {
            if (book.title.toLowerCase().contains(keyword.toLowerCase()) || 
                book.author.toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    // Check Out Book
    public boolean checkOutBook(int memberId, String ISBN) {
        for (Book book : bookCollection) {
            if (book.ISBN.equals(ISBN) && book.status.equals("available")) {
                for (Member member : members) {
                    if (member.id == memberId) {
                        book.status = "checked out";
                        member.borrowedBooks.add(book);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Return Book
    public boolean returnBook(int memberId, String ISBN) {
        for (Member member : members) {
            if (member.id == memberId) {
                for (Book book : member.borrowedBooks) {
                    if (book.ISBN.equals(ISBN)) {
                        book.status = "available";
                        member.borrowedBooks.remove(book);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Calculate Due Date
    public Date calculateDueDate(Date borrowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(borrowDate);
        cal.add(Calendar.DAY_OF_MONTH, 14); // Assuming a 2-week loan period
        return cal.getTime();
    }

    // Calculate Late Fees
    public double calculateLateFees(Date returnDate, Date dueDate) {
        long difference = returnDate.getTime() - dueDate.getTime();
        long daysLate = difference / (1000 * 60 * 60 * 24);
        return daysLate > 0 ? daysLate * 1.00 : 0.00; // Assuming $1.00 per day late fee
    }

    // Generate Member Reports
    public String generateMemberReport(Member member) {
        StringBuilder report = new StringBuilder("Member ID: " + member.id + "\nName: " + member.name + "\nBorrowed Books:\n");
        for (Book book : member.borrowedBooks) {
            report.append(" - ").append(book.title).append(" by ").append(book.author).append(" (ISBN: ").append(book.ISBN).append(")\n");
        }
        return report.toString();
    }
}

// Main Class for Testing
/*The LibraryManagement class serves as the entry point for testing the library system.
It creates a Library instance, adds some books and members,
and demonstrates various operations like searching for books,
checking out books, and generating member reports.
The class also simulates the process of calculating due dates and late fees,
allowing for practical testing of the library's core functionality.
This main class is used to ensure the system works as expected by running various test scenarios.*/
public class LibraryManagement {
    public static void main(String[] args) {
        // Create Library
        Library library = new Library();

        // Add Books
        library.addBook(new Book("Atomic Habits", "Kiara Mitchelle", "203040", "available"));
        library.addBook(new Book("Obote Milton", "Erick Steve", "222444", "available"));

        // Add Members
        library.addMember(new Member(1, "Trinity"));
        library.addMember(new Member(2, "Rayan"));

        // Search Books
        ArrayList<Book> searchResults = library.searchBooks("2020");
        for (Book book : searchResults) {
            System.out.println("Found: " + book.title + " by " + book.author);
        }

        // Check Out Book
        if (library.checkOutBook(1, "203040")) {
            System.out.println("Book checked out successfully.");
        } else {
            System.out.println("Failed to check out book.");
        }

        // Generate Member Report
        Member member = library.members.get(0); // Trinity
        String report = library.generateMemberReport(member);
        System.out.println(report);

        // Calculate Due Date and Late Fees
        Date borrowDate = new Date();
        Date dueDate = library.calculateDueDate(borrowDate);
        System.out.println("Due Date: " + dueDate);

        // Simulate returning the book late
        Calendar cal = Calendar.getInstance();
        cal.setTime(borrowDate);
        cal.add(Calendar.DAY_OF_MONTH, 20); // Borrowed for 20 days
        Date returnDate = cal.getTime();
        double lateFees = library.calculateLateFees(returnDate, dueDate);
        System.out.println("Late Fees: " + lateFees);
    }
}
