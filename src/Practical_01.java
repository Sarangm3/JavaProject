import java.sql.*;

public class Practical_01 {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/db";
    static final String USER = "root";
    static final String PASS = "SArang3@";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = null;
//            String sql = "CREATE TABLE Papers " +
//                    "(subID INTEGER not NULL, " +
//                    " subName VARCHAR(255), " +
//                    " paper VARCHAR(255), " +
//                    " PRIMARY KEY ( subID ))";
//            stmt.executeUpdate(sql);

            // Inserting data
//            sql = "INSERT INTO  Papers (subID, subName, paper) " +
//                    "VALUES (3160707, 'Advance java Programming', '2019_Paper')";
//            stmt.executeUpdate(sql);

            // Querying data
            sql = "SELECT subID, subName, paper FROM Papers";
            ResultSet rs = stmt.executeQuery(sql);

            // Displaying data
            while (rs.next()) {
                int subID = rs.getInt("subID");
                String subName = rs.getString("subName");
                String paper = rs.getString("paper");
                System.out.println("subID: " + subID + ", subName: " + subName + ", paper: " + paper);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
