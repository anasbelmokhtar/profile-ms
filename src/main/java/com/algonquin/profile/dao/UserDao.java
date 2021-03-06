package com.algonquin.profile.dao;

import com.algonquin.profile.model.Credentials;
import com.algonquin.profile.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;


@Repository("mysql")
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public int validateUser(String token) {
        String sql = "UPDATE sql5485812.Users SET UserStatus = 'active' WHERE TOKEN = ?";
        int rowsAffected = jdbcTemplate.update(sql,new Object[]{token});
        return rowsAffected;
    }

    public int changePassword(Credentials cs) {
        String sql = "UPDATE sql5485812.Users SET UserPassword = ? WHERE Username = ?";
        int rowsAffected = jdbcTemplate.update(sql,new Object[]{cs.getPassword(), cs.getUsername()});
        return rowsAffected;
    }

    private static class UserRowMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setEmail(rs.getString("Email"));
            user.setUsername(rs.getString("Username"));
            user.setLastName(rs.getString("LastName"));
            user.setFirstName(rs.getString("FirstName"));
            user.setPhone(rs.getString("Phone"));
            return user;
        }
    }

    @Autowired
    public UserDao(JdbcTemplate template){
        this.jdbcTemplate = template;
    }
    public List<User> getAllUsers() {
        String sql = "SELECT Username, Email, FirstName, LastName, Phone FROM sql5485812.Users";
        List<User> users = jdbcTemplate.query(sql,new UserRowMapper());
        return users;

    }
    public int storeCredentials(User u) throws SQLException, SQLException {
        System.out.println(u.getUsername());
            String insert = "INSERT INTO sql5485812.Users (Username, UserPassword, Email) VALUES (?,?,?)";
            int rowsAffected = jdbcTemplate.update(insert, new Object[]{u.getUsername(),u.getPassword(),u.getEmail()});
            return rowsAffected;
    }
    public int updateUser(User u) throws SQLException, SQLException {
        System.out.println(u.toString());
        String insert = "INSERT INTO sql5485812.Users (Username, UserPassword, LastName, FirstName, Email, Phone, Token, UserStatus) VALUES (?,?,?,?,?,?,?,?)";
        int rowsAffected = jdbcTemplate.update(insert, new Object[]{u.getUsername(),u.getPassword(),u.getLastName(),u.getFirstName(),u.getEmail(),u.getPhone(),u.getToken().toString(),"pending"});
        return rowsAffected;
    }
    public User login(Credentials cs) throws SQLException {;

        String sql = "SELECT Username, Email, FirstName, LastName, Phone FROM sql5485812.Users WHERE Username = ? AND UserPassword = ? AND UserStatus = 'active'";
        User user = jdbcTemplate.queryForObject(sql,new UserRowMapper(), cs.getUsername(), cs.getPassword());
        return user;
    }
}
