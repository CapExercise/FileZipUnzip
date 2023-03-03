package com.capexercise.huffman.general;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public interface IDataBase {

    boolean setUpConnection();

    boolean checkIfKeyExists(String key) throws SQLException;

    boolean addMapIntoTable(String key, Map<Object,Integer> frequencyMap) throws IOException, SQLException;

    Map<Object,Integer> retriveMap(String key) throws SQLException, IOException, ClassNotFoundException;

    boolean closeConnection() throws SQLException;


}
