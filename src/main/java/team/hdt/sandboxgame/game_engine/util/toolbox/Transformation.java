package team.hdt.sandboxgame.game_engine.util.toolbox;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Matrix4fs;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Transformation extends Component {

	private static final int SUITABLE_LOC_ATTEMPTS = 10;

	private final TransformBlueprint blueprint;

	private Vectors3f position;
	private float rotX, rotY, rotZ = 0;
	private FloatTrait scaleTrait;
	private float currentScale;
	private boolean dirty = true;
	private Matrix4fs modelMatrix = new Matrix4fs();
	private List<TransformChangeListener> listeners = new ArrayList<TransformChangeListener>();
	private Entity entity;

	public Transformation(TransformBlueprint blueprint) {
		super(blueprint);
		this.blueprint = blueprint;
	}

	public TransformBlueprint getBlueprint() {
		return blueprint;
	}

	@Override
	public void create(ComponentBundle bundle) {
		this.entity = bundle.getEntity();
		TransformParams params = (TransformParams) bundle.getParameters(ComponentType.TRANSFORM);
		if (params == null) {
			this.position = new Vectors3f(0, 0, 0);
		} else {
			this.position = params.position;
			this.rotY = params.rotY;
		}
		this.scaleTrait = (FloatTrait) getTrait(0);
		this.currentScale = scaleTrait.value;
	}

	@Override
	public void load(ComponentBundle bundle, BinaryReader reader) throws Exception {
		this.entity = bundle.getEntity();
		this.position = reader.readVector();
		this.rotX = reader.readFloat();
		this.rotY = reader.readFloat();
		this.rotZ = reader.readFloat();
		this.scaleTrait = (FloatTrait) getTrait(0);
		this.currentScale = scaleTrait.value;
	}

	public void addChangeListener(TransformChangeListener listener) {
		listeners.add(listener);
	}

	public void setPosition(float x, float y, float z) {
		this.position.set(x, y, z);
		indicateChanged();
	}

	public void setXPosition(float x) {
		this.position.x = x;
		indicateChanged();
	}

	public void setYPosition(float y) {
		this.position.y = y;
		indicateChanged();
	}

	public void setZPosition(float z) {
		this.position.z = z;
		indicateChanged();
	}

	public void setPosition(Vectors3f newPos) {
		this.position.set(newPos);
		indicateChanged();
	}

	public void setXRotation(float xRot) {
		this.rotX = xRot;
		indicateChanged();
	}

	public void returnXRotToZero(float changePerSec){
		if(rotX == 0){
			return;
		}
		this.rotX = returnToZero(rotX, changePerSec, GameManager.getGameSeconds());
		indicateChanged();
	}
	
	public void setYRotation(float yRot) {
		this.rotY = yRot % 360;
		indicateChanged();
	}

	public void setZRotation(float zRot) {
		this.rotZ = zRot;
		indicateChanged();
	}

	public float getScale() {
		return currentScale;
	}

	public void setScale(float scale) {
		this.currentScale = scale;
		indicateChanged();
	}

	public FloatTrait getScaleTrait() {
		return scaleTrait;
	}

	public Vectors3f getPosition() {
		return position;
	}

	/**
	 * Checks if the entity is under the terrain, and if so clamps it to the
	 * terrain.
	 * 
	 * @return The height of the entity above the terrain.
	 */
	public float checkWithTerrain() {
		float height = getTerrainHeight();
		return testEntityAltitude(height);
	}

	public float checkWithTerrainAndWater() {
		float height = getTerrainOrWaterHeight();
		return testEntityAltitude(height);
	}

	private float testEntityAltitude(float height) {
		float entityHeight = position.y - height;
		if (entityHeight <= 0) {
			position.y = height;
			dirty = true;
		}
		return entityHeight;
	}
	
	private static float returnToZero(float current, float changePerSec, float delta){
		float difference = 0 - current;
		float maxAllowedChange = changePerSec * delta;
		if(Math.abs(difference) <= maxAllowedChange){
			return 0;
		}else{
			current += maxAllowedChange * Math.signum(difference);
			return current;
		}
	}

	public float getTerrainHeight() {
		return GameManager.getWorld().getHeightOfTerrain(position.x, position.z);
	}

	public float getTerrainOrWaterHeight() {
		float terrainHeight = GameManager.getWorld().getHeightOfTerrain(position.x, position.z);
		return Math.max(terrainHeight, GameManager.getWorld().getWaterHeight());
	}

	public void clampToTerrain(float offset) {
		float height = GameManager.getWorld().getHeightOfTerrain(position.x, position.z);
		position.y = height + offset;
		dirty = true;
	}

	public float getRotX() {
		return rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setModelMatrix(Matrix4fs modelMat) {
		this.modelMatrix.load(modelMat);
		dirty = false;
	}

	public void updateModelMatrix(Vectors3f up, Vectors3f forward) {
		Matrix4fs rotation = Maths.getRotationMatrix(up, forward);
		modelMatrix.setIdentity();
		Matrix4fs.translate(position, modelMatrix, modelMatrix);
		Matrix4fs.mul(modelMatrix, rotation, modelMatrix);
		Matrix4fs.scale(new Vectors3f(currentScale, currentScale, currentScale), modelMatrix, modelMatrix);
		dirty = false;
	}

	public Matrix4fs getModelMatrix() {
		if (dirty) {
			Maths.updateTransformationMatrix(modelMatrix, position.x, position.y, position.z, rotX, rotY, rotZ,
					currentScale);
			dirty = false;
		}
		return modelMatrix;
	}

	public void increasePosition(float dx, float dy, float dz) {
		position.x += dx;
		position.y += dy;
		position.z += dz;
		indicateChanged();
	}

	public void increasePosition(Vectors3f change) {
		Vectors3f.add(position, change, position);
		indicateChanged();
	}

	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
		indicateChanged();
	}

	@Override
	public void getStatusInfo(List<PopUpInfoGui> info) {
		// info.add(new ComponentInfoGui("Size",
		// Maths.toStringWithLimitedDigits(scale, 3), ColourPalette.WHITE));
	}

	@Override
	public boolean reproduce(ParamsBundle params, boolean boosted) {
		ComponentParams param = calculateSuitableTransform(boosted);
		if (param == null) {
			return false;
		} else {
			params.addParams(param);
			return true;
		}
	}

	@Override
	public void getActions(List<Action> actions) {

	}

	@Override
	public void export(BinaryWriter writer) {
		writer.writeVector(position);
		writer.writeFloat(rotX);
		writer.writeFloat(rotY);
		writer.writeFloat(rotZ);
	}

	private void indicateChanged() {
		dirty = true;
		for (TransformChangeListener listener : listeners) {
			listener.transformChanged();
		}
	}

	public TransformParams calculateSuitableTransform(boolean select) {
		Vectors3f pos = getSuitableSpawnLocation(true);
		if (pos == null) {
			return null;
		}
		return new TransformParams(pos, (float) (Math.random() * 360), this, select);
	}

	public Vectors3f getSuitableSpawnLocation(boolean canReturnNull) {
		if (entity.getBlueprint().getClassification().isTypeOf(Classifier.getAnimalClassification())) {
			Vectors3f pos = new Vectors3f(this.position);
			pos.y = GameManager.getWorld().getHeightOfTerrain(pos.x, pos.z);
			return pos;
		}
		InformationComponent info = (InformationComponent) entity.getComponent(ComponentType.INFO);
		Vectors3f pos = new Vectors3f();
		int count = 0;
		do {
			pos = info.getEvenInRangePoint();
			if (GameManager.getWorld().isAcceptableLocation(entity.getBlueprint(), pos.x,
					pos.z) == UnplaceableReason.NO_PROBLEM) {
				pos.y = GameManager.getWorld().getHeightOfTerrain(pos.x, pos.z);
				return pos;
			}
			count++;
		} while (count < SUITABLE_LOC_ATTEMPTS);
		return canReturnNull ? null : pos;
	}

	public Vectors3f generateSpawnLocation(boolean canReturnNull) {
		if (entity.getBlueprint().getClassification().isTypeOf(Classifier.getAnimalClassification())) {
			return getAnimalSpawnLocation();
		}
		InformationComponent info = (InformationComponent) entity.getComponent(ComponentType.INFO);

		Vector2f pos = new Vector2f();
		int count = 0;
		float theta = (float) (Maths.RANDOM.nextFloat() * Math.PI * 2f);
		float thetaIncrease = (float) ((Math.PI * 2f) / SUITABLE_LOC_ATTEMPTS);
		do {
			pos = info.generateInRadiusRangePoint(theta);
			if (pos != null && GameManager.getWorld().isAcceptableLocation(entity.getBlueprint(), pos.x,
					pos.y) == UnplaceableReason.NO_PROBLEM) {
				float height = GameManager.getWorld().getHeightOfTerrain(pos.x, pos.y);
				return new Vectors3f(pos.x, height, pos.y);
			}
			count++;
			theta += thetaIncrease;
		} while (count < SUITABLE_LOC_ATTEMPTS);
		if (canReturnNull) {
			return null;
		} else{
			Vectors3f spawnPos = new Vectors3f(info.getBasePosition());
			spawnPos.y = GameManager.getWorld().getHeightOfTerrain(spawnPos.x, spawnPos.z);
			return spawnPos;
		}

	}

	private Vectors3f getAnimalSpawnLocation() {
		Vectors3f pos = new Vectors3f(this.position);
		pos.y = GameManager.getWorld().getHeightOfTerrain(pos.x, pos.z);
		return pos;
	}

	public static class TransformParams extends ComponentParams {

		public final Vectors3f position;
		public final float rotY;

		public TransformParams(Vectors3f pos, float rotY, Component component, boolean selected) {
			super(component, selected);
			this.position = new Vectors3f(pos);
			this.rotY = rotY;
		}

		public TransformParams(Vectors3f pos, float rotY, FloatTrait scaleTrait) {
			super(ComponentType.TRANSFORM, scaleTrait);
			this.position = new Vectors3f(pos);
			this.rotY = rotY;
		}

	}

	public static class TransformBlueprint extends ComponentBlueprint {

		private static final String SIZE = GameText.getText(898);
		public static final float STANDARD_SCALE = 0.1f;

		private FloatTraitBlueprint sizeBlueprint = new FloatTraitBlueprint(SIZE, STANDARD_SCALE, 20000) {

			@Override
			public String formatTrait(float value) {
				return "x" + String.format("%.2f", value * 10);
			}

		};

		public TransformBlueprint() {
			super(ComponentType.TRANSFORM);
			super.addTrait(sizeBlueprint);
		}

		@Override
		public Component createInstance() {
			return new Transformation(this);
		}

		public FloatTrait generateRandomScale() {
			return sizeBlueprint.createRandomInstance();
		}

		@Override
		public void getInfo(Map<SpeciesInfoType, List<SpeciesInfoLine>> info) {

		}

		@Override
		public void delete() {
		}

	}

	public static class TransformLoader implements ComponentLoader {

		private static final float MIN = 0.005f;
		private static final String TRAIT_TEXT = GameText.getText(252);

		@Override
		public ComponentBlueprint load(CSVReader reader, Blueprint blueprint) {
			return new TransformBlueprint();
		}

		@Override
		public Requirement loadRequirement(CSVReader reader) {
			final float targetSize = reader.getNextLabelFloat();
			return new Requirement() {

				@Override
				public boolean check(Entity entity) {
					float difference = targetSize - (entity.getTransform().getScaleTrait().value * 10);
					// ASUM only works if always larger size wanted
					return difference <= MIN;
				}

				@Override
				public void getGuiInfo(List<ReqInfo> components) {
					components.add(new ReqInfo(TRAIT_TEXT, String.format("%.2f", targetSize) + "x"));
				}

				@Override
				public boolean isSecret() {
					return false;
				}
			};
		}

	}

}
