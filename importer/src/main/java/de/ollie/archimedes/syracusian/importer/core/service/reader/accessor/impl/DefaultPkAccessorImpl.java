package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import de.ollie.archimedes.syracusian.model.DatabaseType;
import jakarta.inject.Named;

@Named
public class DefaultPkAccessorImpl extends AbstractPkAccessorImpl {

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.UNSPECIFIED;
	}
}
