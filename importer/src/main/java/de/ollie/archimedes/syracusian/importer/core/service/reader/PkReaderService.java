package de.ollie.archimedes.syracusian.importer.core.service.reader;

import java.sql.Connection;
import java.util.Set;

public interface PkReaderService {
	Set<String> read(String schemeName, String tableName, Connection connection);
}
