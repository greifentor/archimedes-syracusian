package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.MessageParameter;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.importer.core.model.PrimaryKeyMDO;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.PkAccessor;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
abstract class AbstractPkAccessorImpl implements PkAccessor {

	@Override
	public PrimaryKeyMDO getPk(String schemeName, String tableName, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		ensure(tableName != null, "table name cannot be null!");
		try {
			PrimaryKeyMDO pk = null;
			ResultSet rs = getPrimaryKeyResultSet(schemeName, tableName, connection);
			while (rs.next()) {
				if (pk == null) {
					pk = new PrimaryKeyMDO().setName(rs.getString("PK_NAME"));
				}
				String cn = rs.getString("COLUMN_NAME");
				pk.addMemberColumnName(cn);
			}
			return pk;
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

	protected ResultSet getPrimaryKeyResultSet(String schemeName, String tableName, Connection connection)
		throws SQLException {
		return connection.getMetaData().getPrimaryKeys(null, schemeName, tableName);
	}
}
