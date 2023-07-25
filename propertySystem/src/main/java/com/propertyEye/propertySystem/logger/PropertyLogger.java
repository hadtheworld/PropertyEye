package com.propertyEye.propertySystem.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyLogger {
	public static Logger getLogger(Class<?> className) {
		return LoggerFactory.getLogger(className);
	}
}
