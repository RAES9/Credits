package com.raes.ezz.helpers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseConnection {
    private Connection dataBase;
    public DatabaseConnection(String host, String port, String dataBase, String user, String password){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+ host +":" + port + "/" + dataBase+ "?characterEncoding=utf8&user=" + user + "&password=" + password);
            this.dataBase = con;
        }catch(Exception e){ System.out.println(e);}
    }

    public ResultSet selectWithTableName(String table){
        try{
            PreparedStatement pst = dataBase.prepareStatement("SELECT * FROM " + table);
            ResultSet rs = pst.executeQuery();
            return rs;
        }catch(Exception e){ System.out.println(e); return null;}
    }

    public ResultSet searchOnTableWithId(String table, Integer id){
        try{
            PreparedStatement pst = dataBase.prepareStatement("SELECT * FROM " + table + " WHERE Id=?");
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            return rs;
        }catch(Exception e){ System.out.println(e); return null;}
    }

    public ResultSet searchCustomOnTable(String table, String value, String column){
        try{
            PreparedStatement pst = dataBase.prepareStatement("SELECT * FROM " + table + " WHERE " + column + "=?");
            pst.setString(1, value);
            ResultSet rs = pst.executeQuery();
            return rs;
        }catch(Exception e){ System.out.println(e); return null;}
    }

    public void deleteOnTableWithId(String table, Integer id){
        try{
            PreparedStatement pst = dataBase.prepareStatement("DELETE FROM " + table + " WHERE Id=?");
            pst.setInt(1,id);
            pst.executeUpdate();
        }catch(Exception e){ System.out.println(e);}
    }

    public void insertWithTableName(HashMap<String,String> parameters, String table){
        String sizeValues = "";
        String columns = "";
        ArrayList<String> p = new ArrayList<>();
        for ( String key : parameters.keySet() ) {
            columns = columns + key + ",";
            sizeValues = sizeValues + "?,";
            p.add(parameters.get(key));
        }
        sizeValues = closeString(sizeValues);
        columns = closeString(columns);
        try{
            PreparedStatement pst = dataBase.prepareStatement("INSERT INTO " + table  + "(" + columns +") VALUES(" + sizeValues + ")");
            for (int i = 0; i < parameters.keySet().size(); i++) {
                pst.setString(i + 1,p.get(i));
            }
            pst.executeUpdate();
        }catch(Exception e){System.out.println(e);}
    }

    private static String closeString(String str) {
        String result = null;
        if ((str != null) && (str.length() > 0)) {
            result = str.substring(0, str.length() - 1);
        }
        return result;
    }
}
