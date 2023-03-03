package com.capexercise.huffman.variations.modtopword;

import com.capexercise.huffman.general.IDataBase;

import java.io.*;
import java.sql.*;
import java.util.Map;

public class SQLImplemenation implements IDataBase
{
    static Connection connection;
    static Statement stm;
    static ResultSet rs;
    @Override
    public boolean setUpConnection() {
        boolean flag=true;
        try {
            connection= DriverManager.getConnection("jdbc:sqlite:freqTable.db");
            stm = connection.createStatement();
            stm.executeUpdate("create table if not exists fileTable (md5 text,file blob)");

        } catch (SQLException e) {
            flag=false;
//            throw new RuntimeException(e);
        }

        return flag;
    }

    @Override
    public boolean checkIfKeyExists(String key) throws SQLException {
        try {
            rs=stm.executeQuery("select * from fileTable ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while(rs.next())
        {
            String returned=rs.getString("md5");
            System.out.println(returned);
            if(returned.equals(key))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addMapIntoTable(String key, Map<Object, Integer> frequencyMap) throws IOException, SQLException {
        ByteArrayOutputStream obj=new ByteArrayOutputStream();
        byte[] bytarr= null;
        ObjectOutputStream op=new ObjectOutputStream(obj);
        op.writeObject(frequencyMap);
        op.flush();
        op.close();
        bytarr=obj.toByteArray();
        PreparedStatement prepareStatement = connection.prepareStatement("insert into fileTable values(?,?)");
        prepareStatement.setString(1, key);
        prepareStatement.setBytes(2,bytarr);
        prepareStatement.executeUpdate();
        return true;
    }

    @Override
    public Map<Object, Integer> retriveMap(String key) throws SQLException, IOException, ClassNotFoundException {
        rs=stm.executeQuery("select * from fileTable where md5='"+key+"'");

            byte[] arr=rs.getBytes("file");

            ObjectInputStream ip=null;
            try {
                ip=new ObjectInputStream(new ByteArrayInputStream(arr));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

                Map<Object,Integer> frequencyMap=(Map<Object, Integer>) ip.readObject();

            return frequencyMap;
    }

    @Override
    public boolean closeConnection() throws SQLException {
        connection.close();
        stm.close();
        rs.close();

        return true;

    }
}
