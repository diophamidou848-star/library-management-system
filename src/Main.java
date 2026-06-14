import java.util.Scanner;



public class Main {
    
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Add Member");
            System.out.println("6. View Members");
            System.out.println("7. Update Member");
            System.out.println("8. Delete Member");
            System.out.println("9. Borrow Book");
            System.out.println("10. View Borrowings");
            System.out.println("11. Return Book");
            System.out.println("0. Exit");

            System.out.print("Choice: ");

            int choice =
                    Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    BookService.addBook();
                    break;

                case 2:
                    BookService.viewBooks();
                    break;
                case 3:
                    BookService.updateBook();
                    break;
                
                case 4:
                    BookService.deleteBook();
                    break;
                case 5:
                    MemberService.addMember();
                    break;

                case 6:
                    MemberService.viewMembers();
                    break;
                case 7:
                    MemberService.updateMember();
                    break;
                case 8:
                    MemberService.deleteMember();
                    break;
                case 9:
                    BorrowingService.borrowBook();
                    break;
                case 10:
                    BorrowingService.viewBorrowings();
                    break;
                case 11:
                    BorrowingService.returnBook();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    return;


                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

}