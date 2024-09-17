/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.adapters;

import java.util.Optional;

public interface URLRepository {

	String save(String url);

	Optional<String> findByCode(String code);
}
