package team.hdt.blockadia.engine.core.util;

import ga.pheonix.utillib.utils.vectors.Vectors2f;
import ga.pheonix.utillib.utils.vectors.Vectors3f;
import team.hdt.blockadia.engine.core.util.mesh.ModelMesh;

import java.util.List;

public class Model extends ModelMesh {
    public Model(List<int[]> faces, List<Vectors3f> verts, List<Vectors2f> tex_coords) {
        super();
        update_gl_data(faces, verts, tex_coords);
        update_gl_buffers();
    }
}