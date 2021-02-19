package com.app.kt_bracket.logic;

import com.app.kt_bracket.data.Category;
import com.app.kt_bracket.structure.*;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
public class BracketBuilder
{
    private static final int THE_LOWEST_COLUMN = 0;
    private static final int FIRST_COLUMN = 1;
    private final int winnerHorizontalDistance = 3;

    private Category category;
    private BracketColumn theLowestColumn;
    private Bracket bracket;


    public Bracket build(Category category)
    {
        this.category = category;

        return completeToPowerOfTwo()
                .reverseOrderOfCompleted()
                .buildTheLowestColumn()
                .buildRestOfColumns()
                .assignCoordinatesTheLowestColumn()
                .assignCoordinatesToRestOfColumns()
                .removeIncompleteFights()
                .assignCoordinatesForFightNumber()
                .decorate()
                .assignCategoryName()
                .get();
    }

    private Bracket get()
    {
        return bracket;
    }

    private BracketBuilder assignCategoryName()
    {
        bracket.setCategoryName(category.getName());
        return this;
    }

    private BracketBuilder decorate()
    {
        IntStream.iterate(0, col -> ++col)
            .limit(bracket.getColumns().size())
            .forEach(col -> {
                bracket.getColumns().get(col).getFights().stream()
                    .forEach(fight -> {
                        Coordinates shiroHorizontalLine = new Coordinates(fight.getShiro().getCoordinates().getX() + 1, fight.getShiro().getCoordinates().getY());
                        Coordinates upRightCorner = new Coordinates(fight.getShiro().getCoordinates().getX() + 2, fight.getShiro().getCoordinates().getY());
                        Coordinates tJunction = new Coordinates(fight.getWinner().getCoordinates().getX() - 1, fight.getWinner().getCoordinates().getY());
                        Coordinates downRightCorner = new Coordinates(fight.getAka().getCoordinates().getX() + 2, fight.getAka().getCoordinates().getY());
                        Coordinates akaHorizontalLine = new Coordinates(fight.getAka().getCoordinates().getX() + 1, fight.getAka().getCoordinates().getY());

                        List<Coordinates> winnerTopVerticalLines = new ArrayList<>();
                        List<Coordinates> winnerBottomVerticalLines = new ArrayList<>();
                        IntStream.iterate(1, row -> ++row)
                            .limit((int) Math.pow(2, col) - 1)
                            .forEach(row -> {
                                winnerTopVerticalLines.add(new Coordinates(fight.getShiro().getCoordinates().getX() + 2,
                                        fight.getShiro().getCoordinates().getY() + row));
                                winnerBottomVerticalLines.add(new Coordinates(fight.getAka().getCoordinates().getX() + 2,
                                        fight.getAka().getCoordinates().getY() - row));
                            });
                        fight.setDecoration(new Decoration( shiroHorizontalLine, upRightCorner, winnerTopVerticalLines,
                                                            tJunction,
                                                            winnerBottomVerticalLines, downRightCorner, akaHorizontalLine ));
                    });
            });

        return this;
    }

    private BracketBuilder assignCoordinatesForFightNumber()
    {
        bracket.getColumns().stream()
            .forEach(column -> {
                column.getFights().stream()
                    .forEach(fight -> {
                        Coordinates winnerCoordinates = fight.getWinner().getCoordinates();
                        fight.setNumberCoordinates(new Coordinates( winnerCoordinates.getX() - 2, winnerCoordinates.getY() ));
                    });
            });

        return this;
    }

    private BracketBuilder removeIncompleteFights()
    {
        AtomicInteger theLowestColumnFightPos = new AtomicInteger(0);

        bracket.getColumns().get(FIRST_COLUMN).getFights().stream()
            .forEach(fight -> {
                Fight firstTheLowestColumnFight = bracket.getColumns().get(THE_LOWEST_COLUMN).getFights().get(theLowestColumnFightPos.getAndIncrement());
                Fight secondTheLowestColumnFight = bracket.getColumns().get(THE_LOWEST_COLUMN).getFights().get(theLowestColumnFightPos.getAndIncrement());

                if ( !firstTheLowestColumnFight.isComplete() )
                    fight.setShiro( new Competitor(fight.getShiro()) );
                if ( !secondTheLowestColumnFight.isComplete() )
                    fight.setAka( new Competitor(fight.getAka()) );
            });

        bracket.getColumns().get(THE_LOWEST_COLUMN).getFights().removeIf(fight -> !fight.isComplete());

        return this;
    }

    private BracketBuilder assignCoordinatesToRestOfColumns()
    {
        AtomicInteger prevCol = new AtomicInteger(0);

        IntStream.iterate(1, colNum -> ++colNum )
            .limit(bracket.getColumns().size()-1)
            .forEach(colNum -> {
                AtomicInteger prevColFightNo = new AtomicInteger(0);
                bracket.getColumns().get(colNum).getFights().stream()
                    .forEach(fight -> {
                        Coordinates shiroCoordinates = bracket.getColumns().get(prevCol.get())
                            .getFights().get(prevColFightNo.getAndIncrement()).getWinner().getCoordinates();
                        int winnerX = shiroCoordinates.getX() + winnerHorizontalDistance;
                        int winnerY = shiroCoordinates.getY() + (int) Math.pow(2, colNum);
                        Coordinates akaCoordinates = bracket.getColumns().get(prevCol.get())
                            .getFights().get(prevColFightNo.getAndIncrement()).getWinner().getCoordinates();

                        fight.getShiro().setCoordinates(shiroCoordinates);
                        fight.getWinner().setCoordinates(new Coordinates(winnerX, winnerY));
                        fight.getAka().setCoordinates(akaCoordinates);
                    });
                prevCol.getAndIncrement();
            });

        return this;
    }

    private BracketBuilder assignCoordinatesTheLowestColumn()
    {
        final int winnerVerticalDistance = 1;
        AtomicInteger row = new AtomicInteger(0);

        this.bracket.getColumns().get(0).getFights()
           .forEach(fight -> {
               int shiroX = 0;
               int shiroY = row.getAndAdd(2);
               int akaX = 0;
               int akaY = row.getAndAdd(2);

               fight.getShiro().setCoordinates(new Coordinates(shiroX, shiroY));
               fight.getWinner().setCoordinates(new Coordinates(shiroX + winnerHorizontalDistance, shiroY + winnerVerticalDistance));
               fight.getAka().setCoordinates(new Coordinates(akaX, akaY));
           });

        return this;
    }

    private BracketBuilder buildRestOfColumns()
    {
        this.bracket = new Bracket();
        bracket.getColumns().add(this.theLowestColumn);

        int numberOfColumns = (int)(Math.log(this.category.getCompetitorList().size()) / Math.log(2));
        int ladderSize = this.category.getCompetitorList().size();
        AtomicInteger prevCol = new AtomicInteger(0); // column counter to use in the loop below

        // Build the rest of columns for this bracket
        IntStream.iterate(ladderSize/4, numberOfFights -> numberOfFights/2)
                 .limit(numberOfColumns - 1) // because the the lowest column is already built
                 .forEach(numberOfFights -> { // iterate and create columns here
                     bracket.getColumns().add( new BracketColumn(
                        IntStream.iterate(0, prevColFightPos -> prevColFightPos + 2)
                                 .limit(numberOfFights)
                                 .mapToObj(prevColFightPos -> { // iterate and create fights for column here
                                     return new Fight(bracket.getColumns().get(prevCol.get()).getFights().get(prevColFightPos).getWinner(),
                                                      bracket.getColumns().get(prevCol.get()).getFights().get(prevColFightPos+1).getWinner(),
                                                      new Competitor("###############") );
                                 }).collect(Collectors.toList())) );
                     prevCol.getAndIncrement();
                 });

        return this;
    }

    private BracketBuilder buildTheLowestColumn()
    {
        // Create fights for the lowest columns and fill their shiros and akas with Competitors from category list
        this.theLowestColumn = new BracketColumn();
        IntStream.iterate(1, i -> i + 2)
                .limit(category.getCompetitorList().size() / 2)
                .forEach(i -> theLowestColumn.getFights().add(new Fight(category.getCompetitorList().get(i-1),
                                                                        category.getCompetitorList().get(i),
                                                                        new Competitor("###############"))) );

        // Re-write Competitors of incomplete fights as their winners
        this.theLowestColumn.getFights()
                .forEach(fight -> {
                    if ( fight.getAka().isEmpty() ) {
                        fight.setWinner(fight.getShiro());
                        fight.setShiro(new Competitor("###############"));
                    }
                });

        return this;
    }

    private BracketBuilder reverseOrderOfCompleted()
    {
        Collections.reverse(this.category.getCompetitorList());
        return this;
    }

    private BracketBuilder completeToPowerOfTwo()
    {
        int freePlaces = determineSize() - this.category.getCompetitorList().size();
        IntStream.iterate(0, i -> i + 2 )
                .limit(freePlaces)
                .forEach(i -> category.getCompetitorList().add(i, new Competitor("###############")) );

        return this;
    }

    private int determineSize()
    {
        int numberOfCompetitors = this.category.getCompetitorList().size();
        int powerResult = 0, power = 0;

        while ( powerResult < numberOfCompetitors )
        {
            powerResult = (int) Math.pow(2, power);
            power++;
        }

        return powerResult;
    }
}
