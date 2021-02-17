package com.app.kt_bracket.structure;

import java.util.ArrayList;
import java.util.List;


public class Bracket
{
    List<BracketColumn> columns;
    String categoryName;


    public Bracket()
    {
        columns = new ArrayList<>();
    }

    public Bracket(List<BracketColumn> columns)
    {
        this.columns = columns;
    }

    public Bracket(List<BracketColumn> columns, String categoryName)
    {
        this.columns = columns;
        this.categoryName = categoryName;
    }

    public List<BracketColumn> getColumns()
    {
        return columns;
    }

    public void setColumns(List<BracketColumn> columns)
    {
        this.columns = columns;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }
}
