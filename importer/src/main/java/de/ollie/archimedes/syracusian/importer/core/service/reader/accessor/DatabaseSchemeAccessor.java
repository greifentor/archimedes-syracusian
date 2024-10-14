package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor;

import java.sql.Connection;

public interface DatabaseSchemeAccessor extends Accessor {
	String getName(String schemeName, Connection connection);
}
