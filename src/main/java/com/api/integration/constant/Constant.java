package com.api.integration.constant;

import java.util.List;

/**
 *
 * @author: Kody Technolab Ltd. <br/>
 * @date    : 05-Jun-2024
 */
public final class Constant {

	public static final String PAGE_NUMBER = "1";
	public static final String PAGE_SIZE = "5";

	/**
	 * field size messages
	 */
	public static final int FIELD_SIZE_255 = 255;
	public static final String FIELD_SIZE_EXCEED_MESSAGE_255 = "{size.not.exceed.from.255}";
	public static final int FIELD_SIZE_15 = 15;
	public static final int FIELD_SIZE_7 = 7;
	public static final String FIELD_SIZE_EXCEED_MESSAGE_15 = "{size.not.exceed.from.15}";
	public static final String CONTACT_MESSAGE = "{size.contact.message}";
	public static final int FIELD_SIZE_60 = 60;
	public static final String FIELD_SIZE_EXCEED_MESSAGE_60 = "{size.not.exceed.from.60}";
	public static final int FIELD_SIZE_50000 = 50000;
	public static final String FIELD_SIZE_EXCEED_MESSAGE_50000 = "{size.not.exceed.from.50000}";
	public static final int FIELD_SIZE_5000 = 5000;
	public static final String FIELD_SIZE_EXCEED_MESSAGE_5000 = "{size.not.exceed.from.5000}";
	public static final int FIELD_SIZE_6 = 6;
	public static final String FIELD_SIZE_EXCEED_MESSAGE_6 = "{size.not.exceed.from.6}";
	public static final int FIELD_SIZE_8 = 8;
	public static final int FIELD_SIZE_16 = 16;

	public static final String SORT_DIRECTION_ASC = "ASC";
	public static final String SORT_DIRECTION_DESC = "DESC";

	public static final List<String> SORT_DIRECTION_LIST = List.of(SORT_DIRECTION_ASC, SORT_DIRECTION_DESC);

}
