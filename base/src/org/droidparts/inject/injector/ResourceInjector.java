/**
 * Copyright 2012 Alex Yanchenko
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.droidparts.inject.injector;

import static org.droidparts.reflection.util.ReflectionUtils.setFieldVal;
import static org.droidparts.reflection.util.TypeHelper.isArray;
import static org.droidparts.reflection.util.TypeHelper.isDrawable;
import static org.droidparts.reflection.util.TypeHelper.isInteger;
import static org.droidparts.reflection.util.TypeHelper.isString;

import java.lang.reflect.Field;

import org.droidparts.annotation.inject.InjectResource;
import org.droidparts.util.L;

import android.content.Context;
import android.content.res.Resources;

public class ResourceInjector {

	static boolean inject(Context ctx, InjectResource ann, Object target,
			Field field) {
		int resId = ann.value();
		if (resId != 0) {
			Resources res = ctx.getResources();
			Class<?> cls = field.getType();
			Object val = null;
			if (isString(cls)) {
				val = res.getString(resId);
			} else if (isInteger(cls)) {
				val = res.getInteger(resId);
			} else if (isDrawable(cls)) {
				val = res.getDrawable(resId);
			} else if (isArray(cls)) {
				Class<?> type = cls.getComponentType();
				if (isInteger(type)) {
					val = res.getIntArray(resId);
				} else if (isString(type)) {
					val = res.getStringArray(resId);
				}
			} else {
				L.e("Resource not supported yet: " + field.getType().getName());
			}
			try {
				setFieldVal(field, target, val);
				return true;
			} catch (IllegalArgumentException e) {
				// swallow
			}
		}
		return false;
	}
}
