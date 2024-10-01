package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.model.ForeignKeyMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.FkReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.PkReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.FkAccessor;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.PkAccessor;
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
public class FkReaderServiceImpl implements FkReaderService {

	private final DatabaseTypeService databaseTypeService;
	private final List<FkAccessor> fkAccessors;

	private final Map<DatabaseType, FkAccessor> accessors = new HashMap<>();
	private FkAccessor defaultAccessor;

	@PostConstruct
	void postConstruct() {
		fkAccessors.stream().forEach(a -> accessors.put(a.getDatabaseType(), a));
		ensure(
			accessors.containsKey(DatabaseType.UNSPECIFIED),
			new IllegalStateException("FkAccessor implementation for UNSPECIFIED missed!")
		);
		defaultAccessor = accessors.get(DatabaseType.UNSPECIFIED);
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
