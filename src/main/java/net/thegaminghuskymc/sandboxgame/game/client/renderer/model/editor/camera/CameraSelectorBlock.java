package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.camera;

import net.thegaminghuskymc.sandboxgame.engine.faces.Face;
import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.engine.util.math.*;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntity;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.CameraPicker;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.Raycasting;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.camera.RaycastingCallback;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.gui.event.GuiEventMouseScroll;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.lines.LineRendererFactory;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.gui.GuiModelView;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModelLayer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance.ModelInstance;

public class CameraSelectorBlock extends CameraSelector {

	protected final Vector3i hovered;
	private final Vector3i firstBlock;
	private final Vector3i secondBlock;
	private Face face;
	private int expansion;

	public CameraSelectorBlock(GuiModelView guiModelView, Color color) {
		super(guiModelView, color);
		this.hovered = new Vector3i();
		this.firstBlock = new Vector3i();
		this.secondBlock = new Vector3i();
		this.expansion = 0;
	}

	@Override
	public void update() {
		LineRendererFactory lines = this.getGuiModelView().getWorldRenderer().getLineRendererFactory();
		lines.removeAllLines();
		lines.addBox(this, this, this.getSelectorColor());
	}

	private final void updateSelection() {
		if (!super.isLeftPressed()) {
			this.firstBlock.set(this.hovered);
		}
		this.updateSecondBlock();
	}

	private final void updateCameraRotation() {
		// rotate
		if (super.isRightPressed()) {
			super.getCamera().increaseTheta(-(super.getMouseDY()) * 1.5f);
			super.getCamera().increasePhi((super.getMouseDX()) * 1.5f);
		} else {
			this.updateHoveredBlock();
		}
	}

	@Override
	public void onMouseScroll(GuiEventMouseScroll<GuiModelView> event) {
		if (super.isLeftPressed()) {
			int d = -Maths.sign(event.getScrollY());
			this.expand(d);
		} else {
			float speed = super.getCamera().getR() * 0.14f;
			super.getCamera().increaseR((float) (-event.getScrollY() * speed));
		}
	}

	private void expand(int d) {
		this.expansion += d;
		this.updateSecondBlock();
	}

	private final void updateSecondBlock() {
		if (this.face == null) {
			this.secondBlock.set(this.hovered);
			return;
		}
		int x = this.hovered.x + this.expansion * this.face.getVector().x;
		int y = this.hovered.y + this.expansion * this.face.getVector().y;
		int z = this.hovered.z + this.expansion * this.face.getVector().z;
		this.secondBlock.set(x, y, z);
	}

	@Override
	public void onMouseMove() {
		this.updateCameraRotation();
		this.updateSelection();
	}

	private final void updateHoveredBlock() {

		ModelInstance modelInstance = super.getSelectedModelInstance();
		EditableModelLayer modelLayer = super.getSelectedModelLayer();

		// extract objects
		if (modelInstance == null || modelLayer == null) {
			return;
		}

		WorldEntity entity = modelInstance.getEntity();
		float s = modelLayer.getBlockSizeUnit();
		ModelEditorCamera camera = (ModelEditorCamera) this.getCamera();

		// origin relatively to the model
		Vector4f origin = new Vector4f(camera.getPosition(), 1.0f);
		Matrix4f transform = new Matrix4f();
		transform.translate(-entity.getPositionX(), -entity.getPositionY(), -entity.getPositionZ());
		transform.scale(1 / s);
		Matrix4f.transform(transform, origin, origin);

		// ray relatively to the model
		Vector3f ray = new Vector3f();
		CameraPicker.ray(ray, camera, super.getMouseX(), super.getMouseY());

		Vector3i pos = new Vector3i();
		Raycasting.raycast(origin.x, origin.y, origin.z, ray.x, ray.y, ray.z, 256.0f, 256.0f, 256.0f,
				new RaycastingCallback() {
					@Override
					public boolean onRaycastCoordinates(int x, int y, int z, Vector3i faceNormal) {
						// System.out.println(x + " : " + y + " : " + z);
						if (z < 0 || modelLayer.getBlockData(pos.set(x, y, z)) != null) {
							hovered.set(x, y, z);
							face = Face.fromVec(faceNormal);
							return (true);
						}
						return (false);
					}
				});
	}

	@Override
	public void onLeftPressed() {
		this.expansion = 0;
	}

	@Override
	public void onRightPressed() {
		ModelEditorCamera camera = this.getCamera();
		camera.getWindow().setCursor(false);
		camera.getWindow().setCursorCenter();

		float cx = this.getCamera().getPosition().x;
		float cy = this.getCamera().getPosition().y;
		float cz = this.getCamera().getPosition().z;

		float cphi = this.getCamera().getPhi();
		float ctheta = this.getCamera().getTheta();
		// float cr = this.getCamera().getR();

		float u = this.getBlockSizeUnit();

		float x = (this.firstBlock.x + 0.5f) * u;
		float y = (this.firstBlock.y + 0.5f) * u;
		float z = (this.firstBlock.z + 0.5f) * u;

		// float dx = x - this.getCamera().getCenter().getX();
		// float dy = y - this.getCamera().getCenter().getY();
		// float dz = z - this.getCamera().getCenter().getZ();
		//
		// float A = (float) (-dx / cr + Math.cos(ctheta) * Math.sin(cphi));
		// float B = (float) (-dy / cr + Math.cos(ctheta) * Math.cos(cphi));
		//
		// float theta = (float) Math.asin(Math.sin(ctheta) - dz / cr);
		// float phi = (float) 0.0f;

		float phi = cphi;
		float theta = ctheta;
		float r = (float) Vector3f.distance(x, y, z, cx, cy, cz);

		this.getCamera().setCenter(x, y, z);
		this.getCamera().setR(r);
		this.getCamera().setPhi(phi);
		this.getCamera().setTheta(theta);
	}

	@Override
	public void onRightReleased() {
		this.getCamera().getWindow().setCursor(true);
		this.getCamera().getWindow().setCursorCenter();
	}

	@Override
	public void onLeftReleased() {
		this.expansion = 0;
	}

	@Override
	public Vector3i getBlock() {
		return (this.getFirstBlock());
	}

	@Override
	public final Face getFace() {
		return (this.face);
	}

	public Vector3i getFirstBlock() {
		return (this.firstBlock);
	}

	public Vector3i getSecondBlock() {
		return (this.secondBlock);
	}

	@Override
	public final int getX() {
		return (Maths.min(this.firstBlock.x, this.secondBlock.x));
	}

	@Override
	public final int getY() {
		return (Maths.min(this.firstBlock.y, this.secondBlock.y));
	}

	@Override
	public final int getZ() {
		return (Maths.min(this.firstBlock.z, this.secondBlock.z));
	}

	@Override
	public final int getWidth() {
		return (Maths.abs(this.firstBlock.x - this.secondBlock.x) + 1);
	}

	@Override
	public final int getDepth() {
		return (Maths.abs(this.firstBlock.y - this.secondBlock.y) + 1);
	}

	@Override
	public final int getHeight() {
		return (Maths.abs(this.firstBlock.z - this.secondBlock.z) + 1);
	}
}
