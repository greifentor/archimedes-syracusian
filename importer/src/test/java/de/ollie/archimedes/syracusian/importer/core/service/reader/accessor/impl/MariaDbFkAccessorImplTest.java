package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.model.ForeignKeyMDO;
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
public class MariaDbFkAccessorImplTest {

	private static final String COLUMN_NAME = "column";
	private static final String COLUMN_NAME_REF = "column-ref";
	private static final String FK_NAME = "fk-name";
	private static final String SCHEME_NAME = "scheme-name";
	private static final String TABLE_NAME_REF = "table-name";

	@Mock
	private Connection connection;

	@Mock
	private DatabaseMetaData databaseMetaData;

	@Mock
	private ResultSet resultSet;

	@InjectMocks
	private MariaDbFkAccessorImpl unitUnderTest;

	@Nested
	class TestsOfMethod_getTables_String_String_Connection {

		@Test
		void throwsAnException_passingConnectionAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getFks(SCHEME_NAME, TABLE_NAME_REF, null));
		}

		@Test
		void throwsAnException_passingTableNameAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getFks(SCHEME_NAME, null, connection));
		}

		@Test
		void throwsAnException_passingSchemeNameAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getFks(null, TABLE_NAME_REF, connection));
		}

		@Test
		void throwsAnException_whenSomethingWentWrongWhileRetrievingPks() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getImportedKeys(SCHEME_NAME, "%", TABLE_NAME_REF)).thenThrow(new SQLException());
			// Run & Check
			assertThrows(ImportFailureException.class, () -> unitUnderTest.getFks(SCHEME_NAME, TABLE_NAME_REF, connection));
		}

		@Test
		void returnsAnEmptySet_ifMetaDataDoesNotContainAnyColumns() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getImportedKeys(SCHEME_NAME, "%", TABLE_NAME_REF)).thenReturn(resultSet);
			when(resultSet.next()).thenReturn(false);
			// Run
			Set<ForeignKeyMDO> returned = unitUnderTest.getFks(SCHEME_NAME, TABLE_NAME_REF, connection);
			// Check
			assertEquals(Set.of(), returned);
		}

		@Test
		void returnsASetWithTheCorrectPkColumnNames_passingValidConnectionSchemeNameAndTableName() throws Exception {
			// Prepare
			when(connection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getImportedKeys(SCHEME_NAME, "%", TABLE_NAME_REF)).thenReturn(resultSet);
			when(resultSet.getString("FKCOLUMN_NAME")).thenReturn(COLUMN_NAME);
			when(resultSet.getString("FK_NAME")).thenReturn(FK_NAME);
			when(resultSet.getString("PKCOLUMN_NAME")).thenReturn(COLUMN_NAME_REF);
			when(resultSet.getString("PKTABLE_NAME")).thenReturn(TABLE_NAME_REF);
			when(resultSet.next()).thenReturn(true, false);
			// Run
			Set<ForeignKeyMDO> returned = unitUnderTest.getFks(SCHEME_NAME, TABLE_NAME_REF, connection);
			// Check
			assertEquals(
				Set.of(
					new ForeignKeyMDO()
						.setColumnName(COLUMN_NAME)
						.setName(FK_NAME)
						.setReferencedColumnName(COLUMN_NAME_REF)
						.setReferencedTableName(TABLE_NAME_REF)
				),
				returned
			);
		}
	}
}
