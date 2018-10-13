package team.hdt.blockadia.engine.core_rewrite.util;

import com.google.common.collect.Maps;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Allows the ability to localize string by calling {@link I18n#format(String, Object...)}.
 * 
 * @author Ocelot5836
 */
public class I18n {

	private static ResourceBundle bundle = ResourceBundle.getBundle("assets/blockadia/lang/" + (Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry()).toLowerCase(), Locale.getDefault());
	private static Map<String, String> translated = Maps.<String, String>newHashMap();

	/**
	 * Gets the specified key from the language file selected.
	 * 
	 * @param key
	 *            The key of the string
	 * @param params
	 *            The same as used in {@link String#format(String, Object...)}
	 * @return Formats a string to the proper language
	 */
	public static String format(String key, Object... params) {
		try {
			if (translated.containsKey(key)) {
				return String.format(translated.get(key), params);
			}

			String formatted = bundle.getString(key);
			translated.put(key, formatted);
			return String.format(formatted, params);
		} catch (Exception e) {
		}

		return key;
	}

	/**
	 * Checks to see if the specified key has a translation available.
	 * 
	 * @param key
	 *            The key of the string in the language file
	 * @return Whether or not there is a translation for the key provided
	 */
	public static boolean contains(String key) {
		if (translated.containsKey(key))
			return true;

		return key != bundle.getString(key);
	}

	/**
	 * Sets the language to the specified Locale. Will default to english if there was no language file found for that locale
	 * 
	 * @param locale
	 *            The new language to get the languages from.
	 */
	public static void setLanguage(Locale locale) {
		try {
			bundle = ResourceBundle.getBundle("assets/blockadia/lang/" + (locale.getLanguage() + "_" + locale.getCountry()).toLowerCase(), locale);
		} catch (Exception e) {
			bundle = ResourceBundle.getBundle("assets/blockadia/lang/" + (Locale.ENGLISH.getLanguage() + "_" + Locale.ENGLISH.getCountry()).toLowerCase(), Locale.ENGLISH);
		}
		translated.clear();
	}
}