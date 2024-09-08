package de.ollie.archimedes.syracusian.importer.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.ollie.archimedes.syracusian.importer.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.model.JDBCConnectionData;
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
		void returnsDataschemeObject_withCorrectNameSet() {
			// Prepare
			JDBCConnectionData connectionData = new JDBCConnectionData(
				"org.hsqldb.jdbc.JDBCDriver",
				"jdbc:hsqldb:file:src/test/resources/test-db/test-db",
				"sa",
				null
			);
			// Run
			DatabaseSchemeMDO returned = unitUnderTest.readDatabaseScheme(SCHEME_NAME, connectionData);
			// Check
			assertEquals(SCHEME_NAME, returned.getName());
		}
	}
}
