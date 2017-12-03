/**
**	This file is part of the project https://github.com/toss-dev/VoxelEngine
**
**	License is available here: https://raw.githubusercontent.com/toss-dev/VoxelEngine/master/LICENSE.md
**
**	PEREIRA Romain
**                                       4-----7          
**                                      /|    /|
**                                     0-----3 |
**                                     | 5___|_6
**                                     |/    | /
**                                     1-----2
*/

package net.thegaminghuskymc.sandboxgame.engine.block;


import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;

public class BlockLight extends BlockCubeOpaque {

	public BlockLight(int blockID) {
		super(blockID);
	}

	@Override
	public String getUnlocalizedName() {
		return null;
	}

	@Override
	public String getRegistryName() {
		return null;
	}

	@Override
	public void update(Terrain terrain, int x, int y, int z) {
		super.update(terrain, x, y, z);
	}

	public byte getLightValue() {
		return (15);
	}

	@Override
	public void onSet(Terrain terrain, int x, int y, int z) {
		terrain.addBlockLight(this.getLightValue(), x, y, z);
	}

	@Override
	public void onUnset(Terrain terrain, int x, int y, int z) {
		terrain.removeLight(x, y, z);
	}

}