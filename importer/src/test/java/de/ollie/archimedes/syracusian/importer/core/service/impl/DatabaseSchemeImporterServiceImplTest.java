package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseConnectionFactory;
import de.ollie.archimedes.syracusian.importer.core.service.reader.DatabaseSchemeReaderService;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DatabaseSchemeImporterServiceImplTest {

	private static final String SCHEME_NAME = "PUBLIC";

	@Mock
	private Connection connection;

	@Mock
	private DatabaseConnectionFactory databaseConnectionFactory;

	@Mock
	private DatabaseSchemeReaderService databaseSchemeReaderService;

	@Mock
	private JDBCConnectionData connectionData;

	@InjectMocks
	private DatabaseSchemeImporterServiceImpl unitUnderTest;

	@Nested
	class TestsOfMethod_readDatabaseScheme_String_JDBCConnectionData {

		@Test
		void throwsAnException_passingANullValueAsSchemeName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.readDatabaseScheme(null, connectionData));
		}

		@Test
		void throwsAnException_passingANullValueAsJDBCConnection() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.readDatabaseScheme(SCHEME_NAME, null));
		}

		@Test
		void throwsAnException_whenSomethingWentWrongDuringDatabaseAccess() throws Exception {
			when(databaseConnectionFactory.create(connectionData)).thenThrow(new SQLException());
			assertThrows(ImportFailureException.class, () -> unitUnderTest.readDatabaseScheme(SCHEME_NAME, connectionData));
		}

		@Test
		void returnsDataschemeObject_withCorrectNameSet() throws Exception {
			// Prepare
			when(databaseConnectionFactory.create(connectionData)).thenReturn(connection);
			when(databaseSchemeReaderService.read(connection)).thenReturn(new DatabaseSchemeMDO(SCHEME_NAME, Set.of()));
			// Run
			DatabaseSchemeMDO returned = unitUnderTest.readDatabaseScheme(SCHEME_NAME, connectionData);
			// Check
			assertEquals(SCHEME_NAME, returned.getName());
		}
	}
}
