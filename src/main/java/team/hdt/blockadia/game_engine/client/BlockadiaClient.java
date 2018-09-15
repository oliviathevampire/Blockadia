package team.hdt.blockadia.game_engine.client;

import team.hdt.blockadia.game_engine.core.Display;
import team.hdt.blockadia.game_engine.core.entity.BaseEntity;
import team.hdt.blockadia.game_engine.core.util.GameSide;
import team.hdt.blockadia.game_engine.core.util.GameSideOnly;
import team.hdt.blockadia.game_engine_old.client.Camera;
import team.hdt.blockadia.game_engine_old.client.model.RawModel;
import team.hdt.blockadia.game_engine_old.client.rendering.ModelTexture;
import team.hdt.blockadia.game_engine_old.client.rendering.TexturedModel;
import team.hdt.blockadia.game_engine_old.common.Loader;
import team.hdt.blockadia.game_engine_old.common.init.Bootstrap;

import java.util.ArrayList;
import java.util.List;

@GameSideOnly(GameSide.CLIENT)
public class BlockadiaClient extends MainExtras {

    public static List<BaseEntity> Entites = new ArrayList<BaseEntity>();
    public static List<BaseEntity> entities = new ArrayList<>();
	public static boolean canStart = true;
	public static Client client;
	public static ListenFromServer serverData;
	public static final String server = "";
	public static final int port = "";
	public static final String userName = "";
	public static final String password = "";
	public static final float version = "";

    public static void main(String[] args) {
        Display display = new Display("Blockadia", getWidth(), getHeight());
		public String ServerResponce;
		public Stirng ClientToSend = userName + ":" + password;
		client  = new Client(server, portNumber, userName, password, version);
		client.start();
		client.sendMessage(new PacketHandler(PacketHandler.MESSAGE, ClientToSend));
		//finish server sender
		if(canStart){
        display.run();
        Camera.create();
        Bootstrap.register();
		}else{
			Syste.out.printl("can not authenticate");
			System.exit(1);
		}
    }
    public static void gameRender(){
        Loader loader = new Loader();
        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22

        };
        float[] vertices = {
                -0.5f,0.5f,0,
                -0.5f,-0.5f,0,
                0.5f,-0.5f,0,
                0.5f,0.5f,0,

                -0.5f,0.5f,1,
                -0.5f,-0.5f,1,
                0.5f,-0.5f,1,
                0.5f,0.5f,1,

                0.5f,0.5f,0,
                0.5f,-0.5f,0,
                0.5f,-0.5f,1,
                0.5f,0.5f,1,

                -0.5f,0.5f,0,
                -0.5f,-0.5f,0,
                -0.5f,-0.5f,1,
                -0.5f,0.5f,1,

                -0.5f,0.5f,1,
                -0.5f,0.5f,0,
                0.5f,0.5f,0,
                0.5f,0.5f,1,

                -0.5f,-0.5f,1,
                -0.5f,-0.5f,0,
                0.5f,-0.5f,0,
                0.5f,-0.5f,1

        };
        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0
        };
        RawModel model = loader.loadToVAO(vertices,textureCoords,indices);
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("grass")));
        int id = 4;
        BaseEntity entity = new BaseEntity(staticModel, id, false);
        entities.add(entity);
    }
    /*
     * |=========================================|
     * |please read TODO list before start coding|
     * |=========================================|
     */

}