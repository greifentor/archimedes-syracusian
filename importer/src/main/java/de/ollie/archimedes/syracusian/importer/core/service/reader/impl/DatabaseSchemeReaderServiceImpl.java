package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.DatabaseSchemeReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.DatabaseSchemeAccessor;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class DatabaseSchemeReaderServiceImpl implements DatabaseSchemeReaderService {

	private final DatabaseTypeService databaseTypeService;
	private final List<DatabaseSchemeAccessor> databaseSchemeAccessors;

	private final Map<DatabaseType, DatabaseSchemeAccessor> accessors = new HashMap<>();
	private DatabaseSchemeAccessor defaultAccessor;

	@PostConstruct
	void postConstruct() {
		databaseSchemeAccessors.stream().forEach(a -> accessors.put(a.getDatabaseType(), a));
		ensure(
			accessors.containsKey(DatabaseType.UNSPECIFIED),
			new IllegalStateException("DatabaseSchemeAccessor implementation for UNSPECIFIED missed!")
		);
		defaultAccessor = accessors.get(DatabaseType.UNSPECIFIED);
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
