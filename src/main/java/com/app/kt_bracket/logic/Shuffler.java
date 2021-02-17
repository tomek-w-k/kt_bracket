package com.app.kt_bracket.logic;

import com.app.kt_bracket.data.Category;
import java.util.Collections;


public class Shuffler
{
    public void shuffle(Category category)
    {
        Collections.shuffle(category.getCompetitorList());
    }
}
