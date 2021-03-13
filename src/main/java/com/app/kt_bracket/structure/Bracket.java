package com.app.kt_bracket.structure;

import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;


public class Bracket
{
    private List<BracketColumn> columns;
    private String categoryName;
    private int numberOfCompetitors;
    private GridPane layoutRepresentation;


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

    public int getNumberOfCompetitors()
    {
        return numberOfCompetitors;
    }

    public void setNumberOfCompetitors(int numberOfCompetitors)
    {
        this.numberOfCompetitors = numberOfCompetitors;
    }

    public GridPane getLayoutRepresentation()
    {
        return layoutRepresentation;
    }

    public void setLayoutRepresentation(GridPane layoutRepresentation)
    {
        this.layoutRepresentation = layoutRepresentation;
    }
}
