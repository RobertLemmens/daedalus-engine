package nl.codebulb.engine;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

    int programID;
    int vertexShaderID;
    int fragmentShaderID;

    public Shader(String shaderPath) {
        programID = glCreateProgram();
        if(!shaderPath.endsWith(".glsl")) {
            DaedalusLogger.error("Shader exception: Only glsl files are supported");
        }
        // get vertex and fragmentshader from glsl file
        String shaderSrc = FileUtils.getResourceFileAsString(shaderPath);
        int vsStart = shaderSrc.indexOf("// VS_BEGIN");
        int vsEnd = shaderSrc.indexOf("// VS_END");
        String vertexSrc = shaderSrc.substring(vsStart, vsEnd);
        int fsStart = shaderSrc.indexOf("// FS_BEGIN");
        int fsEnd = shaderSrc.indexOf("// FS_END");
        String fragmentSrc = shaderSrc.substring(fsStart, fsEnd);
        attachVertexShader(vertexSrc, fragmentSrc);
    }

    public Shader(String vertexShaderSrc, String fragmentShaderSrc) {
        programID = glCreateProgram();
        attachVertexShader(vertexShaderSrc, fragmentShaderSrc);
    }

    private void attachVertexShader(String vertexShader, String fragmentShader)
    {


        // Create the shader and set the source
        vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderID, vertexShader);

        // Compile the shader
        glCompileShader(vertexShaderID);

        // Check for errors
        if (glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == GL_FALSE)
            throw new RuntimeException("Error creating vertex shader\n"
                    + glGetShaderInfoLog(vertexShaderID, glGetShaderi(vertexShaderID, GL_INFO_LOG_LENGTH)));

        // Attach the shader
        glAttachShader(programID, vertexShaderID);

        fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderID, fragmentShader);

        // Compile the shader
        glCompileShader(fragmentShaderID);

        // Check for errors
        if (glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) == GL_FALSE)
            throw new RuntimeException("Error creating fragment shader\n"
                    + glGetShaderInfoLog(fragmentShaderID, glGetShaderi(fragmentShaderID, GL_INFO_LOG_LENGTH)));

        // Attach the shader
        glAttachShader(programID, fragmentShaderID);
    }

    public void bind()
    {
        glUseProgram(programID);
    }

    /**
     * Unbind the shader program.
     */
    public void unbind()
    {
        glUseProgram(0);
    }

    public void link()
    {
        // Link this program
        glLinkProgram(programID);

        // Check for linking errors
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE)
            throw new RuntimeException("Unable to link shader program:");
    }

    public void dispose()
    {
        // Unbind the program
        unbind();

        // Detach the shaders
        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);

        // Delete the shaders
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);

        // Delete the program
        glDeleteProgram(programID);
    }

    public int getID()
    {
        return programID;
    }

}
