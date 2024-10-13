package de.ollie.archimedes.syracusian.shell.command;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseSchemeImporterService;
import de.ollie.archimedes.syracusian.importer.core.service.JDBCConnectionDataFactory;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImporterCommandsTest {

	private static final String DATABASE_URL = "database-url";
	private static final String DATABASE_USER_NAME = "database-user-name";
	private static final String DATABASE_USER_PASSWORD = "database-user-password";
	private static final String DRIVER_CLASS_NAME = "driver-class-name";
	private static final String SCHEME_NAME = "scheme-name";
	private static final String STRING_REPRESENTATION = "string-representation";

	@Mock
	private JDBCConnectionData connectionData;

	@Mock
	private DatabaseSchemeMDO mdo;

	@Mock
	private JDBCConnectionDataFactory jdbcConnectionDataFactory;

	@Mock
	private DatabaseSchemeImporterService databaseSchemeImporterService;

	@InjectMocks
	private ImporterCommands unitUnderTest;

	@Nested
	class TestsOfMethod_importDatabaseScheme_String_String_String_String_String {

		@Test
		void returnsTheStringRepresentationOfTheDatabaseSchemeReturnedByTheService() {
			// Prepare
			when(
				jdbcConnectionDataFactory.create(DRIVER_CLASS_NAME, DATABASE_URL, DATABASE_USER_NAME, DATABASE_USER_PASSWORD)
			)
				.thenReturn(connectionData);
			when(databaseSchemeImporterService.readDatabaseScheme(SCHEME_NAME, connectionData)).thenReturn(mdo);
			when(mdo.toString()).thenReturn(STRING_REPRESENTATION);
			// Run & Check
			assertSame(
				STRING_REPRESENTATION,
				unitUnderTest.importDatabaseScheme(
					SCHEME_NAME,
					DRIVER_CLASS_NAME,
					DATABASE_URL,
					DATABASE_USER_NAME,
					DATABASE_USER_PASSWORD
				)
			);
		}
	}
}
