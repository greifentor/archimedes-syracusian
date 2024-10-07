package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.model.IndexMDO;
import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.IndicesReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.IndicesAccessor;
import jakarta.inject.Named;
import java.sql.Connection;
import java.util.List;
import java.util.Set;

@Named
public class IndicesReaderServiceImpl
	extends AbstractReaderServiceImpl<IndicesAccessor>
	implements IndicesReaderService {

	public IndicesReaderServiceImpl(DatabaseTypeService databaseTypeService, List<IndicesAccessor> accessorsList) {
		super(databaseTypeService, accessorsList);
	}

	@Override
	public Set<IndexMDO> read(String schemeName, TableMDO table, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		ensure(table != null, "table cannot be null!");
		IndicesAccessor accessor = accessors.getOrDefault(databaseTypeService.getDatabaseType(connection), defaultAccessor);
		return accessor.getIndices(schemeName, table, connection);
	}
}
