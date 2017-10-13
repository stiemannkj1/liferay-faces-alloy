/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.alloy.component.field.internal;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.field.Field;
import com.liferay.faces.alloy.component.selectbooleancheckbox.SelectBooleanCheckbox;


/**
 * @author  Kyle Stiemann
 */
@FacesRenderer(componentFamily = Field.COMPONENT_FAMILY, rendererType = Field.RENDERER_TYPE)
public class FieldRenderer extends FieldRendererCompat {

	@Override
	protected UIComponent getSelectBooleanCheckboxChild(List<UIComponent> children) {

		UIComponent selectBooleanCheckboxChild = null;

		for (UIComponent child : children) {

			if (child instanceof SelectBooleanCheckbox) {

				selectBooleanCheckboxChild = child;

				break;
			}
		}

		return selectBooleanCheckboxChild;
	}
}
