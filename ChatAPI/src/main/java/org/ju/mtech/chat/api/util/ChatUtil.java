package org.ju.mtech.chat.api.util;

public class ChatUtil {

	private ChatUtil() {
	}

	public static String dislayContent(final String name, final String content) {
		String displayChatContent = String.format("[%s]: %s", name,
				content.concat(System.getProperty("line.separator")));
		return displayChatContent;
	}
}
