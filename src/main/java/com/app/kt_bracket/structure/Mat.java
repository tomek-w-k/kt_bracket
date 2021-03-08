package com.app.kt_bracket.structure;

import java.util.List;
import java.util.Optional;


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

    public Optional<Bracket> findBracketByCategoryName(String categoryName)
    {
        return brackets.stream()
            .filter(bracket -> bracket.getCategoryName().equals(categoryName))
            .findAny();
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
