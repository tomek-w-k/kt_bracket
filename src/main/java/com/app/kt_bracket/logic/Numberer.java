package com.app.kt_bracket.logic;

import com.app.kt_bracket.data.Category;
import com.app.kt_bracket.structure.Bracket;
import com.app.kt_bracket.structure.Competitor;
import com.app.kt_bracket.structure.Fight;
import com.app.kt_bracket.structure.Mat;
import javafx.scene.control.TreeItem;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
public class Numberer
{
    private Mat mat;
    private int allFightsCount;


    public void number(Mat mat)
    {
        this.mat = mat;

        sortBracketDesc()
        .countAllFights()
        .assignFightNumbers();
    }

    private Numberer assignFightNumbers()
    {
        // determine a number of columns of the biggest bracket
        int theBiggestBracketColumnNumber = mat.getBrackets().get(0).getColumns().size();

        // reverse brackets order, columns in each bracket order and fights in each column order
        Collections.reverse(mat.getBrackets());
        mat.getBrackets().stream()
            .forEach(bracket -> {
                Collections.reverse(bracket.getColumns());
                bracket.getColumns().stream()
                    .forEach(bracketColumn -> Collections.reverse(bracketColumn.getFights()));
            });

        // assign a number to each fight
        AtomicInteger fightCounter = new AtomicInteger(this.allFightsCount);
        IntStream.iterate(0, col -> col + 1)
            .limit(theBiggestBracketColumnNumber )
            .forEach(col -> {
                mat.getBrackets().stream()
                    .forEach(bracket -> {
                        if ( bracket.getColumns().size() > col )
                            bracket.getColumns().get(col).getFights().stream()
                                .forEach(fight -> fight.setNumber(fightCounter.getAndDecrement()));
                    });
            });

        // reverse brackets order, columns in each bracket order and fights in each column order
        Collections.reverse(mat.getBrackets());
        mat.getBrackets().stream()
            .forEach(bracket -> {
                Collections.reverse(bracket.getColumns());
                bracket.getColumns().stream()
                        .forEach(bracketColumn -> Collections.reverse(bracketColumn.getFights()));
            });

        return this;
    }

    private Numberer countAllFights()
    {
        AtomicInteger counter = new AtomicInteger(0);

        mat.getBrackets().stream()
            .forEach(bracket -> {
                bracket.getColumns().stream()
                    .forEach(bracketColumn -> {
                        bracketColumn.getFights().stream()
                            .forEach(fight -> counter.set(counter.get() + 1));
                    });
            });

        this.allFightsCount = counter.get();

        return this;
    }

    private Numberer sortBracketDesc()
    {
        mat.getBrackets().sort(Comparator.comparing(o -> o.getNumberOfCompetitors()));
        Collections.reverse(mat.getBrackets());

        return this;
    }
}
