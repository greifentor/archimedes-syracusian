package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.TableReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.TableAccessor;
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
class TableReaderServiceImpl implements TableReaderService {

	private final DatabaseTypeService databaseTypeService;
	private final List<TableAccessor> tableAccessors;

	private final Map<DatabaseType, TableAccessor> accessors = new HashMap<>();
	private TableAccessor defaultAccessor;

	@PostConstruct
	void postConstruct() {
		tableAccessors.stream().forEach(a -> accessors.put(a.getDatabaseType(), a));
		ensure(
			accessors.containsKey(DatabaseType.UNSPECIFIED),
			new IllegalStateException("TableAccessor implementation for UNSPECIFIED missed!")
		);
		defaultAccessor = accessors.get(DatabaseType.UNSPECIFIED);
	}

	@Override
	public Set<TableMDO> read(String schemeName, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		TableAccessor accessor = accessors.getOrDefault(databaseTypeService.getDatabaseType(connection), defaultAccessor);
		return accessor.getTables(schemeName, connection);
	}
}
