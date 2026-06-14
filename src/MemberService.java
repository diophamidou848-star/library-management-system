import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class MemberService {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/library_db";

    private static final String USER = "postgres";

    private static final String PASSWORD = "Diop@9321";

    public static void addMember() {

        Scanner scanner = new Scanner(System.in);

        try {

            Connection conn =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.print("Nom complet : ");
            String fullName = scanner.nextLine();

            System.out.print("Email : ");
            String email = scanner.nextLine();

            String sql =
                    "INSERT INTO members(full_name, email) VALUES (?, ?)";

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setString(1, fullName);
            stmt.setString(2, email);

            stmt.executeUpdate();

            System.out.println("Membre ajouté avec succès !");

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

public static void viewMembers() {

    try {

        Connection conn =
                DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT * FROM members ORDER BY id";

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();

        System.out.println("\n===== LISTE DES MEMBRES =====");

        while (rs.next()) {

            System.out.println(
                    rs.getInt("id") + " | " +
                    rs.getString("full_name") + " | " +
                    rs.getString("email")
            );
        }

        conn.close();

    } catch (Exception e) {

        e.printStackTrace();

    }
}

public static void updateMember() {

    Scanner scanner = new Scanner(System.in);

    try {

        Connection conn =
                DriverManager.getConnection(URL, USER, PASSWORD);

        System.out.print("ID du membre à modifier : ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Nouveau nom : ");
        String fullName = scanner.nextLine();

        System.out.print("Nouvel email : ");
        String email = scanner.nextLine();

        String sql =
                "UPDATE members SET full_name = ?, email = ? WHERE id = ?";

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setString(1, fullName);
        stmt.setString(2, email);
        stmt.setInt(3, id);

        int rows = stmt.executeUpdate();

        if (rows > 0) {
            System.out.println("Membre modifié avec succès !");
        } else {
            System.out.println("Aucun membre trouvé avec cet ID.");
        }

        conn.close();

    } catch (Exception e) {

        e.printStackTrace();

    }
}
public static void deleteMember() {

    Scanner scanner = new Scanner(System.in);

    try {

        Connection conn =
                DriverManager.getConnection(URL, USER, PASSWORD);

        System.out.print("ID du membre à supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());

        String sql = "DELETE FROM members WHERE id = ?";

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setInt(1, id);

        int rows = stmt.executeUpdate();

        if (rows > 0) {
            System.out.println("Membre supprimé avec succès !");
        } else {
            System.out.println("Aucun membre trouvé avec cet ID.");
        }

        conn.close();

    } catch (Exception e) {

        e.printStackTrace();

    }
}
}