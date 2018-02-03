/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.alloy.component.commandbutton;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.liferay.faces.alloy.component.commandlink.CommandLinkBase;
import com.liferay.faces.alloy.util.internal.JSFUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class isolates differences between JSF 2.2 and JSF 2.1 in order to minimize diffs across branches.
 *
 * @author  Neil Griffin
 */
public abstract class SplitCommandButtonCompat extends CommandLinkBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SplitCommandButtonCompat.class);

	// Private Data Members
	private Map<String, Object> passThroughAttributes;
	private String role;

	// Protected Data Members
	protected CommandButton wrappedCommandButton;

	public Map<String, Object> getPassThroughAttributes(boolean create) {

		if (passThroughAttributes == null) {
			passThroughAttributes = new HashMap<String, Object>();
		}

		Method getPassThroughAttributesMethod = null;

		try {
			Class<? extends CommandButton> wrappedCommandButtonClass = wrappedCommandButton.getClass();
			getPassThroughAttributesMethod = wrappedCommandButtonClass.getMethod("getPassThroughAttributes",
					new Class[] { boolean.class });
		}
		catch (NoSuchMethodException e) {

			FacesContext facesContext = getFacesContext();

			if (JSFUtil.isFaces_2_2_OrNewer(facesContext)) {
				logger.error(e);
			}
		}

		if (getPassThroughAttributesMethod != null) {

			try {
				passThroughAttributes = (Map<String, Object>) getPassThroughAttributesMethod.invoke(
						wrappedCommandButton, new Object[] { create });
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return passThroughAttributes;
	}

	public String getRole() {

		Method getRoleMethod = null;

		Class<? extends CommandButton> wrappedCommandButtonClass = wrappedCommandButton.getClass();

		try {
			getRoleMethod = wrappedCommandButtonClass.getMethod("getRole", new Class[] {});
		}
		catch (NoSuchMethodException e) {

			FacesContext facesContext = getFacesContext();

			if (JSFUtil.isFaces_2_2_OrNewer(facesContext)) {
				logger.error(e);
			}
		}

		if (getRoleMethod != null) {

			try {
				role = (String) getRoleMethod.invoke(wrappedCommandButton, new Object[] {});
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return role;
	}

	public void setRole(String role) {

		Method setRoleMethod = null;

		Class<? extends CommandButton> wrappedCommandButtonClass = wrappedCommandButton.getClass();

		try {
			setRoleMethod = wrappedCommandButtonClass.getMethod("setRole", new Class[] { String.class });
		}
		catch (NoSuchMethodException e) {

			FacesContext facesContext = getFacesContext();

			if (JSFUtil.isFaces_2_2_OrNewer(facesContext)) {
				logger.error(e);
			}
		}

		if (setRoleMethod == null) {
			this.role = role;
		}
		else {

			try {
				setRoleMethod.invoke(wrappedCommandButton, new Object[] { role });
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
	}
}
