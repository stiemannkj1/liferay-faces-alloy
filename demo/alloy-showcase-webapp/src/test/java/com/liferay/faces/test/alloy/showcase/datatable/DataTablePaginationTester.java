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
package com.liferay.faces.test.alloy.showcase.datatable;

import org.junit.Test;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class DataTablePaginationTester extends DataTableTester {

	@Test
	public void runDataTablePaginationTest() throws Exception {

		Browser browser = Browser.getInstance();
		navigateToUseCase(browser, "dataTable", "pagination");

		testPagesPaginationDefault(browser);

		// Summary Position
		// Test default "bottom"
		SeleniumAssert.assertElementVisible(browser,
			labelSelectOptionValueXpath("Summary Position", "bottom") + "[@selected='selected']");

		String defaultPositionXpath = "(//*[@class='pagination'])[2]//span";
		String defaultResults = "Results 1-10 of 162 (Page 1 of 17)";
		SeleniumAssert.assertElementTextVisible(browser, defaultPositionXpath, defaultResults);

		// test select "top"
		clickOptionAndWaitForAjaxRerender(browser, labelSelectOptionValueXpath("Summary Position", "top"));
		browser.waitForElementPresent(labelSelectOptionValueXpath("Summary Position", "top") +
			"[@selected='selected']");
		SeleniumAssert.assertElementTextVisible(browser, "(//*[@class='pagination'])[1]//span", defaultResults);

		// Test select "left"
		clickOptionAndWaitForAjaxRerender(browser, labelSelectOptionValueXpath("Summary Position", "left"));
		browser.waitForElementPresent(labelSelectOptionValueXpath("Summary Position", "left") +
			"[@selected='selected']");
		SeleniumAssert.assertElementTextVisible(browser, "//*[@class='pagination']//li[1]/span", defaultResults);

		// Test select "right"
		clickOptionAndWaitForAjaxRerender(browser, labelSelectOptionValueXpath("Summary Position", "right"));
		browser.waitForElementPresent(labelSelectOptionValueXpath("Summary Position", "right") +
			"[@selected='selected']");
		SeleniumAssert.assertElementTextVisible(browser, "//*[@class='pagination']//li[last()]/span", defaultResults);

		// Test select "none" and select default "bottom"
		clickOptionAndWaitForAjaxRerender(browser, labelSelectOptionValueXpath("Summary Position", "none"));
		browser.waitForElementPresent(labelSelectOptionValueXpath("Summary Position", "none") +
			"[@selected='selected']");
		SeleniumAssert.assertElementNotPresent(browser,
			"//*[@class='pagination']//li//span[contains(.,'Results 1-10 of 162 (Page 1 of 17)')]");

		clickOptionAndWaitForAjaxRerender(browser, labelSelectOptionValueXpath("Summary Position", "bottom"));
		browser.waitForElementPresent(labelSelectOptionValueXpath("Summary Position", "bottom") +
			"[@selected='selected']");
		SeleniumAssert.assertElementTextVisible(browser, defaultPositionXpath, defaultResults);

		// Rows Per Page
		// Test default "10"
		SeleniumAssert.assertElementVisible(browser,
			labelSelectOptionValueXpath("Rows Per Page", "10") + "[@selected='selected']");
		assertListChildCount(browser, "tbody", "tr", 10);

		// Test select "5"
		clickOptionAndWaitForAjaxRerender(browser, labelSelectOptionValueXpath("Rows Per Page", "5"));
		browser.waitForElementPresent(labelSelectOptionValueXpath("Rows Per Page", "5") + "[@selected='selected']");
		assertListChildCount(browser, "tbody", "tr", 5);
		SeleniumAssert.assertElementTextVisible(browser, defaultPositionXpath, "Results 1-5 of 162 (Page 1 of 33)");

		// Test select "15"
		clickOptionAndWaitForAjaxRerender(browser, labelSelectOptionValueXpath("Rows Per Page", "15"));
		browser.waitForElementPresent(labelSelectOptionValueXpath("Rows Per Page", "15") + "[@selected='selected']");
		assertListChildCount(browser, "tbody", "tr", 15);
		SeleniumAssert.assertElementTextVisible(browser, defaultPositionXpath, "Results 1-15 of 162 (Page 1 of 11)");

		// Test select "20"
		clickOptionAndWaitForAjaxRerender(browser, labelSelectOptionValueXpath("Rows Per Page", "20"));
		browser.waitForElementPresent(labelSelectOptionValueXpath("Rows Per Page", "20") + "[@selected='selected']");
		assertListChildCount(browser, "tbody", "tr", 20);
		SeleniumAssert.assertElementTextVisible(browser, defaultPositionXpath, "Results 1-20 of 162 (Page 1 of 9)");

		// Test select "10"
		clickOptionAndWaitForAjaxRerender(browser, labelSelectOptionValueXpath("Rows Per Page", "10"));
		browser.waitForElementPresent(labelSelectOptionValueXpath("Rows Per Page", "10") + "[@selected='selected']");
		assertListChildCount(browser, "tbody", "tr", 10);
		SeleniumAssert.assertElementTextVisible(browser, defaultPositionXpath, "Results 1-10 of 162 (Page 1 of 17)");
	}
}
