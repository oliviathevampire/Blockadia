package team.hdt.blockadia.game_engine.util.toolbox;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;

public class BinaryLineSearch {

    private final Vectors3f abovePoint;
    private final Vectors3f ray = new Vectors3f();
    private final int recursion;
    private final float aimheight;

    public BinaryLineSearch(Vectors3f abovePoint, Vectors3f belowPoint, int recursion, float aimHeight) {
        this.abovePoint = abovePoint;
        Vectors3f.sub(belowPoint, abovePoint, ray);
        this.recursion = recursion;
        this.aimheight = aimHeight;
    }

    public Vectors3f doSearch() {
        Vectors3f samplePos = binarySearch(0, 0, 1);
//		samplePos.y = GameManager.getWorld().getHeightOfTerrain(samplePos.x, samplePos.z);
        return samplePos;
    }

    private Vectors3f getPointOnRay(float distance) {
        Vectors3f scaledRay = new Vectors3f(ray.x * distance, ray.y * distance, ray.z * distance);
        return Vectors3f.add(abovePoint, scaledRay, null);
    }

    private Vectors3f binarySearch(int count, float start, float finish) {
        float half = start + ((finish - start) / 2f);
        if (count >= recursion) {
            return getPointOnRay(half);
        }
        if (intersectionInRange(start, half)) {
            return binarySearch(++count, start, half);
        } else {
            return binarySearch(++count, half, finish);
        }
    }

    private boolean intersectionInRange(float start, float finish) {
        Vectors3f startPoint = getPointOnRay(start);
        Vectors3f endPoint = getPointOnRay(finish);
        if (!isUnderHeight(startPoint) && isUnderHeight(endPoint)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isUnderHeight(Vectors3f testPoint) {
        float terrainHeight = /*GameManager.getWorld().getHeightOfTerrain(testPoint.x, testPoint.z)*/ 10.0F;
        return terrainHeight < aimheight;

    }

}
