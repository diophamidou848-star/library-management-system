import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/library_db";

    private static final String USER = "postgres";

    private static final String PASSWORD = "Diop@9321";

    public static void main(String[] args) {

        try {
            Connection conn =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Connexion PostgreSQL réussie !");

            conn.close();

        } catch (Exception e) {

            System.out.println("Erreur de connexion !");
            e.printStackTrace();

        }
    }
}