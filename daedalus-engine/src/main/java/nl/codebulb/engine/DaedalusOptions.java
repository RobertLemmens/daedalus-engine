package nl.codebulb.engine;

import nl.codebulb.engine.exceptions.InitException;

public record DaedalusOptions(int width, int height, String title){
    public DaedalusOptions {
        if (width < 0) {
            throw new InitException("Window Width cannot be negative");
        }
        if (height < 0) {
            throw new InitException("Window Height cannot be negative");
        }
    }
}
