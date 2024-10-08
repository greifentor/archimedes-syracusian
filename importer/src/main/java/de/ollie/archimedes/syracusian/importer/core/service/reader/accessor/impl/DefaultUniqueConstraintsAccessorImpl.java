package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.model.UniqueConstraintMDO;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.UniqueConstraintsAccessor;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import jakarta.inject.Named;
import java.sql.Connection;
import java.util.Set;

@Named
public class DefaultUniqueConstraintsAccessorImpl
	extends AbstractBaseIndexAccessorImpl<UniqueConstraintMDO>
	implements UniqueConstraintsAccessor {

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.UNSPECIFIED;
	}

	@Override
	public Set<UniqueConstraintMDO> getUniqueConstraints(String schemeName, TableMDO table, Connection connection) {
		return get(schemeName, table, connection, true);
	}

	@Override
	String getSubject() {
		return "unique constraints";
	}

	@Override
	UniqueConstraintMDO createWithName(String name) {
		return new UniqueConstraintMDO().setName(name);
	}
}
