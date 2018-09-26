
package nl.hsleiden.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Main class.
 * 
 * @author Peter van Vliet
 */
public class Main
{
    private static final String DATABASE_HOST = "localhost",
                                DATABAE_NAME = "media",
                                DATABASE_USER = "root",
                                DATABASE_PASSWORD = "mysql";
    
    public static void main(String[] args)
    {
        Database database = new Database();
        
        try
        {
            database.connect(DATABASE_HOST, DATABAE_NAME, DATABASE_USER, DATABASE_PASSWORD);
            
            ResultSet result = database.query("SELECT * FROM series");
            
            while (result.next())
            {
                String name = result.getString("name");
                double score = result.getDouble("score");
                
                System.out.println(String.format("%s (%.1f)", name, score));
            }
            
            database.closeResult(result);
            
            database.disconnect();
        }
        catch (SQLException exception)
        {
            System.err.println(String.format("Databse failure: %s", exception.getMessage()));
        }
    }
}
