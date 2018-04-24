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
import com.liferay.faces.util.lang.OnDemand;
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
	private final OnDemandFaces_2_2_OrNewer onDemandFaces_2_2_OrNewer = new OnDemandFaces_2_2_OrNewer();

	private static boolean isInstanceOfUIViewAction(OnDemandFaces_2_2_OrNewer faces_2_2_OrNewerInitiailzer,
		UIComponent uiComponent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		return faces_2_2_OrNewerInitiailzer.get(facesContext) &&
			uiComponent.getClass().isAssignableFrom(OnDemandUIViewActionClass.INSTANCE);
	}

	protected boolean isVisualComponent(UIComponent uiComponent) {
		return !(uiComponent instanceof UIParameter) && !(uiComponent instanceof UIViewParameter) &&
			!isInstanceOfUIViewAction(onDemandFaces_2_2_OrNewer, uiComponent);
	}

	private static final class OnDemandFaces_2_2_OrNewer extends OnDemand<Boolean, FacesContext> {

		@Override
		protected Boolean computeInitialValue(FacesContext facesContext) {
			return JSFUtil.isFaces_2_2_OrNewer(facesContext);
		}
	}

	private static final class OnDemandUIViewActionClass {

		// Since this class is not referenced until isVisualComponent() is called and the JSF version is 2.2+, the
		// UIViewActionClass instance will be lazily initialized when those two things are true. Class initialization is
		// thread-safe. For more details on this pattern, see
		// http://stackoverflow.com/questions/7420504/threading-lazy-initialization-vs-static-lazy-initialization and
		// http://docs.oracle.com/javase/specs/jls/se7/html/jls-12.html#jls-12.4.2
		private static final Class<?> INSTANCE;

		static {

			Class<?> uiViewActionClass = null;

			try {
				uiViewActionClass = Class.forName("javax.faces.component.UIViewAction");
			}
			catch (ClassNotFoundException e) {
				logger.error(e);
			}

			INSTANCE = uiViewActionClass;
		}
	}
}
