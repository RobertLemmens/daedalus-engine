// VS_BEGIN
#version 330 core

layout(location = 0) in vec4 a_position;
layout(location = 1) in vec2 a_texcoords;
layout(location = 2) in float a_texindex;
layout(location = 3) in float a_textilingfactor;

uniform mat4 u_view_projection;

out vec4 v_position;
out vec2 v_texcoords;
out float v_texindex;
out float v_textilingfactor;

void main()
{
    v_position = a_position;
    v_texcoords = a_texcoords;
    v_texindex = a_texindex;
    v_textilingfactor = a_textilingfactor;
    gl_Position = u_view_projection * a_position;
}
// VS_END

// FS_BEGIN
#version 330 core

layout(location = 0) out vec4 a_color;

in vec2 v_texcoords;
in float v_texindex;
in float v_textilingfactor;

uniform sampler2D u_textures[32];

void main()
{
    a_color = texture(u_textures[int(v_texindex)], v_texcoords * v_textilingfactor);
}
// FS_END
