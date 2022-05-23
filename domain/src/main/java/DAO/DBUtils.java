package DAO;

import models.calculator.RangeDB;
import models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component("dbUtils")
public class DBUtils {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBUtils(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void addUsers(User user) throws SQLException {
        jdbcTemplate.update("Insert into users (email, firstname, lastname, password) " +
                        "values (?,?,?,?)",
                user.getEmail(), user.getFirstname(), user.getLastname(), user.getPassword());
    }

    public List<User> getAllUsers(String order) throws SQLException {
        return jdbcTemplate.query("Select * from users order by " + order, new UserMapper());
    }

    public List<User> getUsers(String role) throws SQLException {
        return jdbcTemplate.query("Select * from users where role=" +"'" +  role + "'", new UserMapper());
    }

    public User getUserByEmail(String email) throws SQLException {
        return jdbcTemplate.query("Select * from users where email=" +"'" +  email + "'", new UserMapper())
                .stream().findAny().orElse(null);
    }

    public User getUserById(int id) throws SQLException {
        return jdbcTemplate.query("Select * from users where id=" + id, new UserMapper())
                .stream().findAny().orElse(null);
    }

    public void deleteUserById(int id) throws SQLException {
        jdbcTemplate.update("Delete from users where id=" + id);
    }

    public void updateUser(int id, User user) {
        String role = String.valueOf(user.getRole());
        String status = String.valueOf(user.getStatus());

        jdbcTemplate.update("UPDATE users SET email=?, firstname=?, lastname=?, password=?, role=?, status=? WHERE id=?",
                user.getEmail(), user.getFirstname(), user.getLastname(), user.getPassword(), role, status, id);
    }

    public void updatePassword(String new_password, int id) {
        jdbcTemplate.update("UPDATE users SET password=? WHERE id=?", new_password, id);
    }

    public List<RangeDB> getRanges(long playerId) throws SQLException {
        return jdbcTemplate.query("Select * from ranges where player_id=" + playerId, new RangeMapper());
    }

    public RangeDB getRangeById(int id) throws SQLException  {
        return jdbcTemplate.query("Select * from ranges where id=" + id, new RangeMapper())
                .stream().findAny().orElse(null);
    }

    public void addRange(RangeDB range) throws SQLException {
        jdbcTemplate.update("Insert into ranges (player_id, name, range) values (?,?,?)",
                range.getPlayerId(), range.getName(), range.getRange());
    }

    public void deleteRangeById(int id) throws SQLException {
        jdbcTemplate.update("Delete from ranges where id=" + id);
    }

}
