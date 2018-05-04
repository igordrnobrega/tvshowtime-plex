package org.nuvola.tvshowtime.business.database;

public class SQLQueries {
    public static String SQL_create_users_table = "CREATE TABLE `users` (\n" +
            "\t`id`\tinteger,\n" +
            "\t`name`\ttext NOT NULL,\n" +
            "\tPRIMARY KEY(`id`)\n" +
            ");";
    public static String SQL_create_viewed_table = "CREATE TABLE `viewed` (\n" +
            "\t`view_id`\tINTEGER,\n" +
            "\t`grand_parent_title`\ttext NOT NULL,\n" +
            "\t`parent_index`\tinteger NOT NULL,\n" +
            "\t`view_index`\tinteger NOT NULL,\n" +
            "\t`viewed_at`\tinteger NOT NULL,\n" +
            "\t`type`\ttext NOT NULL,\n" +
            "\t`user_id`\tinteger NOT NULL,\n" +
            "\tFOREIGN KEY(`view_id`) REFERENCES `users`(`id`) ON DELETE SET NULL,\n" +
            "\tPRIMARY KEY(`view_id`)\n" +
            ");";
}
