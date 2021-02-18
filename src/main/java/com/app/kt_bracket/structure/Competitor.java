package com.app.kt_bracket.structure;


public class Competitor
{
    private String fullName;
    private int startNumber;
    private Coordinates coordinates;


    public Competitor() {  }

    public Competitor(String fullName)
    {
        this.fullName = fullName;
    }

    public Competitor(String fullName, int startNumber, Coordinates coordinates)
    {
        this.fullName = fullName;
        this.startNumber = startNumber;
        this.coordinates = coordinates;
    }

    public Competitor(Competitor competitor)
    {
        this.fullName = competitor.fullName;
        this.startNumber = competitor.startNumber;
        this.coordinates = new Coordinates(competitor.coordinates.getX(), competitor.coordinates.getY());
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public int getStartNumber()
    {
        return startNumber;
    }

    public void setStartNumber(int startNumber)
    {
        this.startNumber = startNumber;
    }

    public Coordinates getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }

    public boolean isEmpty()
    {
        return fullName == "###############";
    }
}
