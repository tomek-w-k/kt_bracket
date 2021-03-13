package com.app.kt_bracket.structure;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Competitor
{
    private StringProperty fullName;
    private IntegerProperty startNumber;
    private Coordinates coordinates;


    public Competitor() {  }

    public Competitor(StringProperty fullName)
    {
        this.fullName = fullName;
    }

    public Competitor(String fullName)
    {
        this.fullName = new SimpleStringProperty(fullName);
    }

    public Competitor(StringProperty fullName, IntegerProperty startNumber, Coordinates coordinates)
    {
        this.fullName = fullName;
        this.startNumber = startNumber;
        this.coordinates = coordinates;
    }

    public Competitor(String fullName, int startNumber, Coordinates coordinates)
    {
        this.fullName = new SimpleStringProperty(fullName);
        this.startNumber = new SimpleIntegerProperty(startNumber);
        this.coordinates = coordinates;
    }

    public Competitor(Competitor competitor)
    {
        this.fullName = competitor.fullName;
        this.startNumber = competitor.startNumber;
        this.coordinates = new Coordinates(competitor.coordinates.getX(), competitor.coordinates.getY());
    }

    public StringProperty fullNameProperty()
    {
        return fullName;
    }

    public String getFullName()
    {
        return fullName.get();
    }

    public void setFullName(StringProperty fullName)
    {
        this.fullName = fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = new SimpleStringProperty(fullName);
    }

    public IntegerProperty startNumberProperty()
    {
        return startNumber;
    }

    public int getStartNumber()
    {
        return startNumber.get();
    }

    public void setStartNumberProperty(IntegerProperty startNumber)
    {
        this.startNumber = startNumber;
    }

    public void setStartNumber(int startNumber)
    {
        this.startNumber = new SimpleIntegerProperty(startNumber);
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
        return fullName.get().isEmpty();
    }
}
