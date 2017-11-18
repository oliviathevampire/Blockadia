package net.thegaminghuskymc.sandboxgame.engine.items;

import net.thegaminghuskymc.sandboxgame.engine.graph.Material;
import net.thegaminghuskymc.sandboxgame.engine.graph.Mesh;
import net.thegaminghuskymc.sandboxgame.engine.graph.Texture;
import net.thegaminghuskymc.sandboxgame.engine.loaders.obj.OBJLoader;
import org.joml.Vector4f;

public class SkyBox extends GameItem {

    public SkyBox(String objModel, String textureFile) throws Exception {
        super();
        Mesh skyBoxMesh = OBJLoader.loadMesh(objModel);
        Texture skyBoxtexture = new Texture(textureFile);
        skyBoxMesh.setMaterial(new Material(skyBoxtexture, 0.0f));
        setMesh(skyBoxMesh);
        setPosition(0, 0, 0);
    }

    public SkyBox(String objModel, Vector4f colour) throws Exception {
        super();
        Mesh skyBoxMesh = OBJLoader.loadMesh(objModel);
        Material material = new Material(colour, 0);
        skyBoxMesh.setMaterial(material);
        setMesh(skyBoxMesh);
        setPosition(0, 0, 0);
    }
}
