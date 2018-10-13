package team.hdt.blockadia.engine.core_rewrite.util;

import team.hdt.blockadia.engine.core_rewrite.Blockadia;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * This class can load images and text easily instead of having to do it manually. It also saves images that are loaded that can be easily accessed by
 * 
 * @author Ocelot5836
 * @since 1.0
 */
public class LoadingUtils {

	/** The list of images loaded into memory. */
	private static final Map<String, BufferedImage> LOADED_IMAGES = new HashMap<String, BufferedImage>();

	/** The default empty image that is 8x8 */
	public static final BufferedImage EMPTY_IMAGE8 = createMissingImage(8, 8);
	/** The default empty image that is 16x16 */
	public static final BufferedImage EMPTY_IMAGE16 = createMissingImage(16, 16);
	/** The default empty image that is 32x32 */
	public static final BufferedImage EMPTY_IMAGE32 = createMissingImage(32, 32);
	/** The default empty image that is 64x64 */
	public static final BufferedImage EMPTY_IMAGE64 = createMissingImage(64, 64);
	/** The image that is used in the place of an image if an image can not be found */
	public static BufferedImage defaultEmptyImage = createMissingImage(256, 256);

	/**
	 * Loads text into an array from the resources.
	 * 
	 * @param path
	 *            The path to the file
	 * @return The text from the file in the form as an array
	 */
	public static String[] loadTextToArray(String path) {
		return loadTextToArray(Class.class.getResourceAsStream(path));
	}

	/**
	 * Loads text into an array from the resources.
	 * 
	 * @param input
	 *            The path to the file
	 * @return The text from the file in the form as an array
	 */
	public static String[] loadTextToArray(InputStream input) {
		ArrayList<String> lines = new ArrayList<String>();
		String[] result = new String[0];
		try {
			Scanner scanner = new Scanner(input);

			while (scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}
			scanner.close();
		} catch (Exception e) {
			Blockadia.logger().warn("Error loading text to array!");
			e.printStackTrace();
		}
		result = new String[lines.size()];

		for (int i = 0; i < lines.size(); i++) {
			result[i] = lines.get(i);
		}

		return result;
	}

	/**
	 * Loads text from a website.
	 * 
	 * @param pageURL
	 *            The path to the file
	 * @return The text from the file in the form as an array
	 */
	public static String[] loadTextToArrayFromURL(String pageURL) {
		try {
			return loadTextToArrayFromURL(new URL(pageURL));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return new String[0];
	}

	/**
	 * Loads text from a website.
	 * 
	 * @param pageURL
	 *            The path to the file
	 * @return The text from the file in the form as an array
	 */
	public static String[] loadTextToArrayFromURL(URL pageURL) {
		ArrayList<String> lines = new ArrayList<String>();
		String[] result = new String[0];
		try {
			Scanner scanner = new Scanner(pageURL.openStream());

			while (scanner.hasNext()) {
				lines.add(scanner.nextLine());
			}
			scanner.close();
		} catch (Exception e) {
			Blockadia.logger().warn(String.format("Error loading text to array from %s!", pageURL));
			e.printStackTrace();
		}
		result = new String[lines.size()];

		for (int i = 0; i < lines.size(); i++) {
			result[i] = lines.get(i);
		}

		return result;
	}

	/**
	 * Loads an image into memory if it is not already loaded. This method can be called by itself to add an image to memory or receive the image directly.
	 * 
	 * @param name
	 *            The name of the image to save into memory
	 * @param path
	 *            The path to the file
	 * @return The image returned by {@link ImageIO}
	 */
	public static BufferedImage loadImage(String name, String path) {
		return loadImage(name, Class.class.getResourceAsStream(path));
	}

	/**
	 * Loads an image into memory if it is not already loaded. This method can be called by itself to add an image to memory or receive the image directly.
	 * 
	 * @param name
	 *            The name of the image to save into memory
	 * @param input
	 *            The path to the file
	 * @return The image returned by {@link ImageIO}
	 */
	public static BufferedImage loadImage(String name, InputStream input) {
		BufferedImage image = null;
		if (isImageLoaded(name))
			return LOADED_IMAGES.get(name);

		try {
			image = ImageIO.read(input);
		} catch (Exception e) {
			Blockadia.logger().warn(String.format("Missing image: %s", input));
			image = defaultEmptyImage;
		}

		LOADED_IMAGES.put(name, image);
		return image;
	}

	/**
	 * Gets the image specified from memory.
	 * 
	 * @param name
	 *            The image to search for
	 * @return The image found in memory
	 */
	public static BufferedImage getImage(String name) {
		if (isImageLoaded(name))
			return LOADED_IMAGES.get(name);
		Blockadia.logger().fatal(String.format("You cannot get an image that has not been loaded! The identifier of the image was %s", name));
		return null;
	}

	/**
	 * This creates an image in case an image isn't present. The image is a purple and black checker board pattern.
	 * 
	 * @param width
	 *            The width of the missing image
	 * @param height
	 *            The height of the missing image
	 * 
	 * @return The image created
	 */
	public static BufferedImage createMissingImage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		if (isImageLoaded("missing" + width + "x" + height))
			return LOADED_IMAGES.get("missing" + width + "x" + height);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if ((x < width / 2 && y < height / 2) || (x >= width / 2 && y >= height / 2)) {
					image.setRGB(x, y, 0);
				} else {
					image.setRGB(x, y, 0xff00ff);
				}
			}
		}
		LOADED_IMAGES.put("missing" + width + "x" + height, image);
		return image;
	}

	/**
	 * Searches {@link #LOADED_IMAGES} for the image specified.
	 * 
	 * @param key
	 *            The key to look for the image
	 * @return Whether or not the image was loaded
	 */
	public static boolean isImageLoaded(String key) {
		return LOADED_IMAGES.containsKey(key);
	}

	/**
	 * @return All the loaded images inside of a new map
	 */
	public static Map<String, BufferedImage> getLoadedImages() {
		return new HashMap<String, BufferedImage>(LOADED_IMAGES);
	}
}