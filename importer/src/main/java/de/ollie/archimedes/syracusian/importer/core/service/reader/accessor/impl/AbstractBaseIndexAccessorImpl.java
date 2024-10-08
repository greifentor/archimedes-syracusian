package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.MessageParameter;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.importer.core.model.ColumnProvider;
import de.ollie.archimedes.syracusian.importer.core.model.Index;
import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractBaseIndexAccessorImpl<T extends Index> {

	protected Set<T> get(String schemeName, TableMDO table, Connection connection, boolean unique) {
		ensure(connection != null, "connection cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		ensure(table != null, "table cannot be null!");
		try {
			Map<String, T> ts = new HashMap<>();
			ResultSet rs = connection.getMetaData().getIndexInfo(null, schemeName, table.getName(), unique, false);
			while (rs.next()) {
				String name = rs.getString("INDEX_NAME");
				if (isToIgnore(rs, table, name)) {
					continue;
				}
				T t = ts.get(name);
				if (t == null) {
					t = createWithName(name);
					ts.put(name, t);
				}
				t.addColumn(rs.getString("COLUMN_NAME"));
			}
			return ts
				.entrySet()
				.stream()
				.map(e -> e.getValue())
				.filter(uc -> !isPrimaryKeyConstraint(table, uc))
				.collect(Collectors.toSet());
		} catch (SQLException e) {
			throw new ImportFailureException(
				"reading " + getSubject() + " from connection failed",
				ReasonType.COLUMN_DATA_READ_ERROR,
				e,
				new MessageParameter("scheme", schemeName),
				new MessageParameter("table", table.getName())
			);
		}
	}

	protected boolean isToIgnore(ResultSet rs, TableMDO table, String indexName) throws SQLException {
		return false;
	}

	abstract String getSubject();

	abstract T createWithName(String name);

	private boolean isPrimaryKeyConstraint(TableMDO t, ColumnProvider<?> cp) {
		Set<String> pkColumnNames = t.getPkColumnNames().stream().collect(Collectors.toSet());
		return (cp != null) && pkColumnNames.equals(cp.getColumnNames());
	}
}
