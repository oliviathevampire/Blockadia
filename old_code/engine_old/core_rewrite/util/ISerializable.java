package team.hdt.blockadia.engine.core_rewrite.util;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Allows a class to be serialized into an object and back out of said object.
 * 
 * @author Ocelot5836
 *
 * @param <T>
 *            The type of data that will serialize/deserialize this class
 */
public interface ISerializable<T> {

	/**
	 * Serializes this class.
	 * 
	 * @return The object that contains data
	 */
	T serialize();

	/**
	 * Deserializes this class.
	 * 
	 * @param data
	 *            The object that contains data
	 */
	void deserialize(T data);
}