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


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class DataListGeneralTester extends DataListTester {

	@Test
	public void runDataListGeneralTest() throws Exception {

		Browser browser = Browser.getInstance();
		navigateToUseCase(browser, "dataList", "general");

		// Test that five list items appear on each example of list types
		assertListChildCount(browser, "ul", "li", 5); // unordered type
		assertListChildCount(browser, "ol", "li", 5); // ordered type
		assertListChildCount(browser, "dl", "dt", 5); // description type

		// Test that list items' text of each list type appear
		assertListItemText(browser, "ul", "li", 2, ENTERPRISE_READY_DESCRIPTION_TEXT);
		assertListItemText(browser, "ol", "li", 3, POWERFUL_INTEGRATION_DESCRIPTION_TEXT);
		assertListItemText(browser, "dl", "dt", 4, LIGHTWEIGHT_DESCRIPTION_TEXT);
	}
}
