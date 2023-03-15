import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MaterialServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Display the user interface using HTML forms
        out.println("<html><body>");
        out.println("<h2>Material Details</h2>");
        out.println("<form action=\"MaterialServlet\" method=\"post\">");
        out.println("Subject Code: <input type=\"text\" name=\"subjectCode\"><br>");
        out.println("Subject Name: <input type=\"text\" name=\"subName\"><br>");
        out.println("Paper Detail: <input type=\"text\" name=\"paperDetail\"><br>");
        out.println("URL: <input type=\"text\" name=\"url\"><br>");
        out.println("Search : <input type=\"text\" name=\"search\"><br>");
        out.println("Operation:");
        out.println("<select name=\"operation\">");
        out.println("<option value=\"add\">Add</option>");
        out.println("<option value=\"view\">View</option>");
        out.println("<option value=\"modify\">Modify</option>");
        out.println("<option value=\"delete\">Delete</option>");
        out.println("</select><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve the user's input
        int subjectCode = Integer.parseInt(request.getParameter("subjectCode"));
        String subName = request.getParameter("subName");
        String paperDetail = request.getParameter("paperDetail");
        String url = request.getParameter("url");
        String operation = request.getParameter("operation");
        String search = request.getParameter("search");

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = Practical_01_DataBase.getConnection();
            stmt = conn.createStatement();

            // Perform the appropriate operation based on the selected option
            if (operation.equals("add")) {
                Practical_01_DataBase.insertPaperDetail(stmt, new Data(subjectCode, subName, paperDetail, url));
                out.println("<html><body>");
                out.println("<h2>Material Added Successfully!</h2>");
                out.println("</body></html>");
            } else if (operation.equals("view")) {
                ResultSet rs = Practical_01_DataBase.searchData(stmt,search);
                String searchResult = Practical_01_DataBase.getSearchResult(rs);
                out.println("<html><body>");
                out.println("<h2>Material Details</h2>");
                out.println("<pre>" + searchResult + "</pre>");
                out.println("</body></html>");
            } else if (operation.equals("modify")) {
                Practical_01_DataBase.updatePaperDetail(stmt, new Data(subjectCode, subName, paperDetail, url));
                out.println("<html><body>");
                out.println("<h2>Material Modified Successfully!</h2>");
                out.println("</body></html>");
            } else if (operation.equals("delete")) {
                Practical_01_DataBase.deletePaperDetail(stmt, subjectCode);
                out.println("<html><body>");
                out.println("<h2>Material Deleted Successfully!</h2>");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("<h2>Invalid operation!</h2>");
                out.println("</body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


