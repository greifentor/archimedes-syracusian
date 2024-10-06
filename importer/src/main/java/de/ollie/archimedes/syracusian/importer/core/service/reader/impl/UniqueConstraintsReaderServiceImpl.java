package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.model.UniqueConstraintMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.UniqueConstraintsReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.UniqueConstraintsAccessor;
import jakarta.inject.Named;
import java.sql.Connection;
import java.util.List;
import java.util.Set;

@Named
public class UniqueConstraintsReaderServiceImpl
	extends AbstractReaderServiceImpl<UniqueConstraintsAccessor>
	implements UniqueConstraintsReaderService {

	public UniqueConstraintsReaderServiceImpl(
		DatabaseTypeService databaseTypeService,
		List<UniqueConstraintsAccessor> accessorsList
	) {
		super(databaseTypeService, accessorsList);
	}

	@Override
	public Set<UniqueConstraintMDO> read(String schemeName, TableMDO table, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		ensure(table != null, "table cannot be null!");
		UniqueConstraintsAccessor accessor = accessors.getOrDefault(
			databaseTypeService.getDatabaseType(connection),
			defaultAccessor
		);
		return accessor.getUniqueConstraints(schemeName, table, connection);
	}
}
