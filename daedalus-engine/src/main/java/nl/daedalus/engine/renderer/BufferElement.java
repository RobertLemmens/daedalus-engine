package nl.daedalus.engine.renderer;

import nl.daedalus.engine.core.Shader;

public record BufferElement(String name, Shader.Datatype datatype, int size, int offset, boolean normalized) {

    public int getComponentCount() {
        return switch (datatype) {
            case FLOAT -> 1;
            case FLOAT2 -> 2;
            case FLOAT3 -> 3;
            case FLOAT4 -> 4;
            case MAT3 -> 12;
            case MAT4 -> 16;
            case INT -> 1;
            case INT2 -> 2;
            case INT3 -> 3;
            case INT4 -> 4;
            case BOOL -> 1;
        };
    }

}
