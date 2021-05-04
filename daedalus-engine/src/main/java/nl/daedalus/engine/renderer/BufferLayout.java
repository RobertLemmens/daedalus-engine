package nl.daedalus.engine.renderer;

import nl.daedalus.engine.Shader;

import java.util.ArrayList;
import java.util.List;

public class BufferLayout {

    private List<BufferElement> elements;
    private int stride = 0;

    public BufferLayout() {
       elements = new ArrayList<>();
    }

    public void addElement(String name, Shader.Datatype datatype) {
        addElement(name, datatype, false);
    }

    public void addElement(String name, Shader.Datatype datatype, boolean normalize) {

        elements.add(new BufferElement(name, datatype, datatype.getSize(), stride, normalize));
        stride += datatype.getSize();
    }

    public List<BufferElement> getElements() {
        return elements;
    }

    public int getStride() {
        return this.stride;
    }

}
