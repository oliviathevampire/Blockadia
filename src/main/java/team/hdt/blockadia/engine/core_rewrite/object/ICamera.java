package team.hdt.blockadia.engine.core_rewrite.object;


import ga.pheonix.utillib.utils.vectors.Vectors3f;

public interface ICamera {

	Vectors3f getPosition();

	Vectors3f getLastPosition();

	Vectors3f getRotation();

	Vectors3f getLastRotation();

	Vectors3f getScale();

	Vectors3f getDirection();
}