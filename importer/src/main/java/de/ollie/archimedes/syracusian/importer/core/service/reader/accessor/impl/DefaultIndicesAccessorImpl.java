package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.MessageParameter;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.importer.core.model.ForeignKeyMDO;
import de.ollie.archimedes.syracusian.importer.core.model.IndexMDO;
import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.IndicesAccessor;
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
public class DefaultIndicesAccessorImpl implements IndicesAccessor {

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.UNSPECIFIED;
	}

	@Override
	public Set<IndexMDO> getIndices(String schemeName, TableMDO table, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		ensure(table != null, "table cannot be null!");
		try {
			Map<String, IndexMDO> idxs = new HashMap<>();
			ResultSet rs = connection.getMetaData().getIndexInfo(null, schemeName, table.getName(), false, false);
			while (rs.next()) {
				String name = rs.getString("INDEX_NAME");
				boolean nonUnique = rs.getBoolean("NON_UNIQUE");
				if (!nonUnique || isAForeignKey(name, table.getForeignKeys())) {
					continue;
				}
				IndexMDO idx = idxs.get(name);
				if (idx == null) {
					idx = new IndexMDO().setName(name);
					idxs.put(name, idx);
				}
				idx.addColumn(rs.getString("COLUMN_NAME"));
			}
			return idxs
				.entrySet()
				.stream()
				.map(e -> e.getValue())
				.filter(uc -> !table.isPrimaryKeyConstraint(uc))
				.collect(Collectors.toSet());
		} catch (SQLException e) {
			throw new ImportFailureException(
				"reading unique constraints from connection failed",
				ReasonType.COLUMN_DATA_READ_ERROR,
				e,
				new MessageParameter("scheme", schemeName),
				new MessageParameter("table", table.getName())
			);
		}
	}

	private boolean isAForeignKey(String indexName, Set<ForeignKeyMDO> fks) {
		return fks.stream().anyMatch(fk -> indexName.contains(fk.getName()));
	}
}
