package com.epam.training.cafe.database;

import java.util.ResourceBundle;

public class DatabaseConfig {
    private static final String FILEPATH = "properties/dbconf";
    private static final String KEY_URL = "db.url";
    private static final String KEY_USER = "db.user";
    private static final String KEY_PASSWORD = "db.password";
    private static final String KEY_POOLSIZE = "db.pool_size";
    private static int DEFAULT_POOL_SIZE = 20;

    private String url;
    private String user;
    private String password;
    private int poolSize;

    DatabaseConfig() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(FILEPATH);
        url = resourceBundle.getString(KEY_URL);
        user = resourceBundle.getString(KEY_USER);
        password = resourceBundle.getString(KEY_PASSWORD);
        try {
            poolSize = Integer.parseInt(resourceBundle.getString(KEY_POOLSIZE));
        } catch (NumberFormatException e) {
            poolSize = DEFAULT_POOL_SIZE;
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPoolSize() {
        return poolSize;
    }
}
