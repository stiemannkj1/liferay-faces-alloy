/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.test.alloy.showcase.datalist;

import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class DataListSelectionTester extends DataListTester {

	@Test
	public void runDataListSelectionTest() throws Exception {

		Browser browser = Browser.getInstance();
		navigateToUseCase(browser, "dataList", "selection");

		// Test that five list items appear
		assertListChildCount(browser, "ul", "li", 5); // unordered type

		// Test that first and last search icons appear
		SeleniumAssert.assertElementVisible(browser, "(//*[contains(@class,'icon-search')])[1]");
		SeleniumAssert.assertElementVisible(browser, "(//*[contains(@class,'icon-search')])[5]");

		// Test that list items' text of list type description appear
		assertListItemText(browser, "ul", "li", 2, "Enterprise Ready");
		assertListItemText(browser, "ul", "li", 3, "Powerful Integration");
		assertListItemText(browser, "ul", "li", 4, "Lightweight");

		browser.centerElementInView("(//*[contains(@class,'icon-search')])[5]");
		testShowModal(browser, "Enterprise Ready", ENTERPRISE_READY_DESCRIPTION_TEXT);
		testShowModal(browser, "Lightweight", LIGHTWEIGHT_DESCRIPTION_TEXT);
	}

	protected void testShowModal(Browser browser, String text, String descriptionText) {

		// Test a "Show Modal" button value.
		browser.click("(//*[contains(text(),'" + text + "')])[1]");
		browser.waitForElementTextVisible(modalDialogXpath, text);
		SeleniumAssert.assertElementVisible(browser, modalDialogXpath);

		SeleniumAssert.assertElementTextVisible(browser, modalDialogXpath, descriptionText);

		browser.click("(//button[contains(@class,'close')])");
		browser.waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.ByXPath.xpath(modalDialogXpath)));
	}
}
