import java.sql.*;

public class PreparedStatement01
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Techpro",
                "postgres", "1984Pl1988.");
        Statement st = con.createStatement();


         /*
            PreparedStatement interface, birden cok kez calistirilabilen onceden derlenmis bir SQL kodunu temsil eder.
            Parametrelendirilmis(Parameterised) SQL query(sorgu)'leri ile calisir. Bir sorguyu 0 veya daha fazla parametre ile kullanabiliriz

        */

        //1. Örnek: Prepared statement kullanarak company adı IBM olan number_of_employees değerini 9999 olarak güncelleyin.

        //1.Adım : PreparedStatement query'sini olusturalım
        String sql1 = "update companies set number_of_employees = ? where company= ? ";

        //2.Adım : PreparedStatement objesini olusturalım
        PreparedStatement pst1=con.prepareStatement(sql1);


        //3.Adım : set int(),setString .. methdlarini kullanarak soru isaretlerinin yerine deger girelim
        pst1.setInt(1,9999);
        pst1.setString(2,"IBM");

        int guncellenenSatirSayisi2 = pst1.executeUpdate();
        System.out.println("guncellenenSatirSayisi2 = " + guncellenenSatirSayisi2);

        String sql2="select*from companies";

        ResultSet resultSet1=st.executeQuery(sql2);

        while(resultSet1.next()){
            System.out.println(resultSet1.getInt(1) +
                    "-" + resultSet1.getString(2) +
                    "-" + resultSet1.getInt(3));
        }

//---------------------------------------------------------------------

/*
    Java'da methodlar return type sahibi olsa da olmasa da method olarak adlandırılır.
    SQL'de ise data return ediyorsa "function" denir. Return yapmıyorsa "procedure" olarak adlandırılır.
     */

        //CallableStatement ile function çağırmayı parametrelendireceğiz.
        //1.Adım: Function kodunu yaz:
        String sql3 ="CREATE OR REPLACE FUNCTION  toplamaF(x NUMERIC, y NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE plpgsql\n" +
                "AS\n" +
                "$$\n" +
                "BEGIN\n" +
                "\n" +
                "RETURN x+y;\n" +
                "\n" +
                "END\n" +
                "$$";
        //2. Adım: Function'ı çalıştır.
        st.execute(sql3);

        //3. Adım: Fonction'ı çağır.
        CallableStatement cst1 = con.prepareCall("{? = call toplamaF(?, ?)}");

        //4. Adım: Return için registerOurParameter() methodunu, parametreler için ise set() ... methodlarını uygula.
        cst1.registerOutParameter(1, Types.NUMERIC);
        cst1.setInt(2, 6);
        cst1.setInt(3, 4);

        //5. Adım: execute() methodu ile CallableStatement'ı çalıştır.
        cst1.execute();

        //6. Adım: Sonucu çağırmak için return data type tipine göre

        System.out.println(cst1.getBigDecimal(1));

//2. Örnek: Koninin hacmini hesaplayan bir function yazın.
//1.Adım: Function kodunu yaz:
        String sql5 = "CREATE OR REPLACE FUNCTION  konininHacmiF(r NUMERIC, h NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE plpgsql\n" +
                "AS\n" +
                "$$\n" +
                "BEGIN\n" +
                "\n" +
                "RETURN 3.14*r*r*h/3;\n" +
                "\n" +
                "END\n" +
                "$$";

//2. Adım: Function'ı çalıştır.
        st.execute(sql5);

//3. Adım: Fonction'ı çağır.
        CallableStatement cst2 = con.prepareCall("{? = call konininHacmiF(?, ?)}");

//4. Adım: Return için registerOurParameter() methodunu, parametreler için ise set() ... methodlarını uygula.
        cst2.registerOutParameter(1, Types.NUMERIC);
        cst2.setInt(2, 1);
        cst2.setInt(3, 6);

//5. Adım: execute() methodu ile CallableStatement'ı çalıştır.
        cst2.execute();

        System.out.printf("%.2f",cst2.getBigDecimal(1));


    }
}
