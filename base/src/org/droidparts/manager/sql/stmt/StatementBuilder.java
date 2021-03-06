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
package org.droidparts.manager.sql.stmt;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public abstract class StatementBuilder {

	protected final SQLiteDatabase db;
	protected final String tableName;

	public StatementBuilder(SQLiteDatabase db, String tableName) {
		this.db = db;
		this.tableName = tableName;
	}

	public static final String[] toArgs(Object... args) {
		String[] arr = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			if (arg == null) {
				arg = "NULL";
			} else if (arg instanceof Boolean) {
				arg = ((Boolean) arg) ? 1 : 0;
			}
			arr[i] = String.valueOf(arg);
		}
		return arr;
	}

	public static String sqlEscapeString(String val) {
		val = DatabaseUtils.sqlEscapeString(val);
		val = val.substring(1, val.length() - 1);
		return val;
	}

	// TODO

}
