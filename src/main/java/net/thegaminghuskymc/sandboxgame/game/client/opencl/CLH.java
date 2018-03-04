package net.thegaminghuskymc.sandboxgame.game.client.opencl;

import net.thegaminghuskymc.sandboxgame.engine.Logger;

import java.util.ArrayList;

//TODO , do we need opencl ?

public class CLH {

	/** lists of every allocated gl objects */
	private static ArrayList<CLObject> clObjects;

	/** initialize OpenCL */
	public static final void clhInit() {
		Logger.get().log(Logger.Level.FINE, "Initializing OpenCL.");
		clObjects = new ArrayList<CLObject>();
	}

	/** stop opencl properly */
	public static final void clhStop() {
		for (CLObject object : clObjects) {
			object.delete();
		}
		clObjects.clear();
	}

	/** check the open al error in the current context : print and returns it */
	public static int clhCheckError(String label, int err) {

		if (err == 0) {
			return (0);
		}

		Logger.get().log(Logger.Level.WARNING, label + " : OpenCL error occured : " + err);
		return (err);
	}

}
