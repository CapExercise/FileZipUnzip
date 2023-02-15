package com.capexercise.general;

import java.util.Comparator;

public class FrequencyComparator implements Comparator<Node>
{
    @Override
    public int compare(Node lhs, Node rhs)
    {

        if(lhs.getFrequency()==rhs.getFrequency())
        {
            if((int)lhs.getVar()>(int)rhs.getVar())
            {
                return 1;
            }
            else
            {
                return -1;
            }

        }
        else if(lhs.getFrequency()>rhs.getFrequency())
        {
            return 1;
        }
        else
        {
            return -1;
        }


    }




}
