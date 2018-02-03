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
package com.liferay.faces.alloy.component.button.internal;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.UIViewParameter;
import javax.faces.context.FacesContext;

import com.liferay.faces.alloy.util.internal.JSFUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.DelegatingRendererBase;


/**
 * This class isolates differences between JSF 2.2 and JSF 2.1 in order to minimize diffs across branches.
 *
 * @author  Neil Griffin
 */
public abstract class ButtonRendererCompat extends DelegatingRendererBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ButtonRendererCompat.class);

	// Private Final Data Members
	private final Class<?> uiViewActionClass;

	public ButtonRendererCompat() {

		Class<?> clazz = null;

		try {
			clazz = Class.forName("javax.faces.component.UIViewAction");
		}
		catch (ClassNotFoundException e) {

			FacesContext facesContext = FacesContext.getCurrentInstance();

			if (JSFUtil.isFaces_2_2_OrNewer(facesContext)) {
				logger.error(e);
			}
		}

		uiViewActionClass = clazz;
	}

	protected boolean isVisualComponent(UIComponent uiComponent) {

		if (uiViewActionClass == null) {
			return (!(uiComponent instanceof UIParameter)) || (!(uiComponent instanceof UIViewParameter));
		}
		else {

			Class<? extends UIComponent> uiComponentClass = uiComponent.getClass();

			return (!(uiComponent instanceof UIParameter)) ||
				(!(uiComponentClass.isAssignableFrom(uiViewActionClass))) ||
				(!(uiComponent instanceof UIViewParameter));
		}
	}
}
