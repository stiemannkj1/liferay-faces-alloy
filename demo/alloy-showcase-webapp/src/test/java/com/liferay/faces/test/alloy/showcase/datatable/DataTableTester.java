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

import java.util.Arrays;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.liferay.faces.test.alloy.showcase.data.DataTester;
import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class DataTableTester extends DataTester {

	// Protected Constants
	protected static final String[] DEFAULT_CUSTOMERS = {
			"John Adams", "Samuel Adams", "Josiah Bartlett", "Carter Braxton", "Charles Carroll", "Benjamin Franklin",
			"Elbridge Gerry", "John Hancock", "Thomas Jefferson", "Francis Lewis"
		};
	protected static final String[] FIRST_NAME_SORT_CUSTOMERS = {
			"Adoniram Taverner", "Andrew Colman", "Andrew Kingston", "Andrew Peale", "Anthony Gilby", "Anthony Langley",
			"Arthur Middleton", "Arthur Roborough", "Barnaby Benison", "Benjamin Franklin"
		};

	// Common Xpath
	protected static final String alloyOutputTextModalXpath = "//*[@class='alloy-output-text']";
	protected static final String assertServerEventInfoTextXpath =
		"//table[contains(@class,'alloy-messages')]/tbody/tr/td";
	protected static final String dateOfBirthHeaderXpath =
		"//table/thead/tr/th[@scope='col'][contains(.,'Date of Birth')]/div";
	protected static final String firstNameHeaderXpath = "//table/thead/tr/th[@scope='col'][contains(.,'First Name')]";
	protected static final String lastNameHeaderXpath = "//table/thead/tr/th[@scope='col'][contains(.,'Last Name')]";
	protected static final String notInfoTextXpath =
		"(//table[contains(@class,'alloy-messages')]/tbody/tr/td[not(contains(.,'in the APPLY_REQUEST_VALUES 2 phase.'))])";

	protected String alloyMessageXpath(String alloyMessage) {
		return "(//table[contains(@class,'alloy-messages')]/tbody/tr/td[contains(.,'" + alloyMessage + "')])";
	}

	/**
	 * Returns a string representation of the contents of the specified array. it also replaces any front and back
	 * square brackets with a value of null.
	 */
	protected String arrayToString(String[] stringArray) {
		return Arrays.toString(stringArray).replaceAll("^\\[", "").replaceAll("\\]$", "");
	}

	protected String dataTableContentXpath(String dataContent) {
		return "(//table[contains(@class,'alloy-data-table')]/tbody/tr/td[contains(.,'" + dataContent + "')])";
	}

	protected String dataTablePageAndResultsNumberXpath(int pageNumber, String resultsNumber) {
		return "//div[@class='showcase-example']//*[@class='pagination']//li/*[contains(.,'Results " + resultsNumber +
			" of 162 (Page " + pageNumber + " of 17)')]";
	}

	protected String dataTablePaginationLinkXpath(String whichPaginationActionLink) {
		return listItemXpath("*[@class='pagination']", "li") + "/*[contains(@onclick,\"'" + whichPaginationActionLink +
			"'\")]";
	}

	protected String getTableRowCustomersXpath(String firstName, String lastName) {
		return "//tbody/tr[contains(.,'" + firstName + "')][contains(.,'" + lastName +
			"')]//input[contains(@id,'customers:')]";
	}

	protected String getTableRowXpath(String firstName, String lastName) {
		return "//tbody/tr[contains(.,'" + firstName + "')][contains(.,'" + lastName + "')]";
	}

	protected String labelSelectOptionValueXpath(String labelText, String optionValue) {
		return "//label[contains(.,'" + labelText + "')]/following-sibling::select/option[@value='" + optionValue +
			"']";
	}

	/**
	 * Must build this action using keyDown(key) and keyUp(key) since Action sendKeys behaves differently than
	 * WebElement sendKeys (unless the browser is phantomjs, then it would need the following script on lines 111-112).
	 * https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/interactions/Actions.html#sendKeys-java.lang.CharSequence...-
	 */
	protected void metaOrCommandClick(Browser browser, String xpath) {
		Keys metaOrCommandKey = Keys.META;

		if (System.getProperty("os.name").toLowerCase(Locale.ENGLISH).contains("mac")) {
			metaOrCommandKey = Keys.COMMAND;
		}

		if ("phantomjs".equals(browser.getName())) {
			browser.executeScript(
				"var element = arguments[0]; YUI().use('node-event-simulate', function(Y) { Y.one(element).simulate('click', { metaKey: true }); });",
				browser.findElementByXpath(xpath));
		}
		else {
			Actions metaOrCommandClickBuilder = browser.createActions();
			metaOrCommandClickBuilder.keyDown(metaOrCommandKey).click(browser.findElementByXpath(xpath)).keyUp(
				metaOrCommandKey);

			Action metaOrCommandClick = metaOrCommandClickBuilder.build();
			metaOrCommandClick.perform();

			return;
		}
	}

	/**
	 * Text information for fired server events at the moment of selection.
	 *
	 * @param  rowIndex
	 * @param  customers
	 */
	protected String serverEventInfoText(String rowSelectEvent, String rowIndex, String customer) {
		return "Received " + rowSelectEvent + " for rowIndex=[" + rowIndex + "] customer=[" + customer +
			"] in the APPLY_REQUEST_VALUES 2 phase.";
	}

	/**
	 * Tests clicking on an element followed by waiting for another element to be visible and finally asserting an
	 * element contains some text
	 *
	 * @param  clickXpath
	 * @param  waitForXpath
	 * @param  assertElementXpath
	 * @param  containsText
	 */
	protected void testClickWaitAssertText(Browser browser, String clickXpath, String waitForXpath,
		String assertElementXpath, String containsText) {
		browser.centerElementInView(clickXpath);
		browser.click(clickXpath);
		browser.waitForElementVisible(waitForXpath);
		SeleniumAssert.assertElementTextVisible(browser, assertElementXpath, containsText);
	}

	/**
	 * Tests the customers selected for the dataTable Selection and Server Events testers.
	 *
	 * @param  customers
	 * @param  componentUseCase
	 */
	protected void testCustomersSelected(Browser browser, String[] customers, String componentUseCase) {

		// Select the customers
		for (String customer : customers) {
			String[] tokens = customer.split(" ");
			browser.centerElementInView(getTableRowCustomersXpath(tokens[0], tokens[1]));
			testRowSelection(browser, tokens[0], tokens[1]);
		}

		browser.centerElementInView("//button[contains(.,'Feedback')]");

		browser.click("//button[contains(.,'Feedback')]");
		browser.waitForElementVisible(notInfoTextXpath);
		browser.waitForElementVisible(alloyMessageXpath(arrayToString(customers)));

		// Assert proper feedback
		browser.centerElementInView(alloyMessageXpath(arrayToString(customers)));

		for (int i = 0; i < customers.length; i++) {
			SeleniumAssert.assertElementTextVisible(browser, alloyMessageXpath(arrayToString(customers)), customers[i]);
		}

		browser.centerElementInView("//button[contains(.,'Modal Dialog')]");

		browser.click("//button[contains(.,'Modal Dialog')]");
		browser.waitForElementTextVisible(modalDialogXpath, customers[0]);

		// Assert proper feedback
		browser.centerElementInView(modalDialogXpath + "//button[contains(.,'Cancel')]");

		for (int i = 0; i < customers.length; i++) {
			SeleniumAssert.assertElementTextVisible(browser,
				"(" + modalDialogXpath + alloyOutputTextModalXpath + ")[" + (i + 1) + "]", customers[i]);
		}

		browser.click(modalDialogXpath + "//button[contains(.,'Cancel')]");
		browser.waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.ByXPath.xpath(modalDialogXpath)));

		// Deselect the customers
		for (String customer : customers) {
			String[] tokens = customer.split(" ");
			browser.centerElementInView(getTableRowCustomersXpath(tokens[0], tokens[1]));
			testRowDeselection(browser, tokens[0], tokens[1]);
		}

		browser.centerElementInView("//button[contains(.,'Feedback')]");
		browser.click("//button[contains(.,'Feedback')]");
		browser.waitForElementVisible(alloyMessageXpath("No Customers Selected"));

		// Assert proper feedback
		SeleniumAssert.assertElementVisible(browser, alloyMessageXpath("No Customers Selected"));

		browser.centerElementInView("//button[contains(.,'Modal Dialog')]");
		browser.click("//button[contains(.,'Modal Dialog')]");
		browser.waitForElementTextVisible(modalDialogXpath + alloyOutputTextModalXpath, "No Customers Selected");

		// Assert proper feedback
		SeleniumAssert.assertElementTextVisible(browser, modalDialogXpath + alloyOutputTextModalXpath,
			"No Customers Selected");

		// Reset UI state
		navigateToUseCase(browser, "dataTable", componentUseCase);
	}

	/**
	 * Tests the default page's values on dataTable
	 *
	 * @param  firstName
	 * @param  lastName
	 * @param  dateOfBirth
	 */
	protected void testDataTableDefaultPage(Browser browser, String firstName, String lastName, String dateOfBirth) {
		testDataTableHeader(browser);

		// Test that for a certain quantity of rows
		int rows = browser.findElements(By.ByXPath.xpath(listItemXpath("tbody", "tr"))).size();

		if (rows == 10) {
			assertListChildCount(browser, "tbody", "tr", 10);
		}
		else {
			assertListChildCount(browser, "tbody", "tr", 2);
		}

		// Test for one value of each category
		SeleniumAssert.assertElementVisible(browser, dataTableContentXpath(firstName));
		SeleniumAssert.assertElementVisible(browser, dataTableContentXpath(lastName));
		SeleniumAssert.assertElementVisible(browser, dataTableContentXpath(dateOfBirth));
		SeleniumAssert.assertElementVisible(browser,
			dataTableContentXpath("United States') or contains(.,'United Kingdom") + "[2]");
	}

	/**
	 * Tests the column headers' values of dataTable
	 */
	protected void testDataTableHeader(Browser browser) {
		SeleniumAssert.assertElementVisible(browser,
			"(//div[contains(@class,'showcase-example-usage')])/table/thead/tr/th[@scope='colgroup'][contains(.,'Customers')]");
		SeleniumAssert.assertElementVisible(browser, firstNameHeaderXpath);
		SeleniumAssert.assertElementVisible(browser, lastNameHeaderXpath);
		SeleniumAssert.assertElementVisible(browser,
			"(//div[contains(@class,'showcase-example-usage')])/table/thead/tr/th[@scope='col'][contains(.,'Date of Birth')]");
		SeleniumAssert.assertElementVisible(browser,
			"(//div[contains(@class,'showcase-example-usage')])/table/thead/tr/th[@scope='col'][contains(.,'Country')]");
	}

	/**
	 * Tests selection for the dataTable Selection and Server Events testers
	 *
	 * @param  componentUseCase
	 */
	protected void testDataTableSelection(Browser browser, String componentUseCase) {

		// Test that the Selection mode defaults to 'checkbox'.
		SeleniumAssert.assertElementVisible(browser, labelSelectOptionValueXpath("Selection Mode", "checkbox"));

		// 'checkbox'
		// Test the first row customer.
		String[] oneCustomers = { DEFAULT_CUSTOMERS[0] };
		testCustomersSelected(browser, oneCustomers, componentUseCase);

		// Test the selected customers.
		String[] twoCustomers = { DEFAULT_CUSTOMERS[0], DEFAULT_CUSTOMERS[2] };
		testCustomersSelected(browser, twoCustomers, componentUseCase);

		// Test all customers on current page selected.
		testCustomersSelected(browser, DEFAULT_CUSTOMERS, componentUseCase);

		// 'radio'
		// Select 'radio' Selection Mode.
		browser.click(labelSelectOptionValueXpath("Selection Mode", "radio"));
		browser.waitForElementVisible("//input[contains(@type,'radio')]");
		SeleniumAssert.assertElementVisible(browser,
			getTableRowCustomersXpath("John", "Adams") + "/..//input[contains(@type,'radio')]");

		// Test the first row customer.
		testRowSelection(browser, "John", "Adams");
		testRowDeselection(browser, "John", "Adams");
		testRowSelection(browser, "John", "Adams");

		// Test selecting another option deselects previous selection
		testRowSelection(browser, "Josiah", "Bartlett");
		SeleniumAssert.assertElementVisible(browser, getRowXpath("John", "Adams", false));
		testRowSelection(browser, "John", "Adams");

		// Assert UI state
		SeleniumAssert.assertElementValue(browser, "(//table/input)", "0", false);

		// Test that selected customers appear in the feedback.
		testClickWaitAssertText(browser, "//button[contains(.,'Feedback')]", notInfoTextXpath,
			assertServerEventInfoTextXpath, "John Adams");

		// Test that selected customers appear in the modal.
		browser.centerElementInView("//button[contains(.,'Modal Dialog')]");
		testClickWaitAssertText(browser, "//button[contains(.,'Modal Dialog')]",
			modalDialogXpath + alloyOutputTextModalXpath, modalDialogXpath + alloyOutputTextModalXpath, "John Adams");

		browser.centerElementInView(modalDialogXpath + "//button[contains(.,'Cancel')]");
		browser.click(modalDialogXpath + "//button[contains(.,'Cancel')]");
		browser.waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.ByXPath.xpath(modalDialogXpath)));

		// Reset UI state
		navigateToUseCase(browser, "dataTable", componentUseCase);
	}

	/**
	 * Tests that the Server Events fire at the moment of selection. Can be used for 'checkbox' or 'radio'.
	 *
	 * @param  rowIndex
	 * @param  customers
	 */
	protected void testDataTableServerEvents(Browser browser, String rowIndex, String[] customers) {

		for (String customer : customers) {
			String[] tokens = customer.split(" ");

			// Select the customer and test that RowSelectEvent and RowDeselectEvent fire.
			String tableRowCustomersXpath = getTableRowCustomersXpath(tokens[0], tokens[1]);
			String serverEventInfoText = serverEventInfoText("RowSelectEvent", rowIndex, arrayToString(customers));
			testClickWaitAssertText(browser, tableRowCustomersXpath, alloyMessageXpath(serverEventInfoText),
				assertServerEventInfoTextXpath, serverEventInfoText);

			if (browser.findElements(By.ByXPath.xpath("//input[contains(@type,'checkbox')]")).isEmpty()) {

				metaOrCommandClick(browser, tableRowCustomersXpath);
			}
			else {
				browser.click(tableRowCustomersXpath);
			}

			browser.waitForElementVisible(alloyMessageXpath(
					serverEventInfoText("RowDeselectEvent", rowIndex, arrayToString(customers))));
			testClickWaitAssertText(browser, tableRowCustomersXpath, alloyMessageXpath(serverEventInfoText),
				assertServerEventInfoTextXpath, serverEventInfoText);
		}

		// Reset UI state
		navigateToUseCase(browser, "dataTable", "server-events");
	}

	/**
	 * Tests the sort column ordering feature of dataTable.
	 *
	 * @param  customers
	 */
	protected void testDataTableSort(Browser browser, String[] customers) {

		for (String customer : customers) {
			String[] tokens = customer.split(" ");
			String tableRowXpath = getTableRowXpath(tokens[0], tokens[1]);
			browser.waitForElementVisible(tableRowXpath);
			browser.centerElementInView(tableRowXpath);
			SeleniumAssert.assertElementTextVisible(browser, tableRowXpath, customer);
		}
	}

	/**
	 * Tests the Pagination feature of dataTable.
	 */
	protected void testPagesPaginationDefault(Browser browser) {

		// test first page
		SeleniumAssert.assertElementVisible(browser, dataTablePageAndResultsNumberXpath(1, "1-10"));
		testDataTableDefaultPage(browser, "Elbridge", "Braxton", "Apr 21, 1713");

		// test next (second) page
		browser.performAndWaitForAjaxRerender(browser.createClickAction(dataTablePaginationLinkXpath("nextPage")),
			dataTablePaginationLinkXpath("10"));
		SeleniumAssert.assertElementVisible(browser, dataTablePageAndResultsNumberXpath(2, "11-20"));
		testDataTableDefaultPage(browser, "Philip", "Wolcott", "Jul 26, 1742");

		// test fifth page
		browser.performAndWaitForAjaxRerender(browser.createClickAction(dataTablePaginationLinkXpath("5")),
			dataTablePaginationLinkXpath("10"));
		SeleniumAssert.assertElementVisible(browser, dataTablePageAndResultsNumberXpath(5, "41-50"));
		testDataTableDefaultPage(browser, "Bernard", "Brayne", "Dec 06, 1596");

		// test previous (fourth) page
		browser.performAndWaitForAjaxRerender(browser.createClickAction(dataTablePaginationLinkXpath("previousPage")),
			dataTablePaginationLinkXpath("10"));
		SeleniumAssert.assertElementVisible(browser, dataTablePageAndResultsNumberXpath(4, "31-40"));
		testDataTableDefaultPage(browser, "Adoniram", "Lever", "Oct 09, 1577");

		// test last (seventeenth) page
		browser.performAndWaitForAjaxRerender(browser.createClickAction(dataTablePaginationLinkXpath("lastPage")),
			dataTablePaginationLinkXpath("10"));
		browser.waitForElementVisible("//li[contains(@class,'active')][contains(.,'17')]");
		SeleniumAssert.assertElementVisible(browser, dataTablePageAndResultsNumberXpath(17, "161-162"));
		testDataTableDefaultPage(browser, "Francis", "Woodcock", "Aug 07, 1614");

		// Return to to first page and test it
		browser.performAndWaitForAjaxRerender(browser.createClickAction(dataTablePaginationLinkXpath("firstPage")),
			dataTablePaginationLinkXpath("10"));
		browser.waitForElementVisible("//li[contains(@class,'active')][contains(.,'1')]");
		SeleniumAssert.assertElementVisible(browser, dataTablePageAndResultsNumberXpath(1, "1-10"));
		testDataTableDefaultPage(browser, "Elbridge", "Braxton", "Apr 21, 1713");
	}

	private String getRowXpath(String firstName, String lastName, boolean selected) {

		String tr = "//table[contains(@id,':customers')]//tr[not(contains(@class,'info'))]";

		if (selected) {
			tr = "//table[contains(@id,':customers')]//tr[contains(@class,'info')]";
		}

		return tr + "[contains(.,'" + firstName + "')][contains(.,'" + lastName + "')]";
	}

	/**
	 * Tests row deselection on dataTable
	 *
	 * @param  firstName
	 * @param  lastName
	 */
	private void testRowDeselection(Browser browser, String firstName, String lastName) {

		if (browser.findElements(By.ByXPath.xpath("//input[contains(@type,'checkbox')]")).isEmpty()) {
			metaOrCommandClick(browser, getTableRowCustomersXpath(firstName, lastName));
		}
		else {
			browser.click(getTableRowCustomersXpath(firstName, lastName));
		}

		browser.waitForElementVisible(getRowXpath(firstName, lastName, false));
		SeleniumAssert.assertElementVisible(browser, getRowXpath(firstName, lastName, false));
	}

	/**
	 * Tests row selection on dataTable
	 *
	 * @param  firstName
	 * @param  lastName
	 */
	private void testRowSelection(Browser browser, String firstName, String lastName) {
		browser.centerElementInView(getTableRowCustomersXpath(firstName, lastName));
		browser.click(getTableRowCustomersXpath(firstName, lastName));
		browser.waitForElementVisible(getRowXpath(firstName, lastName, true));
		SeleniumAssert.assertElementVisible(browser, getRowXpath(firstName, lastName, true));
	}

}
