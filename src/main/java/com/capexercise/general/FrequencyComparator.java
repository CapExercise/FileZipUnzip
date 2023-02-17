package com.capexercise.general;

import com.capexercise.general.helpers.nodes.TreeNode;

import java.util.Comparator;

public class FrequencyComparator implements Comparator<TreeNode>
{
    @Override
    public int compare(TreeNode lhs, TreeNode rhs)
    {

        if(lhs.getFrequency()==rhs.getFrequency())
        {
           String a=lhs.getVar()+"";
           String b=rhs.getVar()+"";
          return a.compareTo(b);
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
