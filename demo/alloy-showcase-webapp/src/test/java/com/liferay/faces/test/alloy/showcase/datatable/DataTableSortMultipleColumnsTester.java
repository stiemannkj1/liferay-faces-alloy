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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class DataTableSortMultipleColumnsTester extends DataTableTester {

	@Test
	public void runDataTableSortMultipleColumnsTest() throws Exception {

		Browser browser = Browser.getInstance();
		navigateToUseCase(browser, "dataTable", "sort-multiple-columns");

		testPagesPaginationDefault(browser);

		// Assert default UI state
		testDataTableSort(browser, DEFAULT_CUSTOMERS);

		// Test sort by first name column header.
		browser.centerElementInView(firstNameHeaderXpath);
		browser.clickAndWaitForAjaxRerender(firstNameHeaderXpath);

		// Test that DataTable values are properly sorted.
		testDataTableSort(browser, FIRST_NAME_SORT_CUSTOMERS);

		// Test sort by multiple column headers using meta key.
		WebElement rerenderElement = browser.findElementByXpath(dateOfBirthHeaderXpath);
		browser.centerElementInView(dateOfBirthHeaderXpath);
		metaOrCommandClick(browser, dateOfBirthHeaderXpath);
		browser.waitUntil(ExpectedConditions.stalenessOf(rerenderElement));
		browser.waitForElementVisible(dateOfBirthHeaderXpath);

		// Test that DataTable values are properly sorted
		String[] firstNameToDateOfBirthMetaOrCommandClickSortCustomers = {
				"Adoniram Taverner", "Andrew Peale", "Andrew Colman", "Andrew Kingston", "Anthony Langley",
				"Anthony Gilby", "Arthur Roborough", "Arthur Middleton", "Barnaby Benison", "Benjamin de la Place"
			};

		testDataTableSort(browser, firstNameToDateOfBirthMetaOrCommandClickSortCustomers);

		// Test that first and last date of row index values are visible.
		SeleniumAssert.assertElementVisible(browser,
			(getTableRowXpath("Adoniram", "Taverner") + "[contains(.,'May 05, 1665')]"));
		SeleniumAssert.assertElementVisible(browser,
			(getTableRowXpath("Benjamin", "de la Place") + "[contains(.,'Nov 24, 1620')]"));

		// Test sort by multiple column headers using meta key.
		rerenderElement = browser.findElementByXpath(dateOfBirthHeaderXpath);
		browser.centerElementInView(dateOfBirthHeaderXpath);
		metaOrCommandClick(browser, dateOfBirthHeaderXpath);
		browser.waitUntil(ExpectedConditions.stalenessOf(rerenderElement));
		browser.waitForElementVisible(dateOfBirthHeaderXpath);

		// Test that DataTable values properly sorted
		String[] dateOfBirthMetaOrCommandClickSortCustomers = {
				"Adoniram Taverner", "Andrew Kingston", "Andrew Colman", "Andrew Peale", "Anthony Gilby",
				"Anthony Langley", "Arthur Middleton", "Arthur Roborough", "Barnaby Benison", "Benjamin Franklin"
			};

		testDataTableSort(browser, dateOfBirthMetaOrCommandClickSortCustomers);

		// Test that first and last date of row index values are visible.
		SeleniumAssert.assertElementVisible(browser,
			(getTableRowXpath("Adoniram", "Taverner") + "[contains(.,'May 05, 1665')]"));
		SeleniumAssert.assertElementVisible(browser,
			(getTableRowXpath("Benjamin", "Franklin") + "[contains(.,'Feb 17, 1706')]"));
	}
}
