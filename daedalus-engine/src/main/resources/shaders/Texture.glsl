// VS_BEGIN
#version 330 core

layout(location = 0) in vec4 a_position;
layout(location = 1) in vec2 a_texcoords;

uniform mat4 u_view_projection;

out vec4 v_position;
out vec2 v_texcoords;

void main()
{
    v_position = a_position;
    v_texcoords = a_texcoords;
    gl_Position = u_view_projection * a_position;
}
// VS_END

// FS_BEGIN
#version 330 core

layout(location = 0) out vec4 a_color;

in vec2 v_texcoords;

uniform sampler2D u_texture;

void main()
{
    a_color = texture(u_texture, v_texcoords);
}
// FS_END
