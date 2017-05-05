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
package com.liferay.faces.test.alloy.showcase.data;

import java.util.List;

import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;
import com.liferay.faces.test.showcase.TesterBase;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class DataTester extends TesterBase {

	// Common Xpath
	protected static final String modalDialogXpath = "(//div[contains(@class,'yui3-widget-modal')])[1]";

	protected final void assertListChildCount(Browser browser, String listType, String listItemTag, int expecteds) {
		String listItemXpath = listItemXpath(listType, listItemTag);

		List<WebElement> elements = browser.findElements(By.xpath(listItemXpath));
		Assert.assertNotNull("Element " + listItemXpath + " is not present in the DOM.", elements);

		int elementsSize = elements.size();
		Assert.assertEquals("Element " + listItemXpath + " does not equal \"" + expecteds + "\". Instead it equals \"" +
			elementsSize + "\".", expecteds, elementsSize);
	}

	// Test that the list item text property is rendered and visible on the page.
	protected final void assertListItemText(Browser browser, String listType, String listItemTag, int itemNumber,
		String expectedContent) {

		String listItemXpath = listItemXpath(listType, listItemTag);
		String listItemContentXpath = listItemXpath + "[" + itemNumber + "]//*[text()]";
		SeleniumAssert.assertElementTextVisible(browser, listItemContentXpath, expectedContent);
	}

	protected final String listItemXpath(String listType, String listItemTag) {
		String listItemXpath = "(//div[@class='showcase-example']//" + listType + "//" + listItemTag + ")";

		return listItemXpath;
	}
}
