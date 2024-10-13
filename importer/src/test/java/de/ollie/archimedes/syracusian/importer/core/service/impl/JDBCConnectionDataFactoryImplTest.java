package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JDBCConnectionDataFactoryImplTest {

	private static final String DATABASE_URL = "database-url";
	private static final String DATABASE_USER_NAME = "database-user-name";
	private static final String DATABASE_USER_PASSWORD = "database-user-password";
	private static final String DRIVER_CLASS_NAME = "driver-class-name";

	@InjectMocks
	private JDBCConnectionDataFactoryImpl unitUnderTest;

	@Nested
	class TestsOfMethod_create_String_String_String_String {

		@Test
		void returnsACorrectJDBCConnectionDataObject() {
			// Prepare
			JDBCConnectionData expected = new JDBCConnectionData(
				DRIVER_CLASS_NAME,
				DATABASE_URL,
				DATABASE_USER_NAME,
				DATABASE_USER_PASSWORD
			);
			// Run
			JDBCConnectionData returned = unitUnderTest.create(
				DRIVER_CLASS_NAME,
				DATABASE_URL,
				DATABASE_USER_NAME,
				DATABASE_USER_PASSWORD
			);
			// Check
			assertEquals(expected, returned);
		}
	}
}
