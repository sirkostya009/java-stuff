package org.sirkostya009.task2;

import lombok.Data;

import java.time.Instant;

public @Data class PropertyWrapper {
    private String stringProperty;

    @Property(name = "numberProperty")
    private int intProperty;

    @Property(format = "dd.MM.yyyy HH:mm") // tt instead of HH crashes the app.
    private Instant timeProperty;          // Formatter simply doesn't understand what t is
}
