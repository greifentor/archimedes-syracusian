package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.DatabaseSchemeReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.DatabaseSchemeAccessor;
import jakarta.inject.Named;
import java.sql.Connection;
import java.util.List;
import java.util.Set;

@Named
class DatabaseSchemeReaderServiceImpl
	extends AbstractReaderServiceImpl<DatabaseSchemeAccessor>
	implements DatabaseSchemeReaderService {

	public DatabaseSchemeReaderServiceImpl(
		DatabaseTypeService databaseTypeService,
		List<DatabaseSchemeAccessor> accessorsList
	) {
		super(databaseTypeService, accessorsList);
	}

	@Override
	public DatabaseSchemeMDO read(Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		DatabaseSchemeAccessor accessor = accessors.getOrDefault(
			databaseTypeService.getDatabaseType(connection),
			defaultAccessor
		);
		return new DatabaseSchemeMDO(accessor.getName(connection), Set.of());
	}
}
