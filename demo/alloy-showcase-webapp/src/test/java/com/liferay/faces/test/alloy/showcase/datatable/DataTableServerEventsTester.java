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


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class DataTableServerEventsTester extends DataTableTester {

	@Test
	public void runDataTableServerEventsTest() throws Exception {

		Browser browser = Browser.getInstance();
		String componentUseCase = "server-events";
		navigateToUseCase(browser, "dataTable", componentUseCase);

		testPagesPaginationDefault(browser);

		testDataTableSelection(browser, componentUseCase);

		String[] johnAdams = { DEFAULT_CUSTOMERS[0] };
		testDataTableServerEvents(browser, "0", johnAdams);

		// Test RowSelectEvent for two 'checkbox'
		String serverEventInfoText = serverEventInfoText("RowSelectEvent", "0", "John Adams");
		testClickWaitAssertText(browser, getTableRowCustomersXpath("John", "Adams"),
			alloyMessageXpath(serverEventInfoText), assertServerEventInfoTextXpath, serverEventInfoText);

		serverEventInfoText = serverEventInfoText("RowSelectEvent", "2", "Josiah Bartlett");
		testClickWaitAssertText(browser, getTableRowCustomersXpath("Josiah", "Bartlett"),
			alloyMessageXpath(serverEventInfoText), assertServerEventInfoTextXpath, serverEventInfoText);

		// Test select all 'checkbox' and test that RowSelectEvent and RowDeselectEvent fire.
		serverEventInfoText = "Received RowSelectRangeEvent for rowIndexes=[0, 1, 2, 3, 4, 5, 6, 7, 8, 9] Customers=[" +
			arrayToString(DEFAULT_CUSTOMERS) + "] in the APPLY_REQUEST_VALUES 2 phase.";
		testClickWaitAssertText(browser, "//input[contains(@id,'selectAll')]", alloyMessageXpath(serverEventInfoText),
			assertServerEventInfoTextXpath, serverEventInfoText);

		serverEventInfoText =
			"Received RowDeselectRangeEvent for rowIndexes=[0, 1, 2, 3, 4, 5, 6, 7, 8, 9] Customers=[" +
			arrayToString(DEFAULT_CUSTOMERS) + "] in the APPLY_REQUEST_VALUES 2 phase.";
		testClickWaitAssertText(browser, "//input[contains(@id,'selectAll')]", alloyMessageXpath(serverEventInfoText),
			assertServerEventInfoTextXpath, serverEventInfoText);

		// Navigate back to use case
		navigateToUseCase(browser, "dataTable", componentUseCase);

		// Select 'radio' Selection Mode.
		String selectRadioXpath = labelSelectOptionValueXpath("Selection Mode", "radio");
		browser.click(selectRadioXpath);
		browser.waitForElementVisible("//input[contains(@type,'radio')]");

		testDataTableServerEvents(browser, "0", johnAdams);

		// Select 'radio' Selection Mode.
		browser.click(selectRadioXpath);
		browser.waitForElementVisible("//input[contains(@type,'radio')]");

		String[] francisLewis = { DEFAULT_CUSTOMERS[9] };
		testDataTableServerEvents(browser, "9", francisLewis);
	}
}
