import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.sql.ResultSet;

public class BorrowingService {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/library_db";

    private static final String USER = "postgres";

    private static final String PASSWORD = "YOUR_PASSWORD";

    public static void borrowBook() {

        Scanner scanner = new Scanner(System.in);

        try {

            Connection conn =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.print("ID du livre : ");
            int bookId = Integer.parseInt(scanner.nextLine());

            System.out.print("ID du membre : ");
            int memberId = Integer.parseInt(scanner.nextLine());

            String sql =
                    "INSERT INTO borrowings(book_id, member_id) VALUES (?, ?)";

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setInt(1, bookId);
            stmt.setInt(2, memberId);

            stmt.executeUpdate();

            String updateBook =
                    "UPDATE books SET available = false WHERE id = ?";

            PreparedStatement stmt2 =
                    conn.prepareStatement(updateBook);

            stmt2.setInt(1, bookId);

            stmt2.executeUpdate();

            System.out.println("Livre emprunté avec succès !");

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

public static void viewBorrowings() {

    try {

        Connection conn =
                DriverManager.getConnection(URL, USER, PASSWORD);

        String sql =
                """
                SELECT
                    borrowings.id,
                    books.title,
                    members.full_name,
                    borrowings.borrow_date,
                    borrowings.status
                FROM borrowings
                JOIN books
                    ON borrowings.book_id = books.id
                JOIN members
                    ON borrowings.member_id = members.id
                ORDER BY borrowings.id
                """;

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();

        System.out.println("\n===== LISTE DES EMPRUNTS =====");

        while (rs.next()) {

            System.out.println(
                    rs.getInt("id") + " | " +
                    rs.getString("title") + " | " +
                    rs.getString("full_name") + " | " +
                    rs.getDate("borrow_date") + " | " +
                    rs.getString("status")
            );
        }

        conn.close();

    } catch (Exception e) {

        e.printStackTrace();

    }
}

public static void returnBook() {

    Scanner scanner = new Scanner(System.in);

    try {

        Connection conn =
                DriverManager.getConnection(URL, USER, PASSWORD);

        System.out.print("ID de l'emprunt : ");
        int borrowingId = Integer.parseInt(scanner.nextLine());

        String updateBorrowing =
                """
                UPDATE borrowings
                SET status = 'returned',
                    return_date = CURRENT_DATE
                WHERE id = ?
                """;

        PreparedStatement stmt =
                conn.prepareStatement(updateBorrowing);

        stmt.setInt(1, borrowingId);

        int rows = stmt.executeUpdate();

        if (rows > 0) {

            String updateBook =
                    """
                    UPDATE books
                    SET available = true
                    WHERE id = (
                        SELECT book_id
                        FROM borrowings
                        WHERE id = ?
                    )
                    """;

            PreparedStatement stmt2 =
                    conn.prepareStatement(updateBook);

            stmt2.setInt(1, borrowingId);

            stmt2.executeUpdate();

            System.out.println("Livre retourné avec succès !");

        } else {

            System.out.println("Aucun emprunt trouvé.");

        }

        conn.close();

    } catch (Exception e) {

        e.printStackTrace();

    }
}
}