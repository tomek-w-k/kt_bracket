package com.app.kt_bracket.structure;

import java.util.ArrayList;
import java.util.List;


public class BracketColumn
{
    private List<Fight> fights;
    private int number;


    public BracketColumn()
    {
        fights = new ArrayList<>();
    }

    public BracketColumn(List<Fight> fights)
    {
        this.fights = fights;
    }

    public BracketColumn(List<Fight> fights, int number)
    {
        this.fights = fights;
        this.number = number;
    }

    public List<Fight> getFights()
    {
        return fights;
    }

    public void setFights(List<Fight> fights)
    {
        this.fights = fights;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }
}
