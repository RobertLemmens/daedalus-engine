// VS_BEGIN
#version 330 core

layout(location = 0) in vec3 a_position;
layout(location = 1) in vec4 a_color;

out vec3 v_position;
out vec4 v_color;

void main()
{
    v_position = a_position;
    v_color = a_color;
    gl_Position = vec4(a_position, 1.0);
}
// VS_END

// FS_BEGIN
#version 330 core

layout(location = 0) out vec4 a_color;

in vec3 v_position;
in vec4 v_color;

void main()
{
    a_color = v_color;
}
// FS_END