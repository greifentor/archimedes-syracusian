package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultDatabaseSchemeAccessorImplTest {

	private static final String SCHEME_NAME = "scheme-name";

	@Mock
	private Connection connection;

	@InjectMocks
	private DefaultDatabaseSchemeAccessorImpl unitUnderTest;

	@Nested
	class TestsOfMethod_getDatabaseType {

		@Test
		void returnsTheCorrectDatabaseType() {
			assertEquals(DatabaseType.UNSPECIFIED, unitUnderTest.getDatabaseType());
		}
	}

	@Nested
	class TestsOfMethod_getName_Connection {

		@Test
		void throwsAnException_passingConnectionAsNull() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getName(SCHEME_NAME, null));
		}

		@Test
		void throwsAnException_whenConnectionAccessFails() throws Exception {
			when(connection.getSchema()).thenThrow(new SQLException());
			ImportFailureException e = assertThrows(
				ImportFailureException.class,
				() -> unitUnderTest.getName(SCHEME_NAME, connection)
			);
			assertEquals(ReasonType.SCHEME_NAME_READ_ERROR, e.getReasonType());
		}

		@Test
		void returnsTheCorrectSchemeNameForTheConnection() throws Exception {
			when(connection.getSchema()).thenReturn(SCHEME_NAME);
			assertEquals(SCHEME_NAME, unitUnderTest.getName(SCHEME_NAME, connection));
		}
	}
}
