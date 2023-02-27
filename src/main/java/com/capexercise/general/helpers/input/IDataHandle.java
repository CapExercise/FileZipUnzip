package com.capexercise.general.helpers.input;

import java.util.Map;

public interface IDataHandle {

    String readContent();

    String[] readContentAsArray();

    void formMap();

    void setPercentage(int per);

    int getPercentage();

    Map<Object, Integer> returnMap();


}
