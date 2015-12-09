/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.test.showcase;

import org.junit.AfterClass;


/**
 * @author  Neil Griffin
 */
public class TesterBase {

	/**
	 * {@link AlloyShowcaseTestSuiteListener#testRunFinished()} is used to shut down the browser/webDriver when the
	 * tests are run with the maven-surefire-plugin. However, {@link AlloyShowcaseTestSuiteListener#testRunFinished()}
	 * is not called when the tests are not run with the maven-surefire-plugin (i.e. when the tests are run from an
	 * IDE). So when the tests are run from an IDE, it is necessary to shutdown the browser after each test class is
	 * run.
	 */
	@AfterClass
	public static void tearDown() {

		boolean runningWithMavenSurefirePlugin = Boolean.valueOf(System.getProperty("runningWithMavenSurefirePlugin"));

		if (!runningWithMavenSurefirePlugin) {

			// "quit" closes all instances of the browsers opened by a test, whereas "close" closes just one instance of
			// the browser
			// https://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/WebDriver.html#quit%28%29
			// https://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/WebDriver.html#close%28%29
			Browser.getInstance().quit();
		}
	}
}
