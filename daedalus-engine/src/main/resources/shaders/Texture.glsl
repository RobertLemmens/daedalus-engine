// VS_BEGIN
#version 330 core

layout(location = 0) in vec3 a_position;
layout(location = 1) in vec4 a_color;
layout(location = 2) in vec2 a_texcoord;
layout(location = 3) in float a_texindex;
layout(location = 4) in float a_tilingfactor;

uniform mat4 u_view_projection;

out vec4 v_color;
out vec2 v_texcoord;
out float v_texindex;
out float v_tilingfactor;

void main() {
    v_color = a_color;
    v_texcoord = a_texcoord;
    v_texindex= a_texindex;
    v_tilingfactor= a_tilingfactor;

    gl_Position = u_view_projection *  vec4(a_position, 1.0);
}
// VS_END

// FS_BEGIN
#version 330 core

layout(location = 0) out vec4 a_color;

in vec4 v_color;
in vec2 v_texcoord;
in float v_texindex;
in float v_tilingfactor;

uniform sampler2D u_Texture[32]; // 32 max supported textures.

void main() {
    //    a_color = texture(u_Texture[int(v_TexIndex)], v_texcoord * v_tilingfactor) * v_color; // mul by color is hue voor texture // werkt niet op AMD
    vec4 tex_color = v_color;
    switch (int (v_texindex)) {
        case 0: tex_color *= texture(u_Texture[0], v_texcoord * v_tilingfactor); break;
        case 1: tex_color *= texture(u_Texture[1], v_texcoord * v_tilingfactor); break;
        case 2: tex_color *= texture(u_Texture[2], v_texcoord * v_tilingfactor); break;
        case 3: tex_color *= texture(u_Texture[3], v_texcoord * v_tilingfactor); break;
        case 4: tex_color *= texture(u_Texture[4], v_texcoord * v_tilingfactor); break;
        case 5: tex_color *= texture(u_Texture[5], v_texcoord * v_tilingfactor); break;
        case 6: tex_color *= texture(u_Texture[6], v_texcoord * v_tilingfactor); break;
        case 7: tex_color *= texture(u_Texture[7], v_texcoord * v_tilingfactor); break;
        case 8: tex_color *= texture(u_Texture[8], v_texcoord * v_tilingfactor); break;
        case 9: tex_color *= texture(u_Texture[9], v_texcoord * v_tilingfactor); break;
        case 10: tex_color *= texture(u_Texture[10], v_texcoord * v_tilingfactor); break;
        case 11: tex_color *= texture(u_Texture[11], v_texcoord * v_tilingfactor); break;
        case 12: tex_color *= texture(u_Texture[12], v_texcoord * v_tilingfactor); break;
        case 13: tex_color *= texture(u_Texture[13], v_texcoord * v_tilingfactor); break;
        case 14: tex_color *= texture(u_Texture[14], v_texcoord * v_tilingfactor); break;
        case 15: tex_color *= texture(u_Texture[15], v_texcoord * v_tilingfactor); break;
        case 16: tex_color *= texture(u_Texture[16], v_texcoord * v_tilingfactor); break;
        case 17: tex_color *= texture(u_Texture[17], v_texcoord * v_tilingfactor); break;
        case 18: tex_color *= texture(u_Texture[18], v_texcoord * v_tilingfactor); break;
        case 19: tex_color *= texture(u_Texture[19], v_texcoord * v_tilingfactor); break;
        case 20: tex_color *= texture(u_Texture[20], v_texcoord * v_tilingfactor); break;
        case 21: tex_color *= texture(u_Texture[21], v_texcoord * v_tilingfactor); break;
        case 22: tex_color *= texture(u_Texture[22], v_texcoord * v_tilingfactor); break;
        case 23: tex_color *= texture(u_Texture[23], v_texcoord * v_tilingfactor); break;
        case 24: tex_color *= texture(u_Texture[24], v_texcoord * v_tilingfactor); break;
        case 25: tex_color *= texture(u_Texture[25], v_texcoord * v_tilingfactor); break;
        case 26: tex_color *= texture(u_Texture[26], v_texcoord * v_tilingfactor); break;
        case 27: tex_color *= texture(u_Texture[27], v_texcoord * v_tilingfactor); break;
        case 28: tex_color *= texture(u_Texture[28], v_texcoord * v_tilingfactor); break;
        case 29: tex_color *= texture(u_Texture[29], v_texcoord * v_tilingfactor); break;
        case 30: tex_color *= texture(u_Texture[30], v_texcoord * v_tilingfactor); break;
        case 32: tex_color *= texture(u_Texture[31], v_texcoord * v_tilingfactor); break;
    }
    a_color = tex_color;
}
// FS_END
