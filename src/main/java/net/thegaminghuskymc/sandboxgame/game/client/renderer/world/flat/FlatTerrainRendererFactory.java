package net.thegaminghuskymc.sandboxgame.game.client.renderer.world.flat;

import net.thegaminghuskymc.sandboxgame.engine.Timer;
import net.thegaminghuskymc.sandboxgame.engine.events.EventListener;
import net.thegaminghuskymc.sandboxgame.engine.events.world.*;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.managers.EventManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.engine.world.WorldFlat;
import net.thegaminghuskymc.sandboxgame.game.client.GameEngineClient;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.GLH;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.MainRenderer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.RendererFactory;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraProjective;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.TerrainMesh;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.TerrainMeshTriangle;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.TerrainMesher;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.world.TerrainRenderer;
import org.lwjgl.glfw.GLFW;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

/**
 * a factory class which create terrain renderer lists
 */
public class FlatTerrainRendererFactory extends RendererFactory {

    public static boolean LIGHT = true;
    static int i = 0;
    static double DT = 0;
    static int COUNT = 0;
    static int MAX_COUNT = 16;
    /**
     * array list of terrain to render
     */
    private HashMap<Terrain, TerrainRenderingData> terrainsRenderingData;
    /**
     * next rendering list
     */
    private ArrayList<TerrainMesh> opaqueRenderingList;
    private ArrayList<TerrainMesh> transparentRenderingList;
    /**
     * the world on which terrain should be considered
     */
    private WorldFlat world;
    private CameraProjective camera;
    private final Comparator<? super TerrainMesh> DISTANCE_DESC_SORT = new Comparator<TerrainMesh>() {
        @Override
        public int compare(TerrainMesh o1, TerrainMesh o2) {
            return ((int) (Vector3f.distanceSquare(o2.getPosition(), camera.getPosition())
                    - Vector3f.distanceSquare(o1.getPosition(), camera.getPosition())));
        }
    };
    private TerrainMesher mesher;
    public FlatTerrainRendererFactory(MainRenderer mainRenderer) {
        super(mainRenderer);

        this.terrainsRenderingData = new HashMap<Terrain, TerrainRenderingData>(4096);
        // this.mesher = new MarchingCubesTerrainMesher();
        this.mesher = new FlatTerrainMesherGreedy();
        // this.mesher = new FlatTerrainMesherCull();
        this.opaqueRenderingList = new ArrayList<TerrainMesh>();
        this.transparentRenderingList = new ArrayList<TerrainMesh>();

        EventManager eventManager = ResourceManager.instance().getEventManager();
        eventManager.addListener(new EventListener<EventTerrainDespawn>() {
            @Override
            public void invoke(EventTerrainDespawn event) {
                Terrain terrain = event.getTerrain();
                if (terrain.getWorld() != world) {
                    return;
                }
                TerrainRenderingData terrainRenderingData = terrainsRenderingData.get(terrain);
                if (terrainRenderingData != null) {
                    terrainRenderingData.deinitialize();
                    terrainsRenderingData.remove(terrain);
                }
            }
        });

        eventManager.addListener(new EventListener<EventTerrainSetBlock>() {
            @Override
            public void invoke(EventTerrainSetBlock event) {
                if (event.getTerrain().getWorld() != world) {
                    return;
                }
                requestMeshUpdate(event.getTerrain());
            }
        });

        eventManager.addListener(new EventListener<EventTerrainBlocklightUpdate>() {
            @Override
            public void invoke(EventTerrainBlocklightUpdate event) {
                if (event.getTerrain().getWorld() != world) {
                    return;
                }
                requestMeshUpdate(event.getTerrain());
            }
        });

        eventManager.addListener(new EventListener<EventTerrainSunlightUpdate>() {
            @Override
            public void invoke(EventTerrainSunlightUpdate event) {
                if (event.getTerrain().getWorld() != world) {
                    return;
                }
                requestMeshUpdate(event.getTerrain());
            }
        });

        eventManager.addListener(new EventListener<EventTerrainDurabilityChanged>() {
            @Override
            public void invoke(EventTerrainDurabilityChanged event) {
                if (event.getTerrain().getWorld() != world) {
                    return;
                }
                requestMeshUpdate(event.getTerrain());
            }
        });
    }

    @Override
    public final void deinitialize() {
        /** destroy every currently set meshes */
        Collection<TerrainRenderingData> terrainsRenderingData = this.terrainsRenderingData.values();
        GameEngineClient.instance().addGLTask(new MainRenderer.GLTask() {
            @Override
            public void run() {
                for (TerrainRenderingData terrainRenderingData : terrainsRenderingData) {
                    if (terrainRenderingData != null) {
                        terrainRenderingData.deinitialize();
                    }
                }
            }
        });
        this.terrainsRenderingData.clear();

    }

    @Override
    public void update(double dt) {
        COUNT = 0;
        DT += dt;
        if ((DT < 0 || DT > 0.2)) {
            if (GLH.glhGetWindow().isKeyPressed(GLFW.GLFW_KEY_X)) {
                DT = 0;

//				this.mesher = i % 5 == 0 ? new FlatTerrainMesherGreedy()
//						: new MarchingCubesTerrainMesher((int) (Math.pow(2, i % 5 - 1)));
                // this.mesher = new MarchingCubesTerrainMesher((int)
                // (Math.pow(2, 0)));
                // this.mesher = i % 2 == 0 ? new FlatTerrainMesherGreedy()
                // : new MarchingCubesTerrainMesher((int) (Math.pow(2, 0)));

                for (TerrainRenderingData terrainRenderingData : terrainsRenderingData.values()) {
                    terrainRenderingData.requestUpdate();
                }
                ++i;
            }
        }
        this.updateLoadedMeshes();
        this.updateRenderingList();
    }

    private final void updateRenderingList() {
        this.opaqueRenderingList.clear();
        this.transparentRenderingList.clear();
        Collection<TerrainRenderingData> collection = this.terrainsRenderingData.values();
        TerrainRenderingData[] terrainsRenderingData = collection.toArray(new TerrainRenderingData[collection.size()]);

        // update meshes and add opaques one first (to be rendered first)
        for (TerrainRenderingData terrainRenderingData : terrainsRenderingData) {
            terrainRenderingData.update(this.getCamera());
            if (terrainRenderingData.isInFrustrum() && terrainRenderingData.opaqueMesh.getVertexCount() > 0) {
                this.opaqueRenderingList.add(terrainRenderingData.opaqueMesh);
            }
        }
        this.opaqueRenderingList.sort(DISTANCE_DESC_SORT);

        // add the transparent meshes (to be rendered after opaque ones)
        for (TerrainRenderingData terrainRenderingData : terrainsRenderingData) {
            if (terrainRenderingData.isInFrustrum() && terrainRenderingData.transparentMesh.getVertexCount() > 0) {
                this.transparentRenderingList.add(terrainRenderingData.transparentMesh);
            }
        }

        GameEngineClient.instance().addGLTask(new MainRenderer.GLTask() {
            @Override
            public void run() {
                for (TerrainRenderingData terrainRenderingData : terrainsRenderingData) {
                    terrainRenderingData.glUpdate();
                }
            }
        });
    }

    private final void requestMeshUpdate(Terrain terrain) {
        TerrainRenderingData terrainRenderingData = this.terrainsRenderingData.get(terrain);
        if (terrainRenderingData == null) {
            return;
        }
        terrainRenderingData.requestUpdate();
    }

    /**
     * unload the meshes
     */
    private final void updateLoadedMeshes() {

        // for each terrain in the factory
        ArrayList<TerrainRenderingData> oldTerrainsRenderingData = new ArrayList<TerrainRenderingData>();
        for (Terrain terrain : this.terrainsRenderingData.keySet()) {
            // if this terrain isnt loaded anymore
            if (!this.getWorld().isTerrainLoaded(terrain)) {
                // then remove it from the factory
                TerrainRenderingData terrainRenderingData = this.terrainsRenderingData.remove(terrain);
                oldTerrainsRenderingData.add(terrainRenderingData);
            }
        }

        // for every loaded terrains
        Terrain[] terrains = this.getWorld().getLoadedTerrains();
        for (Terrain terrain : terrains) {
            // add it to the factory if it hasnt already been added
            if (!this.terrainsRenderingData.containsKey(terrain)) {
                this.terrainsRenderingData.put(terrain, new TerrainRenderingData(terrain));
            }
        }

        GameEngineClient.instance().addGLTask(new MainRenderer.GLTask() {
            @Override
            public void run() {
                for (TerrainRenderingData terrainRenderingData : oldTerrainsRenderingData) {
                    terrainRenderingData.deinitialize();
                }
            }
        });
    }

    @Override
    public void render() {
        MainRenderer mainRenderer = this.getMainRenderer();
        TerrainRenderer terrainRenderer = mainRenderer.getTerrainRenderer();
        terrainRenderer.render(this.getCamera(), this.getWorld(), this.opaqueRenderingList,
                this.transparentRenderingList);
    }

    public final WorldFlat getWorld() {
        return (this.world);
    }

    public final void setWorld(WorldFlat world) {
        this.world = world;
    }

    public final CameraProjective getCamera() {
        return (this.camera);
    }

    public final void setCamera(CameraProjective camera) {
        this.camera = camera;
    }

    class TerrainRenderingData {

        final Terrain terrain;
        final TerrainMesh opaqueMesh; // mesh holding opaque blocks
        final TerrainMesh transparentMesh; // mesh holding transparent blocks
        final ArrayList<TerrainMeshTriangle> opaqueTriangles;
        final ArrayList<TerrainMeshTriangle> transparentTriangles;
        boolean meshUpToDate;
        ByteBuffer vertices;
        boolean isInFrustrum;
        Vector3f lastCameraPos;
        Timer timer;
        Comparator<TerrainMeshTriangle> TRIANGLE_SORT = new Comparator<TerrainMeshTriangle>() {
            @Override
            public int compare(TerrainMeshTriangle t1, TerrainMeshTriangle t2) {
                return (int) (t2.cameraDistance - t1.cameraDistance);
            }
        };
        private float distance;

        TerrainRenderingData(Terrain terrain) {
            this.terrain = terrain;
            this.opaqueMesh = new TerrainMesh(terrain);
            this.transparentMesh = new TerrainMesh(terrain);
            this.opaqueTriangles = new ArrayList<TerrainMeshTriangle>();
            this.transparentTriangles = new ArrayList<TerrainMeshTriangle>();
            this.meshUpToDate = false;
            this.lastCameraPos = new Vector3f();
            this.timer = new Timer();
        }

        void requestUpdate() {
            this.meshUpToDate = false;
        }

        void deinitialize() {
            this.opaqueMesh.deinitialize();
            this.transparentMesh.deinitialize();
        }

        void update(CameraProjective camera) {
            this.timer.update();
            // calculate square distance from camera
            Vector3f center = this.terrain.getWorldPosCenter();
            this.distance = (float) Vector3f.distanceSquare(center, camera.getPosition());
            this.isInFrustrum = (this.distance < camera.getSquaredRenderDistance()
                    && camera.isBoxInFrustum(terrain.getWorldPos(), Terrain.TERRAIN_SIZE));
        }

        boolean isInFrustrum() {
            return (this.isInFrustrum);
        }

        void glUpdate() {
            if (COUNT == MAX_COUNT) {
                return;
            }
            if (this.meshUpToDate) {
                return;
            }
            ++COUNT;
            this.meshUpToDate = true;
            mesher.pushVerticesToStacks(this.terrain, this.opaqueMesh, this.transparentMesh, this.opaqueTriangles,
                    this.transparentTriangles);
            mesher.setMeshVertices(this.opaqueMesh, this.opaqueTriangles);
            mesher.setMeshVertices(this.transparentMesh, this.transparentTriangles);
            this.opaqueTriangles.clear();
            this.transparentTriangles.clear();
            // this.opaqueMesh.cull(true);

            // TODO : can this be done more properly?
            // if there is transparent triangles, and camera moved
            // if (this.transparentTriangles.size() > 0 &&
            // !camera.getPosition().equals(this.lastCameraPos)
            // && this.timer.getTime() > 0.5d) {
            // this.timer.restart();
            // // sort them back to front of the camera
            // for (TerrainMeshTriangle triangle : this.transparentTriangles) {
            // triangle.calculateCameraDistance(camera);
            // }
            // this.transparentTriangles.sort(TRIANGLE_SORT);
            // // update vbo
            // mesher.setMeshVertices(this.transparentMesh,
            // this.transparentTriangles);
            // this.lastCameraPos.set(camera.getPosition());
            // }
        }
    }

}
