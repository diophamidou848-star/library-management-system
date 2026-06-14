import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class BookService {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/library_db";

    private static final String USER = "postgres";

    private static final String PASSWORD = "Diop@9321";

    // Ajouter un livre
    public static void addBook() {

        Scanner scanner = new Scanner(System.in);

        try {

            Connection conn =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.print("Titre : ");
            String title = scanner.nextLine();

            System.out.print("Auteur : ");
            String author = scanner.nextLine();

            System.out.print("Année : ");
            int year = Integer.parseInt(scanner.nextLine());

            String sql =
                    "INSERT INTO books(title, author, year) VALUES (?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, year);

            stmt.executeUpdate();

            System.out.println("Livre ajouté avec succès !");

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    // Afficher les livres
    public static void viewBooks() {

        try {

            Connection conn =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "SELECT * FROM books ORDER BY id";

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            System.out.println("\n===== LISTE DES LIVRES =====");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author") + " | " +
                        rs.getInt("year")
                );
            }

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    // Modifier un livre
    public static void updateBook() {

        Scanner scanner = new Scanner(System.in);

        try {

            Connection conn =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.print("ID du livre à modifier : ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Nouveau titre : ");
            String title = scanner.nextLine();

            System.out.print("Nouvel auteur : ");
            String author = scanner.nextLine();

            System.out.print("Nouvelle année : ");
            int year = Integer.parseInt(scanner.nextLine());

            String sql =
                    "UPDATE books SET title = ?, author = ?, year = ? WHERE id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, year);
            stmt.setInt(4, id);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Livre modifié avec succès !");
            } else {
                System.out.println("Aucun livre trouvé avec cet ID.");
            }

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

// Supprimer un livre
public static void deleteBook() {

    Scanner scanner = new Scanner(System.in);

    try {

        Connection conn =
                DriverManager.getConnection(URL, USER, PASSWORD);

        System.out.print("ID du livre à supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());

        String sql = "DELETE FROM books WHERE id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);

        int rows = stmt.executeUpdate();

        if (rows > 0) {
            System.out.println("Livre supprimé avec succès !");
        } else {
            System.out.println("Aucun livre trouvé avec cet ID.");
        }

        conn.close();

    } catch (Exception e) {

        e.printStackTrace();

    }
}
}