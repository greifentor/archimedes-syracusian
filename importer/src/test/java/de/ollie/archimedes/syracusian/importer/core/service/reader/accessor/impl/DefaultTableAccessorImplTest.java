package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultTableAccessorImplTest {

	private static final String SCHEME_NAME = "scheme-name";
	private static final String TABLE_NAME = "table-name";

	@Mock
	private Connection connection;

	@Mock
	private DatabaseMetaData metaData;

	@Mock
	private ResultSet resultSet;

	@InjectMocks
	private DefaultTableAccessorImpl unitUnderTest;

	@Nested
	class TestsOfMethod_getTables_String_Connection {

		@Test
		void throwsAnException_passingANullValueAsConnection() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getTables(SCHEME_NAME, null));
		}

		@Test
		void throwsAnException_passingANullValueAsSchemeName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getTables(null, connection));
		}

		@Test
		void returnsAnEmptySet_ifMetaDatDoesNotContainAnyTables() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(metaData);
			when(metaData.getTables(null, SCHEME_NAME, "%", new String[] { "TABLE" })).thenReturn(resultSet);
			when(resultSet.next()).thenReturn(false);
			// Run
			Set<TableMDO> returned = unitUnderTest.getTables(SCHEME_NAME, connection);
			// Check
			assertEquals(Set.of(), returned);
		}

		@Test
		void returnsASetWithTables_ifMetaDatContainsTables() throws Exception {
			// Prepare
			Set<TableMDO> expected = Set.of(new TableMDO().setName(TABLE_NAME));
			when(connection.getMetaData()).thenReturn(metaData);
			when(metaData.getTables(null, SCHEME_NAME, "%", new String[] { "TABLE" })).thenReturn(resultSet);
			when(resultSet.next()).thenReturn(true, false);
			when(resultSet.getString("TABLE_NAME")).thenReturn(TABLE_NAME);
			// Run
			Set<TableMDO> returned = unitUnderTest.getTables(SCHEME_NAME, connection);
			// Check
			assertEquals(expected, returned);
		}
	}
}
