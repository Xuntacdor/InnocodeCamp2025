package dao;

import model.User;
import java.sql.*;
import util.DBConnection;

public class UserDAO {

    public static User checkLogin(String email, String password) {
        String sql = "SELECT * FROM Users WHERE email = ? AND password_hash = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setRole(rs.getString("role"));
                    user.setCamBalance(rs.getInt("cam_balance"));
                    user.setIsPremium(rs.getBoolean("is_premium"));
                    user.setPremiumExpiredAt(rs.getString("premium_expired_at")); // kiểu String
                    System.out.println("LOGIN THÀNH CÔNG");
                    return user;
                }
            }
            System.out.println("KHÔNG TÌM THẤY NGƯỜI DÙNG");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertUser(String fullName, String email, String password, String gender, String dob) {
        String sql = "INSERT INTO Users (full_name, email, password_hash, gender, date_of_birth) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, fullName);
            stm.setString(2, email);
            stm.setString(3, password);
            stm.setString(4, gender);
            stm.setString(5, dob);
            stm.executeUpdate();
            System.out.println("Sign in thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateCamBalance(int userId, int newBalance) {
        String sql = "UPDATE Users SET cam_balance = ? WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, newBalance);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            System.out.println("Cập nhật số CAM thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updatePremiumStatus(int userId, boolean isPremium, String expiredAt) {
        String sql = "UPDATE Users SET is_premium = ?, premium_expired_at = ? WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setBoolean(1, isPremium);
            stmt.setString(2, expiredAt);  // format: yyyy-MM-dd HH:mm:ss
            stmt.setInt(3, userId);
            stmt.executeUpdate();
            System.out.println("Cập nhật trạng thái Premium thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setGender(rs.getString("gender"));
                    user.setDateOfBirth(rs.getString("date_of_birth"));
                    user.setRole(rs.getString("role"));
                    user.setActive(rs.getBoolean("is_active"));
                    user.setPhoneNumber(rs.getString("phone_number"));
                    user.setCamBalance(rs.getInt("cam_balance"));
                    user.setIsPremium(rs.getBoolean("is_premium"));
                    user.setPremiumExpiredAt(rs.getString("premium_expired_at"));
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getCamBalanceById(int userId) {
        String sql = "SELECT cam_balance FROM Users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cam_balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void increaseCamBalance(int userId, int amount) {
        String sql = "UPDATE Users SET cam_balance = cam_balance + ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, amount);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            System.out.println("Tăng CAM thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean updateUser(model.User user) {
        try (java.sql.Connection conn = util.DBConnection.getConnection()) {
            String sql = "UPDATE users SET full_name=?, email=? WHERE user_id=?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getUserId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean userExistsByEmail(String email) {
        String sql = "SELECT 1 FROM Users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setGender(rs.getString("gender"));
                user.setDateOfBirth(rs.getString("date_of_birth"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getBoolean("is_active"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setCamBalance(rs.getInt("cam_balance"));
                user.setIsPremium(rs.getBoolean("is_premium"));
                user.setPremiumExpiredAt(rs.getString("premium_expired_at"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User insertGoogleUser(String email, String fullName) {
        String sql = "INSERT INTO Users (email, full_name, password_hash, role, is_active, cam_balance, is_premium) "
                + "VALUES (?, ?, ?, 'user', 1, 0, 0)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, email);
            stmt.setString(2, fullName);
            stmt.setString(3, "GOOGLE_LOGIN"); // đặt giá trị mặc định không dùng
            

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    return getUserById(userId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updatePasswordByEmail(String email, String newPassword) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Users SET password_hash = ? WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword); // nên hash nếu muốn bảo mật
            ps.setString(2, email);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            java.sql.Connection conn = util.DBConnection.getConnection();
            System.out.println("Connected to DB: " + conn.getMetaData().getURL());
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int balance = UserDAO.getCamBalanceById(3);
        System.out.println("camBalance for userId=3: " + balance);
    }

}
