package com.epam.training.cafe.database;

import com.epam.training.cafe.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager
            .getLogger(ConnectionPool.class);

    private static ConnectionPool instance;
    private int size;
    private BlockingQueue<ProxyConnection> freeConnections;
    private static AtomicBoolean isExist = new AtomicBoolean(false);
    private static Lock lock = new ReentrantLock();

    private ConnectionPool() {
        initConnectionPool();
    }

    public static ConnectionPool getInstance() {
        if (!isExist.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isExist.set(true);
                    LOGGER.debug("Instance of ConnectionPool created.");
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
        } catch (InterruptedException e) {
            LOGGER.error(Thread.currentThread() + " didn't get connection", e);
            Thread.currentThread().interrupt();
            System.out.println("Thread down");
        }
        return connection;
    }

    public void returnConnection(Connection connection)
            throws ConnectionPoolException {
        if (!freeConnections.offer((ProxyConnection) connection)) {
            LOGGER.error(Thread.currentThread() + " didn't return connection");
            throw new ConnectionPoolException("Fail to return connection.");
        }
    }

    public void clearConnectionPool() {
        try {
            closeConnections(freeConnections);
        } catch (SQLException e) {
            LOGGER.error("Cannot clear Connection Pool.", e);
        }
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = (Driver) drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOGGER.error("Fail to deregister drivers.", e);
        }
    }

    private void initConnectionPool() {
        DatabaseConfig config = new DatabaseConfig();
        size = config.getPoolSize();
        freeConnections = new LinkedBlockingQueue<ProxyConnection>(size);
        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            for (int i = 0; i < size; i++) {
                freeConnections.add(new ProxyConnection(
                        DriverManager.getConnection(config.getUrl(),
                                config.getUser(), config.getPassword())));
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot initialize Connection Pool.", e);
            throw new RuntimeException("Fail to init pool.", e);
        }
    }

    private void closeConnections(BlockingQueue<ProxyConnection> connections)
            throws SQLException {
        ProxyConnection connection = null;
        while ((connection = connections.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            connection.reallyClose();
        }
    }
}
