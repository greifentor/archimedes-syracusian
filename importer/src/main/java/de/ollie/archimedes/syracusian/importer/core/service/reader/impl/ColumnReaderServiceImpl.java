package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.model.ColumnMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.ColumnReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.ColumnAccessor;
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
public class ColumnReaderServiceImpl implements ColumnReaderService {

	private final DatabaseTypeService databaseTypeService;
	private final List<ColumnAccessor> columnAccessors;

	private final Map<DatabaseType, ColumnAccessor> accessors = new HashMap<>();
	private ColumnAccessor defaultAccessor;

	@PostConstruct
	void postConstruct() {
		columnAccessors.stream().forEach(a -> accessors.put(a.getDatabaseType(), a));
		ensure(
			accessors.containsKey(DatabaseType.UNSPECIFIED),
			new IllegalStateException("ColumnAccessor implementation for UNSPECIFIED missed!")
		);
		defaultAccessor = accessors.get(DatabaseType.UNSPECIFIED);
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
