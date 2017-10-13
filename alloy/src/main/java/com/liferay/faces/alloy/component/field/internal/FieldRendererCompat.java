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

import java.util.List;

import javax.faces.component.UIComponent;

import com.liferay.faces.alloy.component.selectbooleancheckbox.SelectBooleanCheckbox;


/**
 * @author  Kyle Stiemann
 */
public abstract class FieldRendererCompat extends FieldRendererBase {

	protected abstract UIComponent getSelectBooleanCheckboxChild(List<UIComponent> children);

	private UIComponent getSelectBooleanCheckboxLabelFacetChild(UIComponent labelFacet) {

		UIComponent selectBooleanCheckboxChild = null;

		if (labelFacet instanceof SelectBooleanCheckbox) {
			selectBooleanCheckboxChild = labelFacet;
		}
		else if (labelFacet.getChildCount() > 0) {

			List<UIComponent> children = labelFacet.getChildren();
			selectBooleanCheckboxChild = getSelectBooleanCheckboxChild(children);
		}

		return selectBooleanCheckboxChild;
	}
}
