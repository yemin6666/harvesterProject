package util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import harvester.service.SqlUtil;

public class DbMapping {
	public static void main(String[] args) throws Exception {

		String tableName = "user";
		SqlUtil.query(tableName);

	}
}
