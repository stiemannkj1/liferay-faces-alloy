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
public class InputTextValidationTest extends InputTextTester {

	@Test
	public void runInputTextValidationTest() throws Exception {

		navigateToURL(inputTextURL + "validation");

		// Wait to begin the test until the submit button is rendered.
		waitForElement(submitButtonXpath);

		// Test that the web page shows an error message when an invalid value is submitted.
		WebElement input = getElement(inputXpath);
		input.clear();

		String invalidText = "HelloWorldcom";
		input.sendKeys(invalidText);
		click(submitButtonXpath);
		waitForElement(errorXpath);
		assertElementExists(errorXpath);

		// Test that a valid value submits successfully.
		input = getElement(inputXpath);
		input.clear();

		String text = "Hello@World.com";
		input.sendKeys(text);
		click(submitButtonXpath);
		waitForElementText(modelValueXpath, text);
		assertElementTextExists(modelValueXpath, text);

		// Test that the web page shows an error message when an invalid value is submitted.
		input = getElement(inputXpathRight);
		input.clear();
		input.sendKeys(invalidText);
		click(submitButtonXpathRight);
		waitForElement(errorXpath);
		assertElementExists(errorXpath);

		// Test that a valid value submits successfully.
		input = getElement(inputXpathRight);
		input.clear();
		input.sendKeys(text);
		click(submitButtonXpathRight);
		waitForElementText(modelValueXpathRight, text);
		assertElementTextExists(modelValueXpathRight, text);
	}
}
