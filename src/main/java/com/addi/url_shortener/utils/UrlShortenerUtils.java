/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.utils;

import static com.addi.url_shortener.utils.Constants.ALLOWED_CHARACTERS;

import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlShortenerUtils {

	public static String generateSubcode(int size) {
		var code = new StringBuilder();

		int value = size;
		while (value > 0) {
			int remainder = value % Constants.ALLOWED_CHARACTERS.length;
			value /= ALLOWED_CHARACTERS.length;
			code.append(ALLOWED_CHARACTERS[remainder]);
		}

		return code.reverse().toString();
	}

	public static StringBuilder generateCode() {
		Random random = new Random();

		return random.ints(0, ALLOWED_CHARACTERS.length - 1).limit(Constants.RECOMMENDED_LENGTH)
				.mapToObj((item) -> ALLOWED_CHARACTERS[item])
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
	}
}
