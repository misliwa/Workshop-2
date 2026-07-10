package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.*;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";

    private static final String UPDATE_USER_QUERY = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";

    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";

    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(SELECT_USER_BY_ID_QUERY)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int resultId = resultSet.getInt("id");
                String userName = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                return new User(resultId, userName, email, password);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(User user) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement findStatement =
                     conn.prepareStatement(SELECT_USER_BY_ID_QUERY);
            PreparedStatement updateStatement =
                     conn.prepareStatement(UPDATE_USER_QUERY)) {

            findStatement.setInt(1, user.getId());
            ResultSet resultSet = findStatement.executeQuery();

            if(!resultSet.next()){
                System.out.println("Błąd! Nie udało się pobrać danch.");
                return;
            }

            String dbPassword = resultSet.getString("password");

            String passwordToSave = (user.getPassword().equals(dbPassword))
                    ? dbPassword
                    : hashPassword(user.getPassword());


            updateStatement.setString(1, user.getUserName());
            updateStatement.setString(2, user.getEmail());
            updateStatement.setString(3, passwordToSave);
            updateStatement.setInt(4, user.getId());
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(DELETE_USER_QUERY)) {

            statement.setInt(1, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
