package com.capexercise.huffman.variations.modtopword;

import com.capexercise.huffman.general.database.IDataBase;

import java.io.*;
import java.sql.*;
import java.util.Map;

public class SQLImplemenation implements IDataBase {
    static Connection connection;
    static Statement stm;
    static ResultSet rs;

    @Override
    public boolean setUpConnection() {
        boolean flag = true;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:freqTable.db");
            stm = connection.createStatement();
            stm.executeUpdate("create table if not exists fileTable (md5 text,file blob)");

        } catch (SQLException e) {
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean checkIfKeyExists(String key) {
        try {
            rs = stm.executeQuery("select * from fileTable;");
            while (rs.next()) {
                String returned = rs.getString("md5");
                System.out.println(returned);
                if (returned.equals(key))
                    return true;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public boolean addMapIntoTable(String key, Map<Object, Integer> frequencyMap) {

        boolean flag = true;

        try {
            ByteArrayOutputStream obj = new ByteArrayOutputStream();
            ObjectOutputStream op = null;

            op = new ObjectOutputStream(obj);

            op.writeObject(frequencyMap);
            op.flush();
            op.close();
            byte[] byteArray = obj.toByteArray();

            PreparedStatement prepareStatement = connection.prepareStatement("insert into fileTable values(?,?)");
            prepareStatement.setString(1, key);
            prepareStatement.setBytes(2, byteArray);
            prepareStatement.executeUpdate();

        } catch (IOException | SQLException e) {
            flag = false;
            System.out.println("Exception caused:\n" + e.getMessage());
        }

        return flag;
    }

    @Override
    public Map<Object, Integer> retriveMap(String key) {
        Map<Object, Integer> frequencyMap = null;
        try {
            rs = stm.executeQuery("select * from fileTable where md5='" + key + "'");

            byte[] arr = rs.getBytes("file");

            ObjectInputStream ip = new ObjectInputStream(new ByteArrayInputStream(arr));

            frequencyMap = (Map<Object, Integer>) ip.readObject();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            frequencyMap = null;
            System.out.println("Exception caused:\n" + e.getMessage());
        }

        return frequencyMap;
    }

    @Override
    public boolean closeConnection() {
        boolean flag = true;
        try {
            connection.close();
            stm.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception:" + e.getMessage());
            flag = false;
        }
        return flag;

    }
}
