package org.nuvola.tvshowtime.business.database;

public class SQLQueries {
    public static String SQL_create_users_table = "CREATE TABLE `users` (\n" +
            "\t`id`\tINTEGER,\n" +
            "\t`name`\tTEXT NOT NULL UNIQUE,\n" +
            "\t`tvst_token`\tINTEGER,\n" +
            "\tPRIMARY KEY(`id`)\n" +
            ");";
    public static String SQL_create_viewed_table = "CREATE TABLE `viewed` (\n" +
            "\t`view_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`grand_parent_title`\tTEXT NOT NULL,\n" +
            "\t`parent_index`\tINTEGER NOT NULL,\n" +
            "\t`view_index`\tINTEGER NOT NULL,\n" +
            "\t`user_id`\tINTEGER NOT NULL,\n" +
            "\tFOREIGN KEY(`view_id`) REFERENCES `users`(`id`) ON DELETE SET NULL\n" +
            ");";
    public static String SQL_insert_user = "INSERT OR IGNORE INTO users (id, name) VALUES(?,?)";
    public static String SQL_insert_view = "INSERT INTO viewed (grand_parent_title, parent_index, view_index, viewed_at, type, user_id) VALUES(?,?,?,?,?,?)";
    public static String SQL_select_view = "SELECT COUNT(view_id) from viewed WHERE\n" +
            "grand_parent_title = ? AND\n" +
            "parent_index = ? AND\n" +
            "view_index = ? AND\n" +
            "user_id = ?\n" +
            ";";
    public static String SQL_delete_view = "DELETE FROM viewed WHERE\n" +
            "grand_parent_title = ? AND\n" +
            "parent_index = ? AND\n" +
            "view_index = ? AND\n" +
            "user_id = ?\n" +
            ");";
}
