package nl.hsleiden.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database class.
 * 
 * @author Peter van Vliet
 */
public class Database
{
    private Connection connection;
    
    public boolean hasConnection() throws SQLException
    {
        return connection != null && !connection.isClosed();
    }
    
    public void connect(String host, String name, String user, String password) throws SQLException
    {
        String connectionString = String.format(
                "jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=UTC",
                host, name, user, password);
        
        connect(connectionString);
    }
    
    public void connect(String connectionString) throws SQLException
    {
        if (hasConnection())
        {
            System.err.println("Connection already opened");

            return;
        }
        
        connection = DriverManager.getConnection(connectionString);
    }
    
    public void disconnect() throws SQLException
    {
        if (!hasConnection())
        {
            System.err.println("Connection already closed");
            
            return;
        }
        
        connection.close();
    }
    
    public ResultSet query(String queryString) throws SQLException
    {
        Statement statement = connection.createStatement();

        return statement.executeQuery(queryString);
    }
    
    public void closeResult(ResultSet result) throws SQLException
    {
        Statement statement = result.getStatement();
        
        result.close();
        statement.close();
    }
    
    public void useTransactions() throws SQLException
    {
        connection.setAutoCommit(false);
    }
    
    public void commit() throws SQLException
    {
        connection.commit();
    }
    
    public void rollback() throws SQLException
    {
        connection.rollback();
    }
}
