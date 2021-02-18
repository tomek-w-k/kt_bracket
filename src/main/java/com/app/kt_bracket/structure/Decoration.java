package com.app.kt_bracket.structure;

import java.util.List;


public class Decoration
{
    private Coordinates shiroHorizontalLine;
    private Coordinates upRightCorner;
    private List<Coordinates> winnerTopVerticalLines;
    private Coordinates tJunction;
    private List<Coordinates> winnerBottomVerticalLines;
    private Coordinates downRightCorner;
    private Coordinates akaHorizontalLine;


    public Decoration() {  }

    public Decoration(Coordinates shiroHorizontalLine,
                      Coordinates upRightCorner,
                      List<Coordinates> winnerTopVerticalLines,
                      Coordinates tJunction,
                      List<Coordinates> winnerBottomVerticalLines,
                      Coordinates downRightCorner,
                      Coordinates akaHorizontalLine)
    {
        this.shiroHorizontalLine = shiroHorizontalLine;
        this.upRightCorner = upRightCorner;
        this.winnerTopVerticalLines = winnerTopVerticalLines;
        this.tJunction = tJunction;
        this.winnerBottomVerticalLines = winnerBottomVerticalLines;
        this.downRightCorner = downRightCorner;
        this.akaHorizontalLine = akaHorizontalLine;
    }

    public Coordinates getShiroHorizontalLine()
    {
        return shiroHorizontalLine;
    }

    public void setShiroHorizontalLine(Coordinates shiroHorizontalLine)
    {
        this.shiroHorizontalLine = shiroHorizontalLine;
    }

    public Coordinates getUpRightCorner()
    {
        return upRightCorner;
    }

    public void setUpRightCorner(Coordinates upRightCorner)
    {
        this.upRightCorner = upRightCorner;
    }

    public List<Coordinates> getWinnerTopVerticalLines()
    {
        return winnerTopVerticalLines;
    }

    public void setWinnerTopVerticalLines(List<Coordinates> winnerTopVerticalLines)
    {
        this.winnerTopVerticalLines = winnerTopVerticalLines;
    }

    public Coordinates gettJunction()
    {
        return tJunction;
    }

    public void settJunction(Coordinates tJunction)
    {
        this.tJunction = tJunction;
    }

    public List<Coordinates> getWinnerBottomVerticalLines()
    {
        return winnerBottomVerticalLines;
    }

    public void setWinnerBottomVerticalLines(List<Coordinates> winnerBottomVerticalLines)
    {
        this.winnerBottomVerticalLines = winnerBottomVerticalLines;
    }

    public Coordinates getDownRightCorner()
    {
        return downRightCorner;
    }

    public void setDownRightCorner(Coordinates downRightCorner)
    {
        this.downRightCorner = downRightCorner;
    }

    public Coordinates getAkaHorizontalLine()
    {
        return akaHorizontalLine;
    }

    public void setAkaHorizontalLine(Coordinates akaHorizontalLine)
    {
        this.akaHorizontalLine = akaHorizontalLine;
    }
}
