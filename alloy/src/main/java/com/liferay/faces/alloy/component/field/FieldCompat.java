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
package com.liferay.faces.alloy.component.field;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;

import com.liferay.faces.alloy.component.selectbooleancheckbox.SelectBooleanCheckbox;


/**
 * @author  Kyle Stiemann
 */
public abstract class FieldCompat extends FieldBase {

	// Protected Constants
	protected static final String GROUP = "form-group";
	protected static final String ERROR = "has-error";

	// Bootstrap 3 does not contain an info CSS class.
	protected static final String INFO = null;
	protected static final String WARNING = "has-warning";
	protected static final String SUCCESS = "has-success";

	protected boolean hasSelectBooleanCheckboxChild(UIComponent uiComponent) {

		boolean hasSelectBooleanCheckboxChild = false;
		List<UIComponent> children = uiComponent.getChildren();

		for (UIComponent child : children) {

			if (child instanceof SelectBooleanCheckbox) {

				hasSelectBooleanCheckboxChild = true;

				break;
			}
		}

		return hasSelectBooleanCheckboxChild;
	}

	protected boolean isAppendCheckboxCSSClassName() {

		boolean hasSelectBooleanCheckboxChild = hasSelectBooleanCheckboxChild(this);

		if (!hasSelectBooleanCheckboxChild) {

			Map<String, UIComponent> facets = getFacets();
			Set<Map.Entry<String, UIComponent>> entrySet = facets.entrySet();

			for (Map.Entry<String, UIComponent> facetEntry : entrySet) {

				UIComponent uiComponent = facetEntry.getValue();

				if (uiComponent instanceof SelectBooleanCheckbox) {

					hasSelectBooleanCheckboxChild = true;

					break;
				}
				else if (uiComponent.getChildCount() > 0) {

					hasSelectBooleanCheckboxChild = hasSelectBooleanCheckboxChild(uiComponent);

					break;
				}
			}
		}

		return hasSelectBooleanCheckboxChild;
	}
}
