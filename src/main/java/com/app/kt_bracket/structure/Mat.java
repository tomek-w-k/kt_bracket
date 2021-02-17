package com.app.kt_bracket.structure;

import java.util.List;


public class Mat
{
    private List<Bracket> brackets;
    private String number;
    private boolean isNumbered;


    public Mat() {  }

    public Mat(List<Bracket> brackets)
    {
        this.brackets = brackets;
    }

    public List<Bracket> getBrackets()
    {
        return brackets;
    }

    public void setBrackets(List<Bracket> brackets)
    {
        this.brackets = brackets;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public boolean isNumbered()
    {
        return isNumbered;
    }

    public void setNumbered(boolean numbered)
    {
        isNumbered = numbered;
    }
}
