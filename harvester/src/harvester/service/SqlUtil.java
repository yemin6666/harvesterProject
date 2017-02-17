package harvester.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DBPool;

public class SqlUtil {
	public static void query(String tableName) throws SQLException {
		StringBuffer s = new StringBuffer();
		String sql;//
		sql = "select * from " + tableName;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBPool.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				String name = rsmd.getColumnName(i);//
				String type = rsmd.getColumnTypeName(i);//
				if (name != null && name != "") {
					name = name.substring(0, 1).toLowerCase()
							+ name.substring(1);
				}
				String s1 = "private " + getType(type) + " " + name + "\n";
				s.append(s1);
			}
			System.out.println(s);
		} finally {
			close(rs, ps, conn);
		}
	}

	public static List<Map<String, String>> queryList(String sql,
			Object... parameters) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBPool.getConnection();
			ps = conn.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					setPreparedStatement(ps, i + 1, parameters[i]);
				}
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int colSize = rsmd.getColumnCount() + 1;
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 1; i < colSize; i++) {
					map.put(rsmd.getColumnLabel(i), rs.getString(i));
				}
				list.add(map);
			}
			return list;
		} finally {
			close(rs, ps, conn);
		}
	}

	public static Map<String, String> queryMap(String sql, Object... parameters)
			throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBPool.getConnection();
			ps = conn.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					setPreparedStatement(ps, i + 1, parameters[i]);
				}
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int colSize = rsmd.getColumnCount() + 1;
			Map<String, String> map = new HashMap<String, String>();
			if (rs.next()) {
				for (int i = 1; i < colSize; i++) {
					map.put(rsmd.getColumnLabel(i), rs.getString(i));
				}
			}
			return map;
		} finally {
			close(rs, ps, conn);
		}
	}

	/**
	 * 适用于select count(*) from table 语句
	 * 
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public static int queryCount(String sql, Object... parameters)
			throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBPool.getConnection();
			ps = conn.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					setPreparedStatement(ps, i + 1, parameters[i]);
				}
			}
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
			return 0;
		} finally {
			close(ps, conn);
		}

	}

	public static void recoverAutoCommit(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void rollback(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int executeUpdate(String sql, Object... parameters)
			throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBPool.getConnection();
			ps = conn.prepareStatement(sql);
			if (parameters != null)
				for (int i = 0; i < parameters.length; i++) {
					setPreparedStatement(ps, i + 1, parameters[i]);
				}
			return ps.executeUpdate();
		} finally {
			close(ps, conn);
		}
	}

	public static int executeUpdate(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBPool.getConnection();
			ps = conn.prepareStatement(sql);
			return ps.executeUpdate();
		} finally {
			close(ps, conn);
		}
	}

	public static void close(ResultSet rs, Statement stm, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stm != null) {
			try {
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Statement sta, Connection conn) {
		if (sta != null) {
			try {
				sta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void setPreparedStatement(PreparedStatement ps, int index,
			Object value) throws SQLException {

		if (value instanceof String) {
			ps.setString(index, (String) value);
			return;
		}

		if (value instanceof Integer) {
			ps.setInt(index, (Integer) value);
			return;
		}

		if (value instanceof Date) {
			ps.setTimestamp(index, new Timestamp(((Date) value).getTime()));
			return;
		}
		if (value instanceof Double) {
			ps.setDouble(index, (Double) value);
			return;
		}
		if (value instanceof Long) {
			ps.setLong(index, (Long) value);
			return;
		}
		if (value instanceof Byte) {
			ps.setByte(index, (Byte) value);
			return;
		}
		if (value instanceof Float) {
			ps.setFloat(index, (Float) value);
			return;
		}
		if (value instanceof Short) {
			ps.setShort(index, (Short) value);
			return;
		}
		if (value == null) {
			ps.setObject(index, null);
			return;
		}
		ps.setObject(index, value);
	}

	private SqlUtil() {
	}

	private static String getType(String type) {
		if ("VARCHAR2".equalsIgnoreCase(type) || "CHAR".equalsIgnoreCase(type)
				|| "VARCHAR".equalsIgnoreCase(type)) {
			type = "String";
		} else if ("NUMBER".equalsIgnoreCase(type)) {
			type = "BigDecimal";
		} else if ("DATE".equalsIgnoreCase(type)) {
			type = "Date";
		} else if ("DATETIME".equalsIgnoreCase(type)) {
			type = "Date";
		} else if ("LONG".equalsIgnoreCase(type)) {
			type = "Long";
		} else if ("INT".equalsIgnoreCase(type)) {
			type = "int";
		} else {

		}
		return type;
	}
}
