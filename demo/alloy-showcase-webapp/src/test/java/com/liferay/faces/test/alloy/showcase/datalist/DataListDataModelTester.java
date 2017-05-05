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

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class DataListDataModelTester extends DataListTester {

	@Test
	public void runDataListDataModelTest() throws Exception {

		Browser browser = Browser.getInstance();
		navigateToUseCase(browser, "dataList", "data-model");

		// Test that five list items appear on each example of list types
		assertListChildCount(browser, "dl", "dt", 5); // description type
		assertListChildCount(browser, "dl", "dd", 5); // description type tags

		// Test that list item icons appear
		SeleniumAssert.assertElementVisible(browser, "(//img[contains(@src,'icon-enterprise.png')])");
		SeleniumAssert.assertElementVisible(browser, "(//img[contains(@src,'icon-integration.png')])");
		SeleniumAssert.assertElementVisible(browser, "(//img[contains(@src,'icon-lightweight.png')])");

		// Test that list items' text of list type description appear
		assertListItemText(browser, "dl", "dt", 2, "Enterprise Ready");
		assertListItemText(browser, "dl", "dt", 3, "Powerful Integration");
		assertListItemText(browser, "dl", "dt", 4, "Lightweight");

		assertListItemText(browser, "dl", "dd", 2, ENTERPRISE_READY_DESCRIPTION_TEXT);
		assertListItemText(browser, "dl", "dd", 3, POWERFUL_INTEGRATION_DESCRIPTION_TEXT);
		assertListItemText(browser, "dl", "dd", 4, LIGHTWEIGHT_DESCRIPTION_TEXT);
	}
}
