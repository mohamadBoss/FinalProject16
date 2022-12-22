import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
public class Connection_Pool {
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/company";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "206954950";
    private Set<Connection> connections =  new HashSet<Connection>();;
    public Connection instance = null;
    private static Connection_Pool  dbConnection;
    private Connection_Pool() {
        int i =10;
        try {
            while (i>0)
            {
                instance = createConnection();
                connections.add(instance);
                i--;
            }


        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL , USERNAME , PASSWORD);
    }

    public static  Connection_Pool createInstance()
    {
        if( dbConnection == null)
        {
            dbConnection = new Connection_Pool();
        }
        return dbConnection;
    }



    public Connection getConnections() {
        if (connections.isEmpty() == false)
        {
            //return the last elements from connections
            Connection c = connections.stream().reduce((one, two) -> two).get();
            connections.remove(c);
            return c;
        }
        return null;
    }

    public Connection_Pool getInstance() {

        return dbConnection;
    }

    public void restoreConnection(Connection connection){
        connections.add(connection);
        notify();
    }
    public void closeAllConnections() throws SQLException {
        Iterator<Connection> it = connections.iterator();
        while(it.next() != null)
        {
            it.next().close();
        }
   }

}
