/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.alloy.component.column.internal;

import java.io.IOException;

import com.liferay.faces.alloy.component.column.Column;


/**
 * @author  Kyle Stiemann
 */
public abstract class ColumnRendererCompat extends ColumnRendererBase {

	protected String getSpanClassNamePrefix(Column column, boolean offset) throws IOException {

		StringBuilder stringBuilder = new StringBuilder();
		String size = column.getSize();
		size = getColumnCSSClassSize(size);
		stringBuilder.append("col-");
		stringBuilder.append(size);

		if (offset) {
			stringBuilder.append("-offset-");
		}
		else {
			stringBuilder.append("-");
		}

		return stringBuilder.toString();
	}

	private String getColumnCSSClassSize(String size) throws IOException {

		if ("extra-small".equals(size) || "xs".equals(size)) {
			size = "xs";
		}
		else if ("small".equals(size) || "sm".equals(size)) {
			size = "sm";
		}
		else if ("medium".equals(size) || "md".equals(size)) {
			size = "md";
		}
		else if ("large".equals(size) || "lg".equals(size)) {
			size = "lg";
		}
		else {
			throw new IOException(size + " is not a valid value for size.");
		}

		return size;
	}
}
