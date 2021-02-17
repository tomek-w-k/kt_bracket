package com.app.kt_bracket.data;

import com.app.kt_bracket.structure.Competitor;
import java.util.List;


public class Category
{
    List<Competitor> competitorList;
    String name;


    public Category() {  }

    public Category(List<Competitor> competitorList)
    {
        this.competitorList = competitorList;
    }

    public Category(List<Competitor> competitorList, String name)
    {
        this.competitorList = competitorList;
        this.name = name;
    }

    public List<Competitor> getCompetitorList()
    {
        return competitorList;
    }

    public void setCompetitorList(List<Competitor> competitorList)
    {
        this.competitorList = competitorList;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
