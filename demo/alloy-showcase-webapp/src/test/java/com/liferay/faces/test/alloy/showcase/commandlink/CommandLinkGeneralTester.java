/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.test.alloy.showcase.commandlink;

import org.junit.Test;

import com.liferay.faces.test.alloy.showcase.buttonlink.ButtonLinkTester;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class CommandLinkGeneralTester extends ButtonLinkTester {

	@Test
	public void runCommandLinkGeneralTest() throws Exception {
		runButtonLinkGeneralTest("commandLink", generalLink1Xpath, generalLink2Xpath);
	}
}
