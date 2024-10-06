package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.model.UniqueConstraintMDO;
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
public class DefaultUniqueConstraintsAccessorImplTest {

	private static final String COLUMN_NAME_0 = "column-0";
	private static final String COLUMN_NAME_1 = "column-1";
	private static final String INDEX_NAME = "index-name";
	private static final String SCHEME_NAME = "scheme-name";
	private static final String TABLE_NAME = "table-name";

	@Mock
	private Connection connection;

	@Mock
	private DatabaseMetaData databaseMetaData;

	@Mock
	private ResultSet resultSet;

	@InjectMocks
	private DefaultUniqueConstraintsAccessorImpl unitUnderTest;

	@Nested
	class TestsOfMethod_getTables_String_String_Connection {

		@Test
		void throwsAnException_passingConnectionAsNullValue() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.getUniqueConstraints(SCHEME_NAME, TABLE_NAME, null)
			);
		}

		@Test
		void throwsAnException_passingTableNameAsNullValue() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.getUniqueConstraints(SCHEME_NAME, null, connection)
			);
		}

		@Test
		void throwsAnException_passingSchemeNameAsNullValue() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.getUniqueConstraints(null, TABLE_NAME, connection)
			);
		}

		@Test
		void throwsAnException_whenSomethingWentWrongWhileRetrievingPks() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getIndexInfo(null, SCHEME_NAME, TABLE_NAME, true, false)).thenThrow(new SQLException());
			// Run & Check
			assertThrows(
				ImportFailureException.class,
				() -> unitUnderTest.getUniqueConstraints(SCHEME_NAME, TABLE_NAME, connection)
			);
		}

		@Test
		void returnsAnEmptySet_ifMetaDataDoesNotContainAnyColumns() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getIndexInfo(null, SCHEME_NAME, TABLE_NAME, true, false)).thenReturn(resultSet);
			when(resultSet.next()).thenReturn(false);
			// Run
			Set<UniqueConstraintMDO> returned = unitUnderTest.getUniqueConstraints(SCHEME_NAME, TABLE_NAME, connection);
			// Check
			assertEquals(Set.of(), returned);
		}

		@Test
		void returnsASetWithTheCorrectUniqueConstraints_passingValidConnectionSchemeNameAndTableName() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getIndexInfo(null, SCHEME_NAME, TABLE_NAME, true, false)).thenReturn(resultSet);
			when(resultSet.getString("INDEX_NAME")).thenReturn(INDEX_NAME);
			when(resultSet.getString("COLUMN_NAME")).thenReturn(COLUMN_NAME_0);
			when(resultSet.next()).thenReturn(true, false);
			// Run
			Set<UniqueConstraintMDO> returned = unitUnderTest.getUniqueConstraints(SCHEME_NAME, TABLE_NAME, connection);
			// Check
			assertEquals(Set.of(new UniqueConstraintMDO().addColumn(COLUMN_NAME_0).setName(INDEX_NAME)), returned);
		}

		@Test
		void returnsASetWithTheCorrectUniqueConstraints_passingValidConnectionSchemeNameAndTableName_withMultiColumnUniqueConstraint()
			throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getIndexInfo(null, SCHEME_NAME, TABLE_NAME, true, false)).thenReturn(resultSet);
			when(resultSet.getString("INDEX_NAME")).thenReturn(INDEX_NAME);
			when(resultSet.getString("COLUMN_NAME")).thenReturn(COLUMN_NAME_0, COLUMN_NAME_1);
			when(resultSet.next()).thenReturn(true, true, false);
			// Run
			Set<UniqueConstraintMDO> returned = unitUnderTest.getUniqueConstraints(SCHEME_NAME, TABLE_NAME, connection);
			// Check
			assertEquals(
				Set.of(new UniqueConstraintMDO().addColumn(COLUMN_NAME_0).addColumn(COLUMN_NAME_1).setName(INDEX_NAME)),
				returned
			);
		}

		@Test
		void returnsASetWithTheCorrectUniqueConstraints_passingValidConnectionSchemeNameAndTableName_withTwoUniqueConstraints()
			throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getIndexInfo(null, SCHEME_NAME, TABLE_NAME, true, false)).thenReturn(resultSet);
			when(resultSet.getString("INDEX_NAME")).thenReturn(INDEX_NAME + 0, INDEX_NAME + 1);
			when(resultSet.getString("COLUMN_NAME")).thenReturn(COLUMN_NAME_0, COLUMN_NAME_1);
			when(resultSet.next()).thenReturn(true, true, false);
			// Run
			Set<UniqueConstraintMDO> returned = unitUnderTest.getUniqueConstraints(SCHEME_NAME, TABLE_NAME, connection);
			// Check
			assertEquals(
				Set.of(
					new UniqueConstraintMDO().addColumn(COLUMN_NAME_0).setName(INDEX_NAME + 0),
					new UniqueConstraintMDO().addColumn(COLUMN_NAME_1).setName(INDEX_NAME + 1)
				),
				returned
			);
		}
	}
}
