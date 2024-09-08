package de.ollie.archimedes.syracusian.importer.service.accessor.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DatabaseSchemeNameAccessorImplTest {

	private static final String SCHEME_NAME = "scheme-name";

	@Mock
	private Connection connection;

	@InjectMocks
	private DatabaseSchemeNameAccessorImpl unitUnderTest;

	@Nested
	class TestsOfMethod_getDatabaseSchemeName_Connection {

		@Test
		void throwsAnException_passingANullValueAsConnection() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getDatabaseSchemeName(null));
		}

		@Test
		void returnsTheCorrectSchemeNameForTheConnection() throws Exception {
			when(connection.getSchema()).thenReturn(SCHEME_NAME);
			assertEquals(SCHEME_NAME, unitUnderTest.getDatabaseSchemeName(connection));
		}
	}
}
