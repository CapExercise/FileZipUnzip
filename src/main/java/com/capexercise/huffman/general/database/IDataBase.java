package com.capexercise.huffman.general.database;

import java.sql.SQLException;
import java.util.Map;

public interface IDataBase {

    boolean setUpConnection();

    boolean checkIfKeyExists(String key);

    boolean addMapIntoTable(String key, Map<Object, Integer> frequencyMap);

    Map<Object, Integer> retriveMap(String key);

    boolean closeConnection() throws SQLException;


}
