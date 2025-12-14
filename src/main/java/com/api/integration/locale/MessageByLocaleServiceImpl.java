package com.api.integration.locale;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 *
 * Author: Kody Technolab Ltd. <br/>
 * Date : 13-May-2024
 */
@Component
@RequiredArgsConstructor
public class MessageByLocaleServiceImpl implements MessageByLocaleService {

	private final MessageSource messageSource;

	/**
	 * If no locale specified take English as local and display messages.
	 */
	@Override
	public String getMessage(final String id, final Object[] arg) {
		final Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(id, arg, locale);
	}

	/**
	 * New method: Get message by language code (e.g., "en", "fr", "ar", "ku").
	 */
	@Override
	public String getMessageByLangCode(final String id, String langCode, final Object[] args) {
		Locale locale = getLocaleFromLanguageCode(langCode);
		return messageSource.getMessage(id, args, locale);
	}

	/**
	 * Convert language code (e.g., "en", "fr", "ar", "ku") to Locale.
	 */
	private Locale getLocaleFromLanguageCode(String langCode) {
		return Locale.of(langCode);
	}
}
