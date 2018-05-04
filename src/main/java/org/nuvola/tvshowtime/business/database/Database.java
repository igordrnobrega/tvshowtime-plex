package org.nuvola.tvshowtime.business.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.*;

public class Database {
    private static final Logger LOG = LoggerFactory.getLogger(Database.class);

    private static String dbURL = "";

    public static void createNewDatabase(String path, String fileName) {

        File dbpath = new File(path);
        if (!dbpath.exists()) {
            dbpath.mkdirs();
        }

        File dbfile = new File(dbpath.getAbsolutePath() + "/" + fileName + ".db");
        dbURL = "jdbc:sqlite:" + dbfile.getAbsoluteFile();

        try (Connection conn = DriverManager.getConnection(dbURL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                createTables();
                LOG.info("Database at " + dbfile.getAbsoluteFile());
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
    }

    public static void createTables() {
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(SQLQueries.SQL_create_users_table);
            stmt.execute(SQLQueries.SQL_create_viewed_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
