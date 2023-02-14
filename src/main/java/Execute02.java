import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Execute02
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Techpro",
                "postgres", "1984Pl1988.");
        Statement durum = conn.createStatement();
//execute () metodu DDL(create drop alter) ve DQL(select) için kullanılabilir.
        //eger execute metodu ddl için kullanılırsa false return eder.
        //eger execute metodu dql için kullanılırsa ResultSet alındıgına true aksi halde false verir.
       boolean sql= durum.execute("CREATE TABLE workers(worker_id VARCHAR(20), worker_name VARCHAR(20), worker_salary INT)");

        System.out.println("sql = " + sql);//false return eder. cunku data cagırmıyoruz.


       String sql2= "alter table workers add column worker_address varchar(80)";

       durum.execute(sql2);

       String sql3="DROP TABLE workers";
       durum.execute(sql3);
       conn.close();
       durum.close();


    }
}
