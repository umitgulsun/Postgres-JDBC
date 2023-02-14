import java.sql.*;
import java.util.*;

public class ExecuteQuerry
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Techpro", "postgres", "1984Pl1988.");
        Statement st = con.createStatement();

        //1. Örnek:  region id'si 1 olan "country name" değerlerini çağırın.

        String sql = "select country_name from countries where region_id=1";//dql kullandık

        boolean r1 = st.execute(sql);
        System.out.println("r1 = " + r1);


        //recordları görmek için executequery metodunu kullanmalıyız.

        ResultSet rst = st.executeQuery(sql);

        int count=0;
        while (rst.next())
        {
            count++;
            String str = rst.getString(1);
            System.out.println(str);
        }
        System.out.println(count+" tane sayı var");

        String sql2 = "select country_id,country_name from countries where region_id>2";

        ResultSet rst2 = st.executeQuery(sql2);
        System.out.println("---------------------------------------------------");

        while (rst2.next())
        {
            String str = rst2.getString("country_name");
            String str2 = rst2.getString("country_id");
            System.out.println(str + "--" + str2);
        }

        String sql3 = "select * from companies where number_of_employees=(select min(number_of_employees) from companies)";
        ResultSet rst3 = st.executeQuery(sql3);
        while (rst3.next())
            System.out.println(rst3.getInt(1) + "--" + rst3.getString(2) + "--" + rst3.getInt(3));

        rst2.close();
        rst3.close();
        con.close();
        st.close();

        Scanner input = new Scanner(System.in);
        System.out.print("Ömer Hayyam üçgeni için satır giriniz : ");
        int hayyamUcgenSatiri = input.nextInt();
        int[][] hayyamUcgeni = new int[hayyamUcgenSatiri][];
        for (int i = 0; i < hayyamUcgeni.length; i++)
        {
            hayyamUcgeni[i] = new int[i + 1];
            if (i == 0)
            {
                hayyamUcgeni[i][i] = i + 1;
                continue;
            }
            for (int j = 0; j < hayyamUcgeni[i].length; j++)
            {
                if (j == 0 || j == hayyamUcgeni[i].length - 1)
                {
                    hayyamUcgeni[i][j] = hayyamUcgeni[0][0];
                    continue;
                }
                hayyamUcgeni[i][j] = hayyamUcgeni[i - 1][j - 1] + hayyamUcgeni[i - 1][j];
            }
        }

//NumberFormat format = new DecimalFormat("000");


        // System.out.print(" ");
        for (int i = 0; i < hayyamUcgeni.length; i++)
        {

            for (int j = 0; j < hayyamUcgenSatiri - i; j++)
            {
                System.out.printf("%2s", " ");
            }
            for (int j = 0; j < hayyamUcgeni[i].length; j++)
            {
                System.out.printf("%4s", hayyamUcgeni[i][j]);
            }
            System.out.println();
        }

       String[] isimler={"ali","veli","cumali","kadir"};
       String[] sayilar={"1","2","3","4","5","6","7","8"};
       Map<String,String> son=new HashMap<>();
        System.out.println(son);
        List<String> lstsayison=new ArrayList<>();
       Random rnd=new Random();

        for (int i = 0; i < isimler.length; i++)
        {
                int sayi= rnd.nextInt(1,sayilar.length+1);
                String say= String.valueOf(sayi);
                int sayi2= rnd.nextInt(1,sayilar.length+1);
                String say2= String.valueOf(sayi2);
                String sayiSon=sayi+","+sayi2;
                String[] dizisayison= sayiSon.split(" ,");

                if (!lstsayison.contains(say) & !lstsayison.contains(say2)&!say.equals(say2))
                {
                    lstsayison.add(say);
                    lstsayison.add(say2);
                    son.put(isimler[i],sayiSon );
                }else {
                    i--;
                }

        }
        System.out.println(son);
        System.out.println(lstsayison);


    }

}
