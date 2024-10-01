package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.model.ForeignKeyMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.FkReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.FkAccessor;
import jakarta.inject.Named;
import java.sql.Connection;
import java.util.List;
import java.util.Set;

@Named
public class FkReaderServiceImpl extends AbstractReaderServiceImpl<FkAccessor> implements FkReaderService {

	public FkReaderServiceImpl(DatabaseTypeService databaseTypeService, List<FkAccessor> accessorsList) {
		super(databaseTypeService, accessorsList);
	}

	@Override
	public Set<ForeignKeyMDO> read(String schemeName, String tableName, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		ensure(tableName != null, "table name cannot be null!");
		FkAccessor accessor = accessors.getOrDefault(databaseTypeService.getDatabaseType(connection), defaultAccessor);
		return accessor.getFks(schemeName, tableName, connection);
	}
}
