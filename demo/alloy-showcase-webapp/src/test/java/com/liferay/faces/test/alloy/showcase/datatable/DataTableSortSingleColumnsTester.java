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
public class DataTableSortSingleColumnsTester extends DataTableTester {

	@Test
	public void runDataTableSortSingleColumnsTest() throws Exception {

		Browser browser = Browser.getInstance();
		navigateToUseCase(browser, "dataTable", "sort-single-column");

		testPagesPaginationDefault(browser);

		// Assert default UI state
		testDataTableSort(browser, DEFAULT_CUSTOMERS);

		// Test sort by first name column header.
		browser.centerElementInView(firstNameHeaderXpath);
		browser.clickAndWaitForAjaxRerender(firstNameHeaderXpath);

		// Test that DataTable values properly sorted.
		testDataTableSort(browser, FIRST_NAME_SORT_CUSTOMERS);

		// Test sort by inverted first name column header.
		browser.centerElementInView(firstNameHeaderXpath);
		browser.clickAndWaitForAjaxRerender(firstNameHeaderXpath);

		// Test that DataTable values properly sorted
		String[] firstNameInvertedSortCustomers = {
				"William Becon", "William Bonham", "William Lever", "William Turner", "William Fulke",
				"William Proudlove", "William Maynard", "William Philip", "William Downing", "William Macklet"
			};

		testDataTableSort(browser, firstNameInvertedSortCustomers);

		// Click on last name column header
		browser.centerElementInView(lastNameHeaderXpath);
		browser.clickAndWaitForAjaxRerender(lastNameHeaderXpath);

		// Test that DataTable values properly sorted
		String[] lastNameSortCustomers = {
				"John Adams", "Samuel Adams", "Gilbert Alcock", "Thomas Aldrich", "Thomas Axton", "John Bale",
				"Thomas Barber", "Josiah Bartlett", "William Becon", "Barnaby Benison"
			};

		testDataTableSort(browser, lastNameSortCustomers);

		// Click on last name column header
		browser.centerElementInView(lastNameHeaderXpath);
		browser.clickAndWaitForAjaxRerender(lastNameHeaderXpath);

		// Test that DataTable values properly sorted
		String[] lastNameInvertedSortCustomers = {
				"Benjamin de la Place", "Obadiah de la Marche", "George Wythe", "Robert Wright", "Daniel Wright",
				"William Woodcock", "Lever Wood", "Oliver Wolcott", "John Witherspoon", "Francis Wincop"
			};

		testDataTableSort(browser, lastNameInvertedSortCustomers);

		// Click on date of birth name column header
		browser.centerElementInView(dateOfBirthHeaderXpath);
		browser.clickAndWaitForAjaxRerender(dateOfBirthHeaderXpath);

		// Test that DataTable values properly sorted
		String[] dateOfBirthSortCustomers = {
				"John Pullain", "Lever Wood", "Daniel Wright", "William Fulke", "John Hardyman", "John Field",
				"Samuel Price", "William Gibbon", "Robert Johnson", "John Garbrand"
			};

		testDataTableSort(browser, dateOfBirthSortCustomers);

		// Test that first and last date of birth fields are visible.
		SeleniumAssert.assertElementVisible(browser,
			(getTableRowXpath("John", "Pullain") + "[contains(.,'May 27, 1566')]"));
		SeleniumAssert.assertElementVisible(browser,
			(getTableRowXpath("John", "Garbrand") + "[contains(.,'Apr 05, 1580')]"));

		// Click on date of birth name column header
		browser.centerElementInView(dateOfBirthHeaderXpath);
		browser.clickAndWaitForAjaxRerender(dateOfBirthHeaderXpath);

		// Test that DataTable values properly sorted
		String[] dateOfBirthInvertedSortCustomers = {
				"Thomas Jefferson", "Thomas Stone", "Arthur Middleton", "John Penn", "John Rutledge", "Charles Carroll",
				"John Hancock", "Carter Braxton", "John Adams", "Thomas McKean"
			};

		testDataTableSort(browser, dateOfBirthInvertedSortCustomers);

		// Test that first and last date of birth fields are visible.
		SeleniumAssert.assertElementVisible(browser,
			(getTableRowXpath("Thomas", "Jefferson") + "[contains(.,'May 13, 1743')]"));
		SeleniumAssert.assertElementVisible(browser,
			(getTableRowXpath("Thomas", "McKean") + "[contains(.,'Apr 19, 1734')]"));
	}
}
