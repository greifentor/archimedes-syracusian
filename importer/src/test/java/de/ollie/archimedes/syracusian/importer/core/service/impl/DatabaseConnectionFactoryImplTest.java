package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DatabaseConnectionFactoryImplTest {

	@InjectMocks
	private DatabaseConnectionFactoryImpl unitUnderTest;

	@Nested
	class TestsOfMethod_create_JDBCConnection {

		@Test
		void returnsAnObject_passingValidParameters() throws Exception {
			// Prepare
			JDBCConnectionData connectionData = new JDBCConnectionData(
				"org.hsqldb.jdbc.JDBCDriver",
				"jdbc:hsqldb:file:src/test/resources/test-db/test-db",
				"sa",
				null
			);
			// Run & Check
			assertNotNull(unitUnderTest.create(connectionData));
		}
	}
}
