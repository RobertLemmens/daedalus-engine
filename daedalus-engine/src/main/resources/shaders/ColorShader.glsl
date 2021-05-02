// VS_BEGIN
#version 330 core

layout(location = 0) in vec2 position;

void main()
{
    gl_Position = vec4(position, 0.0, 1.0);
}
// VS_END

// FS_BEGIN
#version 330 core

out vec4 fragColor;

void main()
{
    fragColor = vec4(1.0);
}
// FS_END