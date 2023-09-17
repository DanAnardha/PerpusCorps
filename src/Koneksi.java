
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static Connection koneksi = null;

    public static Connection getConnection() {
        boolean kesalahan = false;
        if (!kesalahan) {
            try {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                }
                koneksi = DriverManager.getConnection("jdbc:mysql:///perpustakaan", "root", "");
                System.out.println("Koneksi Database Berhasil!");
            } catch (SQLException ex) {
                System.out.println("Koneksi Database Gagal!");
                kesalahan = true;
            }
        }
        return koneksi;
    }
}
