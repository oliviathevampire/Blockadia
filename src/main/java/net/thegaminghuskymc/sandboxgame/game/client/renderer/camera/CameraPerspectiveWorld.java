package net.thegaminghuskymc.sandboxgame.game.client.renderer.camera;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.window.GLFWWindow;

public class CameraPerspectiveWorld extends CameraProjectiveWorld {

    public static final int TERRAIN_RENDER_DISTANCE = 32;
    public static final float RENDER_DISTANCE = Terrain.DIMX_SIZE * TERRAIN_RENDER_DISTANCE;
    private static final int PLANE_TOP = 0;
    private static final int PLANE_BOT = 1;
    private static final int PLANE_LEFT = 2;
    private static final int PLANE_RIGHT = 3;
    private static final int PLANE_NEAR = 4;
    private static final int PLANE_FAR = 5;
    /**
     * recalculate the frustum planes
     */
    Vector3f nc = new Vector3f();
    Vector3f ntl = new Vector3f();
    Vector3f ntr = new Vector3f();
    Vector3f nbl = new Vector3f();
    Vector3f nbr = new Vector3f();
    Vector3f fc = new Vector3f();
    Vector3f ftl = new Vector3f();
    Vector3f ftr = new Vector3f();
    Vector3f fbl = new Vector3f();
    Vector3f fbr = new Vector3f();
    /**
     * planes attributes
     */
    private float fov;
    private float nearDistance;
    private float farDistance;
    private CameraPlane[] planes;

    public CameraPerspectiveWorld(GLFWWindow window) {
        super(window);
        super.setPosition(0, 16, 0);
        super.setPositionVelocity(0, 0, 0);
        super.setRotationVelocity(0, 0, 0);
        super.setPitch(0);
        super.setYaw(0);
        super.setRoll(0);
        super.setSpeed(0.2f);
        super.setRotSpeed(1);

        this.planes = new CameraPlane[6];
        for (int i = 0; i < this.planes.length; i++) {
            this.planes[i] = new CameraPlane();
        }

        this.setFov(70);
        this.setNearDistance(0.01f);
        this.setFarDistance(RENDER_DISTANCE);
        this.setRenderDistance(RENDER_DISTANCE);
    }

    @Override
    public Camera clone() {
        CameraPerspectiveWorld camera = new CameraPerspectiveWorld(null);
        camera.setAspect(this.getAspect());
        camera.setPitch(this.getPitch());
        camera.setYaw(this.getYaw());
        camera.setRoll(this.getRoll());
        camera.setFov(this.getFov());
        camera.setNearDistance(this.getNearDistance());
        camera.setFarDistance(this.getFarDistance());
        camera.setPosition(this.getPosition());
        camera.setRenderDistance(this.getRenderDistance());
        camera.setWorld(this.getWorld());
        return (camera);
    }

    @Override
    public void update() {
        super.update();
        this.createPlanes();
    }

    @Override
    protected void createProjectionMatrix(Matrix4f dst) {
        Matrix4f.perspective(dst, this.getAspect(), (float) Math.toRadians(this.getFov()), this.getNearDistance(),
                this.getFarDistance());
    }

    /**
     * a function which creates the perspective view planes represented by the
     * camera
     */
    private void createPlanes() {

        Vector3f up = Vector3f.AXIS_Y;
        Vector3f forward = this.getViewVector();
        Vector3f right = Vector3f.cross(forward, up, null);

        up.normalise();
        forward.normalise();
        right.normalise();

        float tang = (float) Math.tan(Math.toRadians(this.getFov() * 0.5f));

        float nh = this.nearDistance * tang;
        float nw = nh * this.getAspect();

        float fh = this.farDistance * tang;
        float fw = fh * this.getAspect();

        nc.x = this.getPosition().x + forward.x * this.getNearDistance();
        nc.y = this.getPosition().y + forward.y * this.getNearDistance();
        nc.z = this.getPosition().z + forward.z * this.getNearDistance();

        fc.x = this.getPosition().x + forward.x * this.getFarDistance();
        fc.y = this.getPosition().y + forward.y * this.getFarDistance();
        fc.z = this.getPosition().z + forward.z * this.getFarDistance();

        // calculate rectangle planes corners (ftl = far, top, left) (nbr =
        // near, bot, right)
        // near plane corner
        ntl.x = nc.x + (up.x * nh) - (right.x * nw);
        ntl.y = nc.y + (up.y * nh) - (right.y * nw);
        ntl.z = nc.z + (up.z * nh) - (right.z * nw);

        ntr.x = nc.x + (up.x * nh) + (right.x * nw);
        ntr.y = nc.y + (up.y * nh) + (right.y * nw);
        ntr.z = nc.z + (up.z * nh) + (right.z * nw);

        nbl.x = nc.x - (up.x * nh) - (right.x * nw);
        nbl.y = nc.y - (up.y * nh) - (right.y * nw);
        nbl.z = nc.z - (up.z * nh) - (right.z * nw);

        nbr.x = nc.x - (up.x * nh) + (right.x * nw);
        nbr.y = nc.y - (up.y * nh) + (right.y * nw);
        nbr.z = nc.z - (up.z * nh) + (right.z * nw);

        // far plane corners
        ftl.x = fc.x + (up.x * fh) - (right.x * fw);
        ftl.y = fc.y + (up.y * fh) - (right.y * fw);
        ftl.z = fc.z + (up.z * fh) - (right.z * fw);

        ftr.x = fc.x + (up.x * fh) + (right.x * fw);
        ftr.y = fc.y + (up.y * fh) + (right.y * fw);
        ftr.z = fc.z + (up.z * fh) + (right.z * fw);

        fbl.x = fc.x - (up.x * fh) - (right.x * fw);
        fbl.y = fc.y - (up.y * fh) - (right.y * fw);
        fbl.z = fc.z - (up.z * fh) - (right.z * fw);

        fbr.x = fc.x - (up.x * fh) + (right.x * fw);
        fbr.y = fc.y - (up.y * fh) + (right.y * fw);
        fbr.z = fc.z - (up.z * fh) + (right.z * fw);

        // set the planes
        this.planes[PLANE_TOP].set(ntr, ntl, ftl);
        this.planes[PLANE_BOT].set(nbl, nbr, fbr);
        this.planes[PLANE_LEFT].set(ntl, nbl, fbl);
        this.planes[PLANE_RIGHT].set(nbr, ntr, fbr);
        this.planes[PLANE_NEAR].set(ntl, ntr, nbr);
        this.planes[PLANE_FAR].set(ftr, ftl, fbl);
    }

    private float getNearDistance() {
        return (this.nearDistance);
    }

    private void setNearDistance(float f) {
        this.nearDistance = f;
    }

    private float getFarDistance() {
        return (this.farDistance);
    }

    private void setFarDistance(float f) {
        this.farDistance = f;
    }

    private float getFov() {
        return (this.fov);
    }

    private void setFov(float f) {
        this.fov = f;
    }

    @Override
    public boolean isPointInFrustum(float x, float y, float z) {
        long t = System.nanoTime();
        for (int i = 0; i < 6; i++) {
            if (this.planes[i].distance(x, y, z) < 0) {
                Logger.get().log(Logger.Level.DEBUG, "out: " + (System.nanoTime() - t));
                return (false);
            }
        }
        Logger.get().log(Logger.Level.DEBUG, "in: " + (System.nanoTime() - t));
        return (true);
    }

    @Override
    public boolean isBoxInFrustum(float x, float y, float z, float sx, float sy, float sz) {

        for (int i = 0; i < 6; i++) {

            CameraPlane plane = this.planes[i];

            if (plane.distance(this.getVertexP(plane.normal, x, y, z, sx, sy, sz)) < 0) {
                return (false); // outside
            }
        }
        return (true); // fully inside
    }

    private Vector3f getVertexP(Vector3f normal, float x, float y, float z, float sx, float sy, float sz) {
        Vector3f res = new Vector3f(x, y, z);
        if (normal.x > 0) {
            res.x += sx;
        }
        if (normal.y > 0) {
            res.y += sy;
        }
        if (normal.z > 0) {
            res.z += sz;
        }
        return (res);
    }

    @Override
    public boolean isSphereInFrustum(Vector3f center, float radius) {

        for (int i = 0; i < 6; i++) {
            float distance = this.planes[i].distance(center);
            if (distance < -radius) {
                return (false);
            }
            if (distance < radius) {
                return (true); // intersect
            }
        }
        return (true); // inside
    }

    class CameraPlane {

        Vector3f normal;
        Vector3f point;
        float d;

        CameraPlane() {
            this.normal = new Vector3f();
            this.point = new Vector3f();
        }

        void set(Vector3f a, Vector3f b, Vector3f c) {

            Vector3f aux1 = Vector3f.sub(a, b, null);
            Vector3f aux2 = Vector3f.sub(c, b, null);
            Vector3f.cross(aux2, aux1, this.normal);
            this.normal.normalise();
            this.point.set(b);
            this.d = -(Vector3f.dot(this.normal, this.point));
        }

        public float distance(float x, float y, float z) {
            return (d + Vector3f.dot(this.normal, x, y, z));
        }

        public float distance(Vector3f point) {
            return (this.distance(point.x, point.y, point.z));
        }
    }

}