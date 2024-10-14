package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.MessageParameter;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.importer.core.model.ColumnMDO;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.ColumnAccessor;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Named
abstract class AbstractColumnAccessorImpl implements ColumnAccessor {

	@Override
	public Set<ColumnMDO> getColumns(String schemeName, String tableName, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		ensure(tableName != null, "table name cannot be null!");
		try {
			Set<ColumnMDO> columns = new HashSet<>();
			ResultSet rs = getColumnResultSet(schemeName, tableName, connection);
			while (rs.next()) {
				ColumnMDO c = new ColumnMDO().setName(rs.getString("COLUMN_NAME")).setNullable(rs.getBoolean("NULLABLE"));
				columns.add(c);
			}
			return columns;
		} catch (SQLException e) {
			throw new ImportFailureException(
				"reading column from connection failed",
				ReasonType.COLUMN_DATA_READ_ERROR,
				e,
				new MessageParameter("scheme", schemeName),
				new MessageParameter("table", tableName)
			);
		}
	}

	protected ResultSet getColumnResultSet(String schemeName, String tableName, Connection connection)
		throws SQLException {
		return connection.getMetaData().getColumns(null, schemeName, tableName, "%");
	}
}
