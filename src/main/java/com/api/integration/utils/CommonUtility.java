package com.api.integration.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.api.integration.constant.Constant;
import com.api.integration.repo.CustomJpaRepository;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * Author: Kody Technolab Ltd. <br/>
 * Date : 13-May-2024
 */
@Slf4j
public class CommonUtility {

	private CommonUtility() {
	}

	/**
	 * Generate random number for OTP
	 *
	 * @return
	 */
	public static int getRandomNumber() {
		final SecureRandom number = new SecureRandom();
		return 100000 + number.nextInt(899999);
	}

	public static String getRandomAlphaNumericNumber() {
		final SecureRandom number = new SecureRandom();
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder sb = new StringBuilder(20);
		for (int i = 0; i < 19; i++) {
			if ((i + 1) % 5 == 0) {
				sb.append("-");
			} else {
				sb.append(alphaNumeric.charAt(number.nextInt(alphaNumeric.length())));
			}
		}
		return sb.toString();
	}

	public static String getRandomAlphaNumericNumber(final int length) {
		final SecureRandom number = new SecureRandom();
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(alphaNumeric.charAt(number.nextInt(alphaNumeric.length())));
		}
		return sb.toString();
	}

	/**
	 * generic UUID generation from any repository <br/>
	 *
	 * @date         : 17th Nov 2023
	 *
	 * @param  clazz
	 * @return
	 */
	public static String generateUuid(final CustomJpaRepository<?> clazz) {
		String uuid = CommonUtility.getRandomAlphaNumericNumber();
		if (clazz.findByUuid(uuid).isPresent()) {
			return generateUuid(clazz);
		} else {
			return uuid;
		}
	}

	/**
	 * validate SortByField Name with multiple classes
	 *
	 * @param  sortByDirection
	 * @param  sortByField
	 * @return
	 */
	public static boolean validateSortByFieldName(final String sortByField, final List<Class<?>> clazzList) {
		if (StringUtils.isBlank(sortByField)) {
			return false;
		}
		for (Class<?> clazz : clazzList) {
			while (clazz != null) {
				if (Arrays.asList(clazz.getDeclaredFields()).stream().anyMatch(field -> sortByField.equals(field.getName()))) {
					return true;
				}
				clazz = clazz.getSuperclass();
			}
		}
		return false;
	}

	/**
	 * validate sortByDirection
	 *
	 * @param  sortByDirection
	 * @return
	 */
	public static boolean validatesortByDirection(final String sortByDirection) {
		if (StringUtils.isBlank(sortByDirection)) {
			return false;
		}
		return Constant.SORT_DIRECTION_LIST.contains(sortByDirection);
	}

}
