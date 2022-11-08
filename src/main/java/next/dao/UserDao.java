package next.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {

    public Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/slipp_dev";
        String id = "root";
        String pw = "12345";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, id, pw);
        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            return null;
        }
    }

    public void insert(User user) throws SQLException {
        String sql = "insert into USERS values (?,?,?,?)";
        PreparedStatement pstmt = getConnection().prepareStatement(sql); // 쿼리문 정상적인지 확인
        pstmt.setString(1,user.getUserId()); // 인덱스는 1부터 시작한다.
        pstmt.setString(2,user.getPassword());
        pstmt.setString(3,user.getName());
        pstmt.setString(4,user.getEmail());
        pstmt.executeUpdate(); // 쿼리 실행
    }

    public void update(User user) throws SQLException {
        // TODO 구현 필요함.
    }

    public List<User> findAll() throws SQLException {
        // TODO 구현 필요함.
        return new ArrayList<User>();
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
