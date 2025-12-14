package com.api.integration.locale;

/**
 *
 * Author: Kody Technolab Ltd. <br/>
 * Date : 09-May-2024
 */
public interface MessageByLocaleService {

	String getMessage(String id, Object[] arg);

	/**
	 * New method: Get message by language code (e.g., "en", "fr", "ar", "ku").
	 */
	String getMessageByLangCode(String id, String langCode, Object[] args);
}
