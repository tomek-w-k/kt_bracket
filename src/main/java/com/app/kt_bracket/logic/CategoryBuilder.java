package com.app.kt_bracket.logic;

import com.app.kt_bracket.data.Category;
import com.app.kt_bracket.structure.Competitor;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class CategoryBuilder
{
    public Category build(List<String> fullNames, StringProperty categoryName)
    {
        return new Category(fullNames.stream().map(fullName -> new Competitor(fullName))
                                                .collect(Collectors.toList()), categoryName);
    }
}
