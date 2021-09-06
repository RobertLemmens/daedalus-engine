package nl.daedalus.engine.core;

import nl.daedalus.engine.exceptions.InitException;

public record DaedalusOptions(int width, int height, String title, boolean vsync){
    public DaedalusOptions {
        if (width < 0) {
            throw new InitException("Window Width cannot be negative");
        }
        if (height < 0) {
            throw new InitException("Window Height cannot be negative");
        }
    }
}
