package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.MessageParameter;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.TableAccessor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

abstract class AbstractTableAccessorImpl implements TableAccessor {

	@Override
	public Set<TableMDO> getTables(String schemeName, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		try {
			Set<TableMDO> tables = new HashSet<>();
			ResultSet rs = getTableResultSet(schemeName, connection);
			while (rs.next()) {
				TableMDO t = new TableMDO().setName(rs.getString("TABLE_NAME"));
				tables.add(t);
			}
			return tables;
		} catch (SQLException e) {
			throw new ImportFailureException(
				"reading schema from connection failed",
				ReasonType.TABLE_DATA_READ_ERROR,
				e,
				new MessageParameter("scheme", schemeName)
			);
		}
	}

	protected ResultSet getTableResultSet(String schemeName, Connection connection) throws SQLException {
		return connection.getMetaData().getTables(null, schemeName, "%", new String[] { "TABLE" });
	}
}
