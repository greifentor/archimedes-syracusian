package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.MessageParameter;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.importer.core.model.UniqueConstraintMDO;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.UniqueConstraintsAccessor;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class DefaultUniqueConstraintsAccessorImpl implements UniqueConstraintsAccessor {

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.UNSPECIFIED;
	}

	@Override
	public Set<UniqueConstraintMDO> getUniqueConstraints(String schemeName, String tableName, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		ensure(tableName != null, "table name cannot be null!");
		try {
			Map<String, UniqueConstraintMDO> ucs = new HashMap<>();
			ResultSet rs = connection.getMetaData().getIndexInfo(null, schemeName, tableName, true, false);
			while (rs.next()) {
				String name = rs.getString("INDEX_NAME");
				UniqueConstraintMDO uc = ucs.get(name);
				if (uc == null) {
					uc = new UniqueConstraintMDO().setName(name);
					ucs.put(name, uc);
				}
				uc.addColumn(rs.getString("COLUMN_NAME"));
			}
			return ucs.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toSet());
		} catch (SQLException e) {
			throw new ImportFailureException(
				"reading unique constraints from connection failed",
				ReasonType.COLUMN_DATA_READ_ERROR,
				e,
				new MessageParameter("scheme", schemeName),
				new MessageParameter("table", tableName)
			);
		}
	}
}
