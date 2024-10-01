package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.TableReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.TableAccessor;
import jakarta.inject.Named;
import java.sql.Connection;
import java.util.List;
import java.util.Set;

@Named
class TableReaderServiceImpl extends AbstractReaderServiceImpl<TableAccessor> implements TableReaderService {

	public TableReaderServiceImpl(DatabaseTypeService databaseTypeService, List<TableAccessor> accessorsList) {
		super(databaseTypeService, accessorsList);
	}

	@Override
	public Set<TableMDO> read(String schemeName, Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		TableAccessor accessor = accessors.getOrDefault(databaseTypeService.getDatabaseType(connection), defaultAccessor);
		return accessor.getTables(schemeName, connection);
	}
}
