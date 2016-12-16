/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.test.alloy.showcase.buttonlink;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;
import com.liferay.faces.test.showcase.TesterBase;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class ButtonLinkTester extends TesterBase {

	protected static final String generalButton1Xpath =
		"//label[contains(.,'Example')][contains(.,'children')]/ancestor::div[@class='showcase-example']//button//img[contains(@src,'liferay-faces-logo-small.png')][contains(@src,'ln=images') or contains(@src,'ln:images')]";
	protected static final String generalButton2Xpath =
		"//label[contains(.,'Example')][contains(.,'image')]/ancestor::div[@class='showcase-example']//button//img[contains(@src,'liferay-faces-logo-small.png')][contains(@src,'ln=images') or contains(@src,'ln:images')]";
	protected static final String generalButtonValueXpath =
		"//label[contains(.,'Example')][contains(.,'value')]/ancestor::div[@class='showcase-example']//button[@type='button' or @value='1234']";
	protected static final String generalLink1Xpath =
		"//img[contains(@src,'liferay-faces-logo-small.png')]/parent::a[contains(.,'Text for a link')]";
	protected static final String generalLink2Xpath =
		"//a[contains(.,'Text for a link')][not(contains(@src,'liferay-faces-logo-small.png'))]";

	protected void runButtonLinkGeneralTest(boolean button, String componentName, String buttonLink1xpath,
		String buttonLink2xpath) throws Exception {

		Browser browser = Browser.getInstance();
		navigateToUseCase(browser, componentName, "general");

		// Test that buttons/links render on the page visibly and are clickable.

		SeleniumAssert.assertElementVisible(browser, buttonLink1xpath);
		SeleniumAssert.assertElementVisible(browser, buttonLink2xpath);
		browser.click(buttonLink1xpath);
		browser.click(buttonLink2xpath);

		assertImageRendered(browser, "children");

		if (button) {
			SeleniumAssert.assertElementVisible(browser, generalButtonValueXpath);
			browser.click(generalButtonValueXpath);

			assertImageRendered(browser, "image");
		}
	}

	protected void runButtonLinkMenuTest(String componentName) throws Exception {

		Browser browser = Browser.getInstance();
		navigateToUseCase(browser, componentName, "immediate");

		// Test that the value submits successfully and the valueChangeListener
		// method is called during the
		// APPLY_REQUEST_VALUES phase.
		browser.clickAndWaitForAjaxRerender(
			"(//*[contains(@onclick,'mojarra.ab')][contains(text(),'Submit') or contains(@value,'Submit')])[1]");
		SeleniumAssert.assertElementVisible(browser, immediateMessage1Xpath);

		// Test that the value submits successfully and the valueChangeListener
		// method is called during the
		// PROCESS_VALIDATIONS phase.
		browser.clickAndWaitForAjaxRerender(
			"(//*[contains(@onclick,'mojarra.ab')][contains(text(),'Submit') or contains(@value,'Submit')])[2]");
		SeleniumAssert.assertElementVisible(browser, "//li[contains(text(),'INVOKE_APPLICATION ')]");
	}
}
