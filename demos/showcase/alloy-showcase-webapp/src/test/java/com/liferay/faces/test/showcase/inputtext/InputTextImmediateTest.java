/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.test.showcase.inputtext;

import org.junit.Test;

import org.openqa.selenium.WebElement;

import static com.liferay.faces.test.showcase.AlloyShowcaseTestSuiteUtil.*;


/**
 * @author  Kyle Stiemann
 */
public class InputTextImmediateTest extends InputTextTester {

	@Test
	public void runInputTextImmediateTest() throws Exception {

		navigateToURL(inputTextURL + "immediate");

		// Wait to begin the test until the submit button is rendered.
		waitForElement(submitButtonXpath);

		// Test that the value submits successfully and the valueChangeListener method is called during the
		// APPLY_REQUEST_VALUES phase.
		WebElement input = getElement(inputXpath);
		String text = "Hello World!";
		input.sendKeys(text);
		click(submitButtonXpath);

		String immediateMessage = "//li[@class='text-info'][contains(text(),'APPLY_REQUEST_VALUES')]";
		waitForElement(immediateMessage);
		assertElementTextExists(modelValueXpath, text);
		assertElementExists(immediateMessage);

		// Test that the value submits successfully and the valueChangeListener method is called during the
		// PROCESS_VALIDATIONS phase.
		input = getElement(inputXpathRight);
		input.sendKeys(text);
		click(submitButtonXpathRight);

		String immediateMessageRight = "//li[@class='text-info'][contains(text(),'PROCESS_VALIDATIONS')]";
		waitForElement(immediateMessageRight);
		assertElementTextExists(modelValueXpathRight, text);
		assertElementExists(immediateMessageRight);
	}
}
