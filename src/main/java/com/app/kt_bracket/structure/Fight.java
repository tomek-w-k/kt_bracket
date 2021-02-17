package com.app.kt_bracket.structure;


public class Fight
{
    private Competitor shiro;
    private Competitor aka;
    private Competitor winner;

    private int number;
    private Coordinates numberCoordinates;


    public Fight() {  }

    public Fight(Competitor shiro, Competitor aka)
    {
        this.shiro = shiro;
        this.aka = aka;
    }

    public Fight(Competitor shiro, Competitor aka, Competitor winner)
    {
        this.shiro = shiro;
        this.aka = aka;
        this.winner = winner;
    }

    public Fight(Competitor shiro, Competitor aka, Competitor winner, int number)
    {
        this.shiro = shiro;
        this.aka = aka;
        this.winner = winner;
        this.number = number;
    }

    public Competitor getShiro()
    {
        return shiro;
    }

    public void setShiro(Competitor shiro)
    {
        this.shiro = shiro;
    }

    public Competitor getAka()
    {
        return aka;
    }

    public void setAka(Competitor aka)
    {
        this.aka = aka;
    }

    public Competitor getWinner()
    {
        return winner;
    }

    public void setWinner(Competitor winner)
    {
        this.winner = winner;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public Coordinates getNumberCoordinates()
    {
        return numberCoordinates;
    }

    public boolean isComplete()
    {
        return !shiro.isEmpty() && !aka.isEmpty();
    }
}
