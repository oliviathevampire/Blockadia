package net.timmy.timmylib.api;

import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

public interface IModelRegister {
	/**
	 * Do whatever needs to be done to register models
	 * For Blocks, statemapper registration needs to be done BEFORE itemblock registration
	 * Exceptions to that rule are noted
	 */
	@SideOnly(Side.CLIENT)
	void registerModels();
}