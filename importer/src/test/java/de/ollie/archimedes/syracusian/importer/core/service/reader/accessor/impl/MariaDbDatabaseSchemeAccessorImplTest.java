package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.ollie.archimedes.syracusian.model.DatabaseType;
import java.sql.Connection;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MariaDbDatabaseSchemeAccessorImplTest {

	private static final String SCHEME_NAME = "scheme-name";

	@Mock
	private Connection connection;

	@InjectMocks
	private MariaDbDatabaseSchemeAccessorImpl unitUnderTest;

	@Nested
	class TestsOfMethod_getDatabaseType {

		@Test
		void returnsTheCorrectDatabaseType() {
			assertEquals(DatabaseType.MARIA_DB, unitUnderTest.getDatabaseType());
		}
	}

	@Nested
	class TestsOfMethod_getName_String_Connection {

		@Test
		void throwsAnException_passingSchemeNameAsNull() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getName(null, connection));
		}

		@Test
		void throwsAnException_passingConnectionAsNull() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getName(SCHEME_NAME, null));
		}

		@Test
		void returnsTheCorrectSchemeNameForTheConnection() throws Exception {
			assertEquals(SCHEME_NAME, unitUnderTest.getName(SCHEME_NAME, connection));
		}
	}
}
