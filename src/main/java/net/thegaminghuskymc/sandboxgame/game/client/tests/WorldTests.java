package net.thegaminghuskymc.sandboxgame.game.client.tests;

import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Maths;
import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.engine.world.WorldFlat;
import net.thegaminghuskymc.sandboxgame.game.client.GameEngineClient;
import net.thegaminghuskymc.sandboxgame.game.mod.Blocks;

public class WorldTests {

	public WorldTests() {

	}

	public void testWorld() {

		GameEngineClient engine = new GameEngineClient();
		engine.initialize();

		World world = new WorldFlat() {
			@Override
			public String getName() {
				return ("Test world");
			}
		};
		world.spawnTerrain(new Terrain(1, 0, 0));
		world.spawnTerrain(new Terrain(0, 0, 0));
		world.spawnTerrain(new Terrain(-1, 0, 0));
		world.setBlock(Blocks.DIRT, 0.0f, 1.0f, 0.0f);

		float e = Maths.ESPILON;
		/*Assert.assertEquals(world.getBlock(0.0f, 1.0f, 0.0f), Blocks.DIRT);
		Assert.assertEquals(world.getBlock(0.0f, 1.0f + e, 0.0f), Blocks.DIRT);
		Assert.assertEquals(world.getBlock(0.0f, 1.0f - e, 0.0f), Blocks.AIR);
		Assert.assertEquals(world.getBlock(e, 1.0f, e), Blocks.DIRT);
		Assert.assertEquals(world.getBlock(-e, 1.0f, e), Blocks.AIR);
		Assert.assertEquals(world.getBlock(e, 1.0f, -e), Blocks.AIR);
		Assert.assertEquals(world.getBlock(-e, 1.0f, -e), Blocks.AIR);
		Assert.assertEquals(world.getBlock(1.0f - e, 1.0f, 1.0f - e), Blocks.DIRT);*/

		engine.deinitialize();
	}
}
