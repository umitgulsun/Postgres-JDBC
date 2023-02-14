import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUtils {

    private static Connection connection;
    private static Statement statement;


    //1. Adım: Driver'a kaydol
    //2. Adım: Datbase'e bağlan
    public static Connection connectToDataBase(String hostName, String dbName,String username, String password)  {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://"+hostName+":5432/"+dbName,username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(connection!=null){
            System.out.println("Connection Success");
        }else {
            System.out.println("Connection Fail");
        }
        return connection;
    }

    //3. Adım: Statement oluştur.
    public static Statement createStatement(){

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
    }

    //4. Adım: Query çalıştır.
    public static boolean execute(String sql){
        boolean isExecute;
        try {
            isExecute = statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isExecute;
    }

    //ExecuteQuery ve ExecuteUpdate methodları ödev...

    //5. Adım: Bağlantı ve Statement'ı kapat.
    public static void closeConnectionAndStatement(){

        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if(connection.isClosed()&&statement.isClosed()){
                System.out.println("Connection and statement closed!");

            }else {
                System.out.println("Connection and statement NOT closed!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Table oluşturan method
    public static void createTable(String tableName, String... columnName_dataType ){
        StringBuilder columnName_dataValue = new StringBuilder("");

        for(String w : columnName_dataType){

            columnName_dataValue.append(w).append(",");
        }

        columnName_dataValue.deleteCharAt(columnName_dataValue.length()-1);

        try {
            statement.execute( "CREATE TABLE "+tableName+"("+columnName_dataValue+")");
            System.out.println("Table "+tableName+" successfully created!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet executeQuerryy(String sql){
        ResultSet rst;
        try
        {
            rst=statement.executeQuery(sql);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return rst;
    }

    public static int executeUpdatee(String sql){
        int updateSayisi;
        try
        {
            updateSayisi=statement.executeUpdate(sql);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return updateSayisi;
    }

    public static void tabloOlustur(String tabloName,String... dizi){

        StringBuilder strKonteynir= new StringBuilder("");

        for (String w:dizi)
        {
           strKonteynir.append(w).append(",");
        }
        strKonteynir.deleteCharAt(strKonteynir.length()-1);

        try
        {
            statement.execute("CREATE TABLE "+tabloName
                    +"("+strKonteynir+")");
            System.out.println(tabloName+" olustu");
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }



}
