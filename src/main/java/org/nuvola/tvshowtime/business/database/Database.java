package org.nuvola.tvshowtime.business.database;

import org.nuvola.tvshowtime.business.plex.User;
import org.nuvola.tvshowtime.business.plex.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.*;

public class Database {
    private static final Logger LOG = LoggerFactory.getLogger(Database.class);

    private static String dbURL = "";

    private static Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createNewDatabase(String path, String fileName) {

        File dbpath = new File(path);
        if (!dbpath.exists()) {
            dbpath.mkdirs();
        }

        File dbfile = new File(dbpath.getAbsolutePath() + "/" + fileName + ".db");
        dbURL = "jdbc:sqlite:" + dbfile.getAbsoluteFile();

        try (Connection conn = connect()) {
            if (conn != null) {
                createTables();
                LOG.info("Database at " + dbfile.getAbsoluteFile());
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
    }

    public static void createTables() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(SQLQueries.SQL_create_users_table);
            stmt.execute(SQLQueries.SQL_create_viewed_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void InsertUser(User user) {
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(SQLQueries.SQL_insert_user)) {
            // create a new table
            pstmt.setLong(1, user.getId());
            pstmt.setString(2, user.getName());
            int success = pstmt.executeUpdate();
            if(success == 1)
                LOG.info("New user added: " + "{ID: " + user.getId() + ", Name: " + user.getName() + "}");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
    }

    public static void InsertVideo(Video video, User user) {
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(SQLQueries.SQL_insert_view)) {
            // create a new table
            pstmt.setString(1, video.getGrandparentTitle());
            pstmt.setInt(2, video.getParentIndex());
            pstmt.setInt(3, video.getIndex());
            pstmt.setLong(4, user.getId());
            int success = pstmt.executeUpdate();
            /*if(success == 1)
                LOG.info("New user added: " + "{ID: " + user.getId() + ", Name: " + user.getName() + "}");*/
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
    }

    public static boolean CheckVideo(Video video, User user) {
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(SQLQueries.SQL_select_view)) {
            // create a new table
            pstmt.setString(1, video.getGrandparentTitle());
            pstmt.setInt(2, video.getParentIndex());
            pstmt.setInt(3, video.getIndex());
            pstmt.setLong(4, user.getId());
            ResultSet result = pstmt.executeQuery();
            return result.getInt(1) == 1;
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return false;
    }
}
