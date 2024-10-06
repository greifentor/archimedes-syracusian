package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.model.PrimaryKeyMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.PkReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.PkAccessor;
import jakarta.inject.Named;
import java.sql.Connection;
import java.util.List;

@Named
public class PkReaderServiceImpl extends AbstractReaderServiceImpl<PkAccessor> implements PkReaderService {

	public PkReaderServiceImpl(DatabaseTypeService databaseTypeService, List<PkAccessor> accessorsList) {
		super(databaseTypeService, accessorsList);
	}

	@Override
	public PrimaryKeyMDO read(String schemeName, String tableName, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		ensure(tableName != null, "table name cannot be null!");
		PkAccessor accessor = accessors.getOrDefault(databaseTypeService.getDatabaseType(connection), defaultAccessor);
		return accessor.getPk(schemeName, tableName, connection);
	}
}
