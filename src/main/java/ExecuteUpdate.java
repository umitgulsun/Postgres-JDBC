import java.sql.*;

public class ExecuteUpdate
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Techpro",
                "postgres", "1984Pl1988.");
        Statement st = con.createStatement();

        String sql="update companies set number_of_employees=16000 where number_of_employees\n" +
                "<  (select avg (number_of_employees) from companies)";

        int updateEdilenSatirSayisi=st.executeUpdate(sql);

        System.out.println("updateEdilenSatirSayisi = " + updateEdilenSatirSayisi);
        ResultSet res=st.executeQuery("select*from companies");

        while (res.next()){

            System.out.println(res.getInt(1)
                    +"--"+ res.getString(2)+ res.getInt(3));
        }



        res.close();
        st.close();
        con.close();
    }
}
