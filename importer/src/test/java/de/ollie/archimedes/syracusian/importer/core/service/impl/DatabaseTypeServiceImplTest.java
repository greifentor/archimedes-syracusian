package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DatabaseTypeServiceImplTest {

	@Mock
	private Connection connection;

	@Mock
	private DatabaseMetaData metaData;

	@InjectMocks
	private DatabaseTypeServiceImpl unitUnderTest;

	@Nested
	class TestsOfMethod_getDatabaseType_Connection {

		@Test
		void throwsAnException_passingANullValueAsConnection() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getDatabaseType(null));
		}

		@Test
		void throwsAnException_passingAnInvalidConnection() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(metaData);
			when(metaData.getDriverName()).thenThrow(new SQLException());
			// Run & Check
			ImportFailureException e = assertThrows(
				ImportFailureException.class,
				() -> unitUnderTest.getDatabaseType(connection)
			);
			assertEquals(ReasonType.DATABASE_TYPE_READ_ERROR, e.getReasonType());
		}

		@Test
		void returnsDatabaseTypeUNSPECIFIED_passingAConnectionWithNoMatchingDriverNameForHSQL() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(metaData);
			when(metaData.getDriverName()).thenReturn(";op");
			// Run & Check
			assertEquals(DatabaseType.UNSPECIFIED, unitUnderTest.getDatabaseType(connection));
		}

		@Test
		void returnsDatabaseTypeHSQL_passingAConnectionWithAMatchingDriverNameForHSQL() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(metaData);
			when(metaData.getDriverName()).thenReturn("org.hsqldb.jdbc.JDBCDriver");
			// Run & Check
			assertEquals(DatabaseType.HSQL, unitUnderTest.getDatabaseType(connection));
		}
	}
}
