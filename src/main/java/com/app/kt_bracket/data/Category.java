package com.app.kt_bracket.data;

import com.app.kt_bracket.structure.Competitor;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.List;


public class Category
{
    List<Competitor> competitorList;
    StringProperty name;


    public Category() {  }

    public Category(List<Competitor> competitorList)
    {
        this.competitorList = competitorList;
    }

    public Category(List<Competitor> competitorList, StringProperty name)
    {
        this.competitorList = competitorList;
        this.name = name;
    }

    public Category(List<Competitor> competitorList, String name)
    {
        this.competitorList = competitorList;
        this.name = new SimpleStringProperty(name);
    }

    public List<Competitor> getCompetitorList()
    {
        return competitorList;
    }

    public void setCompetitorList(List<Competitor> competitorList)
    {
        this.competitorList = competitorList;
    }

    public StringProperty nameProperty()
    {
        return name;
    }

    public String getName()
    {
        return name.get();
    }

    public void setNameProperty(StringProperty name)
    {
        this.name = name;
    }

    public void setName(String name)
    {
        this.name = new SimpleStringProperty(name);
    }
}
