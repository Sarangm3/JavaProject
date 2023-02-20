import java.sql.*;
class Data{
    int subID;
    String subName;
    String paperDetail;
    String url;
    Data(int subID,String subName,String paperDetail,String url){
        this.subID = subID;
        this.subName = subName; this.paperDetail = paperDetail;
        this.url = url;
    }
}
public class Practical_01 {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/db";
    static final String USER = "root";
    static final String PASS = "SArang3@";

    static void createTable(Statement stmt){
        try{
            String sql = "CREATE TABLE Papers " +
                    "(subID INTEGER not NULL, " +
                    " subName VARCHAR(255), " +
                    " paperDetail VARCHAR(255), " +
                    " url VARCHAR(255), " +
                    " PRIMARY KEY (subID))";
            stmt.executeUpdate(sql);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    static void insertPaperDetail(Statement stmt,Data newData){
        try {
            String sql = "INSERT INTO Papers (subID, subName, paperDetail, url) " +
                    "VALUES (" + newData.subID +
                    ", '" + newData.subName +
                    "', '" + newData.paperDetail +
                    "', '" + newData.url + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static ResultSet searchData(Statement stmt){
        try{
            String sql = "SELECT subID, subName, paperDetail, url FROM Papers";
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    static void updatePaperDetail(Statement stmt, Data updatedData) {
        try {
            String sql = "UPDATE Papers " +
                    "SET subName = '" + updatedData.subName + "', " +
                    "paperDetail = '" + updatedData.paperDetail + "', " +
                    "url = '" + updatedData.url + "' " +
                    "WHERE subID = " + updatedData.subID;
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void deletePaperDetail(Statement stmt, int subID) {
        try {
            String sql = "DELETE FROM Papers WHERE subID = " + subID;
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // Creating table
//            createTable(stmt);

            //Insert data
            insertPaperDetail(stmt,new Data(3160714,"DATA MINING","2019_paper","http://example.com/paper"));

            // Updating paper detail
            updatePaperDetail(stmt, new Data(3160714,"DATA MINING","2020_paper","http://example.com/paper"));

            // Deleting paper detail
            deletePaperDetail(stmt, 3160714);

            insertPaperDetail(stmt,new Data(3160714,"DATA MINING","2019_paper","http://example.com/paper"));

            ResultSet rs = searchData(stmt);

            // Displaying data
            while (rs.next()) {
                int subID = rs.getInt("subID");
                String subName = rs.getString("subName");
                String paperDetail = rs.getString("paperDetail");
                String url = rs.getString("url");
                System.out.println(" subID: " + subID + "\n subName: " + subName + "\n paperDetail: " + paperDetail + "\n url :"+ url);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
