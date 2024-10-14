package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.model.ColumnMDO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MariaDbColumnAccessorImplTest {

	private static final String COLUMN_NAME_A = "column-a";
	private static final String COLUMN_NAME_B = "column-b";
	private static final String SCHEME_NAME = "scheme-name";
	private static final String TABLE_NAME = "table-name";

	@Mock
	private ColumnMDO columnA;

	@Mock
	private ColumnMDO columnB;

	@Mock
	private Connection connection;

	@Mock
	private DatabaseMetaData databaseMetaData;

	@Mock
	private ResultSet resultSet;

	@InjectMocks
	private MariaDbColumnAccessorImpl unitUnderTest;

	@Nested
	class TestsOfMethod_getColumns_String_String_Connection {

		@Test
		void throwsAnException_passingConnectionAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getColumns(SCHEME_NAME, TABLE_NAME, null));
		}

		@Test
		void throwsAnException_passingTableNameAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getColumns(SCHEME_NAME, null, connection));
		}

		@Test
		void throwsAnException_passingSchemeNameAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getColumns(null, TABLE_NAME, connection));
		}

		@Test
		void throwsAnException_whenSomethingWentWrongWhileRetrievingPks() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getColumns(SCHEME_NAME, "%", TABLE_NAME, "%")).thenThrow(new SQLException());
			// Run & Check
			assertThrows(ImportFailureException.class, () -> unitUnderTest.getColumns(SCHEME_NAME, TABLE_NAME, connection));
		}

		@Test
		void returnsAnEmptySet_ifMetaDataDoesNotContainAnyColumns() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getColumns(SCHEME_NAME, "%", TABLE_NAME, "%")).thenReturn(resultSet);
			when(resultSet.next()).thenReturn(false);
			// Run
			Set<ColumnMDO> returned = unitUnderTest.getColumns(SCHEME_NAME, TABLE_NAME, connection);
			// Check
			assertEquals(Set.of(), returned);
		}

		@Test
		void returnsASetWithTheCorrectColumns_passingValidConnectionSchemeNameAndTableName() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getColumns(SCHEME_NAME, "%", TABLE_NAME, "%")).thenReturn(resultSet);
			when(resultSet.getString("COLUMN_NAME")).thenReturn(COLUMN_NAME_A, COLUMN_NAME_B);
			when(resultSet.next()).thenReturn(true, true, false);
			// Run
			Set<ColumnMDO> returned = unitUnderTest.getColumns(SCHEME_NAME, TABLE_NAME, connection);
			// Check
			assertEquals(Set.of(new ColumnMDO().setName(COLUMN_NAME_A), new ColumnMDO().setName(COLUMN_NAME_B)), returned);
		}

		@Test
		void returnsASetWithTheCorrectColumns_withNotNullColumns_passingValidConnectionSchemeNameAndTableName()
			throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getColumns(SCHEME_NAME, "%", TABLE_NAME, "%")).thenReturn(resultSet);
			when(resultSet.getString("COLUMN_NAME")).thenReturn(COLUMN_NAME_A, COLUMN_NAME_B);
			when(resultSet.getBoolean("NULLABLE")).thenReturn(true, false);
			when(resultSet.next()).thenReturn(true, true, false);
			// Run
			Set<ColumnMDO> returned = unitUnderTest.getColumns(SCHEME_NAME, TABLE_NAME, connection);
			// Check
			assertEquals(
				Set.of(
					new ColumnMDO().setName(COLUMN_NAME_A).setNullable(true),
					new ColumnMDO().setName(COLUMN_NAME_B).setNullable(false)
				),
				returned
			);
		}
	}
}
