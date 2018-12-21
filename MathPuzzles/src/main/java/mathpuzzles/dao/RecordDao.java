package mathpuzzles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mathpuzzles.domain.Record;
import mathpuzzles.domain.User;

/**
 * Implements interface Dao for database queries for the class record
 */
public class RecordDao implements Dao<Record, Integer> {

    Database database;
    Dao<User, String> userDao;

    public RecordDao(Database database, Dao<User, String> userDao) {
        this.database = database;
        this.userDao = userDao;
    }

    @Override
    public Record findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Record WHERE id = ?");
            stmt.setObject(1, key);

            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }

            User user = userDao.findOne(rs.getString("username"));

            Record record = new Record(rs.getString("time"), rs.getInt("solved"), user);
            closer(stmt, rs, conn);

            return record;

        }
    }

    /**
     * Returns true if there is a record in the database matching the given parameters
     *
     * @param user The user of the record to be returned
     * @param time The time of the record to be returned
     * @param solved The Amount of solved problems for the record to be returned
     * @return True if record is in the database otherwise false
     * @throws SQLException if something fails at database level
     */
    public boolean findOneForUserWithTimeAndSolved(User user, String time, Integer solved) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Record WHERE username = ? AND time = ? AND solved = ?");
            stmt.setObject(1, user.getUsername());
            stmt.setObject(2, time);
            stmt.setObject(3, solved);

            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                return false;
            }

            closer(stmt, rs, conn);

            return true;
        }
    }

    @Override
    public List<Record> findAll() throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Record");
            List<Record> records = new ArrayList();

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = userDao.findOne(rs.getString("username"));
                Record record = new Record(rs.getString("time"), rs.getInt("solved"), user);
                records.add(record);
            }

            closer(stmt, rs, conn);

            return records;
        }
    }

    /**
     * Returns a list of record objects
     * @param time The time for the record objects
     * @return a list of record objects matching the time
     * @throws SQLException if something fails at database level
     */
    public List<Record> findAllForTime(String time) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Record WHERE time = ?");
            stmt.setObject(1, time);
            List<Record> records = new ArrayList();

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = userDao.findOne(rs.getString("username"));
                Record record = new Record(rs.getString("time"), rs.getInt("solved"), user);
                records.add(record);
            }

            closer(stmt, rs, conn);

            return records;
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Record save(Record object) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Record " + "(time, solved, username) " + "VALUES (?, ?, ?)");

            stmt.setString(1, object.getTime());
            stmt.setInt(2, object.getSolved());
            stmt.setString(3, object.getUser().getUsername());
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement("SELECT * FROM Record WHERE username = ? AND time = ? AND solved = ?");
            stmt.setString(1, object.getUser().getUsername());
            stmt.setString(2, object.getTime());
            stmt.setInt(3, object.getSolved());

            ResultSet rs = stmt.executeQuery();
            rs.next();

            User user = userDao.findOne(rs.getString("username"));
            Record record = new Record(rs.getString("time"), rs.getInt("solved"), user);

            closer(stmt, rs, conn);

            return record;
        }
    }

    /**
     * Closes all closable database things
     * @param stmt Statement
     * @param rs ResultSet
     * @param conn Connection
     * @throws SQLException if something fails at database level
     */
    public void closer(Statement stmt, ResultSet rs, Connection conn) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
        if (rs != null) {
            rs.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

}
