
//Mysql connection

//import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class jdbcDemo{
    private static String driver = "com.mysql.jdbc.Driver";
    private static String connection = "jdbc:mysql://localhost:3306/test";
    private static String user = "root";
    private static String password = "password";


    private static Connection con = null;
    private static Statement state = null;
    private static ResultSet result;
    private static PreparedStatement pstate;

    public static void main(String args[])/* throws Exception*/{
        mysqlConnect();
        /*
        insertData("good","nice","fine","evil");
        insertDataUsingSt("word1","meanin1","syn1","ant1");
        //deleteData("sd");
        countRow("dictionary");
        //updateData("ss", "suru");
        showData("surayea");
        //closeConnection();

         */
       /* insertStudent(1,"s1",15);*/
    }


    public static void insertStudent(int id,String name, int age) {
        try{
            //using PreparedStatement
            pstate = con.prepareStatement("insert into students(id, name,age)"+
                    "values(?,?,?)");
            pstate.setInt(1,id);
            pstate.setString(2,name);
            pstate.setInt(3,age);
            int value = pstate.executeUpdate();

            System.out.println("Query OK, 1 student row insertedted.");
        }
        catch(SQLException e){
            System.err.println("Query error." + e.getMessage());
        }
    }

    public static void mysqlConnect(){
        try{
            //Class.forName(driver);
            con = DriverManager.getConnection(connection, user, password);
            System.out.println("Successfully connected to local database.");
        }
        /*catch(ClassNotFoundException e){
            System.err.println("Couldn't load driver.");
        }*/
        catch(SQLException e){
            System.err.println("Couldn't connect to database." + e.getMessage());
        }
    }


    public static void closeConnection(){
        try{
            if(!con.isClosed()){
                con.close();
                System.out.println("Database closed successfully.");
            }
        }
        catch(NullPointerException e){
            System.err.println("Couldn't load driver.");
        }
        catch(SQLException e){
            System.err.println("Couldn't close database.");
        }
    }

    public static void insertDataUsingSt(String word, String meaning, String synonym, String antonym){
        try {
            state = con.createStatement();
            int value = state.executeUpdate("insert into dictionary(word, meaning, synonym, antonym)"+
                                  "values('"+word+"', '"+meaning+"', '"+synonym+"', '"+antonym+"')");

            System.out.println("Query OK, 1 row inserted.");
        }
        catch (SQLException e) {
            System.err.println("Query error." + e.getMessage());
        }
    }

    public static void insertData(String word, String meaning, String synonym, String antonym){
        try{
            //using PreparedStatement
            pstate = con.prepareStatement("insert into dictionary(word, meaning, synonym, antonym)"+
                    "values(?,?,?,?)");
            pstate.setString(1, word);
            pstate.setString(2, meaning);
            pstate.setString(3, synonym);
            pstate.setString(4, antonym);
            int value = pstate.executeUpdate();

            //using Statement
            //state = con.createStatement();
            //int value = state.executeUpdate("insert into dictionary(word, meaning, synonym, antonym)"+
            //                      "values('"+word+"', '"+meaning+"', '"+synonym+"', '"+antonym+"')");

            System.out.println("Query OK, 1 row insertedted.");
        }
        catch(SQLException e){
            System.err.println("Query error." + e.getMessage());
        }
    }

    public static void deleteData(String word){
        try{
            //using PreparedStatement
            pstate = con.prepareStatement("delete from dictionary where word = ?");
            pstate.setString(1,"word");
            int value = pstate.executeUpdate();

            //using Statement
            //state = con.createStatement();
            //int value = state.executeUpdate("delete from dictionary where word='"+word+"'");

            System.out.println("Query OK, 1 row deleted.");
        }
        catch(SQLException e){
            System.err.println("Query error.");
        }
    }

    public static void countRow(String table){
        try{
            result = state.executeQuery("SELECT COUNT(*) FROM "+table);
            result.next();
            int rowcount = result.getInt(1);
            System.out.println("Number of rows: "+rowcount);
        }
        catch(SQLException e){
            System.err.println("Query error.");
        }
    }

    public static void showData(String word){
        try{
            state = con.createStatement();
            result = state.executeQuery("select * from dictionary where word='"+word+"'");
            while(result.next()){
                String word1 = result.getString("word");
                String mean = result.getString("meaning");
                String syno = result.getString("synonym");
                String anto = result.getString("antonym");
                System.out.println("Word: "+word1+" Meaning: "+mean+" synonym: "+syno+" antonym: "+anto);
            }
        }
        catch(SQLException e){
            System.err.println("Query error.");
        }
        catch(NullPointerException e){
            System.err.println("Element not found.");
        }
    }

    public static void updateData(String word, String meaning){
        try{
            //using Statement
            //state = con.createStatement();
            //int value = state.executeUpdate("update dictionary set meaning='"+meaning+"' where word='"+word+"'");

            //using PreparedStatement
            pstate = con.prepareStatement("update dictionary set meaning= ? whrere word = ?");
            pstate.setString(1, meaning);
            pstate.setString(2, word);
            pstate.executeUpdate();

            System.out.println("Query OK, 1 row updated.");
        }
        catch(SQLException e){
            System.err.println("Query error."+e.getMessage());
        }
    }

}


