package team.hdt.blockadia.game_engine_old.util.toolbox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class Query {

    private final int id;
    private final int type;

    private boolean inUse = false;

    public Query(int type) {
        this.id = GL15.glGenQueries();
        this.type = type;
    }

    public void start() {
        this.inUse = true;
        GL15.glBeginQuery(type, id);
    }

    public void end() {
        GL15.glEndQuery(type);
    }

    public boolean isResultReady() {
        return GL15.glGetQueryObjecti(id, GL15.GL_QUERY_RESULT_AVAILABLE) == GL11.GL_TRUE;
    }

    public boolean isInUse() {
        return inUse;
    }

    public int getResult() {
        this.inUse = false;
        return GL15.glGetQueryObjecti(id, GL15.GL_QUERY_RESULT);
    }

    public void delete() {
        GL15.glDeleteQueries(id);
    }

}
