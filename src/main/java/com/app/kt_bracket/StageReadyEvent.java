package com.app.kt_bracket;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;


public class StageReadyEvent extends ApplicationEvent
{
    public StageReadyEvent(Stage source)
    {
        super(source);
    }

    public Stage getStage()
    {
        return Stage.class.cast(getSource());
    }
}
