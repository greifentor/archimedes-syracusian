package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.MessageParameter;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.importer.core.model.ForeignKeyMDO;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.FkAccessor;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Named
abstract class AbstractFkAccessorImpl implements FkAccessor {

	@Override
	public Set<ForeignKeyMDO> getFks(String schemeName, String tableName, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		ensure(tableName != null, "table name cannot be null!");
		try {
			Set<ForeignKeyMDO> fks = new HashSet<>();
			ResultSet rs = getFksResultSet(schemeName, tableName, connection);
			while (rs.next()) {
				fks.add(
					new ForeignKeyMDO()
						.setColumnName(rs.getString("FKCOLUMN_NAME"))
						.setName(rs.getString("FK_NAME"))
						.setReferencedColumnName(rs.getString("PKCOLUMN_NAME"))
						.setReferencedTableName(rs.getString("PKTABLE_NAME"))
				);
			}
			return fks;
		} catch (SQLException e) {
			throw new ImportFailureException(
				"reading foreign keys from connection failed",
				ReasonType.COLUMN_DATA_READ_ERROR,
				e,
				new MessageParameter("scheme", schemeName),
				new MessageParameter("table", tableName)
			);
		}
	}

	protected ResultSet getFksResultSet(String schemeName, String tableName, Connection connection) throws SQLException {
		return connection.getMetaData().getImportedKeys(null, schemeName, tableName);
	}
}
