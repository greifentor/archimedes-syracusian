package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.model.ColumnMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.ColumnReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.ColumnAccessor;
import jakarta.inject.Named;
import java.sql.Connection;
import java.util.List;
import java.util.Set;

@Named
public class ColumnReaderServiceImpl extends AbstractReaderServiceImpl<ColumnAccessor> implements ColumnReaderService {

	public ColumnReaderServiceImpl(DatabaseTypeService databaseTypeService, List<ColumnAccessor> accessorsList) {
		super(databaseTypeService, accessorsList);
	}

	@Override
	public Set<ColumnMDO> read(String schemeName, String tableName, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		ensure(tableName != null, "table name cannot be null!");
		ColumnAccessor accessor = accessors.getOrDefault(databaseTypeService.getDatabaseType(connection), defaultAccessor);
		return accessor.getColumns(schemeName, tableName, connection);
	}
}
