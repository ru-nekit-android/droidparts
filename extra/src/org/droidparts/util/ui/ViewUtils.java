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
package org.droidparts.util.ui;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import java.lang.reflect.Method;

import org.droidparts.adapter.ui.AnimationListenerAdapter;
import org.droidparts.util.L;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ViewUtils {

	public static void setInvisible(View view, boolean invisible) {
		view.setVisibility(invisible ? INVISIBLE : VISIBLE);
	}

	public static void setGone(View view, boolean gone) {
		view.setVisibility(gone ? GONE : VISIBLE);
	}

	public static void crossFade(final View lowerViewFrom,
			final View upperViewTo, int durationMillis) {
		Animation animFrom = new AlphaAnimation(1, 0);
		Animation animTo = new AlphaAnimation(0, 1);
		animFrom.setDuration(durationMillis);
		animTo.setDuration(durationMillis);
		animFrom.setAnimationListener(new AnimationListenerAdapter() {
			@Override
			public void onAnimationEnd(Animation animation) {
				lowerViewFrom.setVisibility(INVISIBLE);
			}
		});
		animTo.setAnimationListener(new AnimationListenerAdapter() {
			@Override
			public void onAnimationEnd(Animation animation) {
				upperViewTo.setVisibility(VISIBLE);
			}
		});
		lowerViewFrom.startAnimation(animFrom);
		upperViewTo.startAnimation(animTo);
	}

	public static void setKeyboardVisible(View view, boolean visible) {
		InputMethodManager imm = (InputMethodManager) view.getContext()
				.getSystemService(INPUT_METHOD_SERVICE);
		if (visible) {
			imm.showSoftInput(view, 0);
		} else {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public static void putCursorAfterLastSymbol(EditText editText) {
		editText.setSelection(editText.getText().length());
	}

	public static void disableOverscroll(View view) {
		Class<?> viewCls = view.getClass();
		try {
			Method m = viewCls.getMethod("setOverScrollMode",
					new Class[] { int.class });
			int OVER_SCROLL_NEVER = (Integer) viewCls.getField(
					"OVER_SCROLL_NEVER").get(view);
			m.invoke(view, OVER_SCROLL_NEVER);
		} catch (Exception e) {
			L.e(e);
		}
	}

	//

	@Deprecated
	public static void setVisible(View view, boolean visible) {
		setInvisible(view, !visible);
	}
}
