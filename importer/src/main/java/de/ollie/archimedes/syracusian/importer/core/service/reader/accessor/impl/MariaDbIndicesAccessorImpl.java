package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import de.ollie.archimedes.syracusian.importer.core.model.ForeignKeyMDO;
import de.ollie.archimedes.syracusian.importer.core.model.IndexMDO;
import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.IndicesAccessor;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@Named
public class MariaDbIndicesAccessorImpl extends AbstractBaseIndexAccessorImpl<IndexMDO> implements IndicesAccessor {

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.MARIA_DB;
	}

	@Override
	protected boolean isToIgnore(ResultSet rs, TableMDO table, String indexName) throws SQLException {
		boolean nonUnique = rs.getBoolean("NON_UNIQUE");
		return !nonUnique || isAForeignKey(indexName, table.getForeignKeys());
	}

	private boolean isAForeignKey(String indexName, Set<ForeignKeyMDO> fks) {
		return fks.stream().anyMatch(fk -> indexName.contains(fk.getName()));
	}

	@Override
	public Set<IndexMDO> getIndices(String schemeName, TableMDO table, Connection connection) {
		return get(schemeName, table, connection, false);
	}

	@Override
	String getSubject() {
		return "indices";
	}

	@Override
	IndexMDO createWithName(String name) {
		return new IndexMDO().setName(name);
	}

	@Override
	protected ResultSet getIndexResultSet(String schemeName, TableMDO table, boolean unique, Connection connection)
		throws SQLException {
		return connection.getMetaData().getIndexInfo(schemeName, "%", table.getName(), unique, false);
	}
}
