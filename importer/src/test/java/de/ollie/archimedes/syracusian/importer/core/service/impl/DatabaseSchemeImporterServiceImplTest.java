package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.model.ColumnMDO;
import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.model.ForeignKeyMDO;
import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseConnectionFactory;
import de.ollie.archimedes.syracusian.importer.core.service.reader.ColumnReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.DatabaseSchemeReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.FkReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.PkReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.TableReaderService;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DatabaseSchemeImporterServiceImplTest {

	private static final String SCHEME_NAME = "PUBLIC";
	private static final String TABLE_NAME_A = "TABLE_A";
	private static final String TABLE_NAME_B = "TABLE_B";

	@Mock
	private ColumnMDO columnA;

	@Mock
	private ColumnMDO columnB;

	@Mock
	private ColumnReaderService columnReaderService;

	@Mock
	private Connection connection;

	@Mock
	private DatabaseConnectionFactory databaseConnectionFactory;

	@Mock
	private DatabaseSchemeReaderService databaseSchemeReaderService;

	@Mock
	private FkReaderService fkReaderService;

	@Mock
	private ForeignKeyMDO foreignKeyA;

	@Mock
	private ForeignKeyMDO foreignKeyB;

	@Mock
	private PkReaderService pkReaderService;

	@Mock
	private TableMDO tableA;

	@Mock
	private TableMDO tableB;

	@Mock
	private TableReaderService tableReaderService;

	@Mock
	private JDBCConnectionData connectionData;

	@InjectMocks
	private DatabaseSchemeImporterServiceImpl unitUnderTest;

	@Nested
	class TestsOfMethod_readDatabaseScheme_String_JDBCConnectionData {

		@Test
		void throwsAnException_passingANullValueAsSchemeName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.readDatabaseScheme(null, connectionData));
		}

		@Test
		void throwsAnException_passingANullValueAsJDBCConnection() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.readDatabaseScheme(SCHEME_NAME, null));
		}

		@Test
		void throwsAnException_whenSomethingWentWrongDuringDatabaseAccess() throws Exception {
			when(databaseConnectionFactory.create(connectionData)).thenThrow(new SQLException());
			assertThrows(ImportFailureException.class, () -> unitUnderTest.readDatabaseScheme(SCHEME_NAME, connectionData));
		}

		@Test
		void returnsDataschemeObject_withCorrectNameSet() throws Exception {
			// Prepare
			when(databaseConnectionFactory.create(connectionData)).thenReturn(connection);
			when(databaseSchemeReaderService.read(connection)).thenReturn(new DatabaseSchemeMDO(SCHEME_NAME, Set.of()));
			// Run
			DatabaseSchemeMDO returned = unitUnderTest.readDatabaseScheme(SCHEME_NAME, connectionData);
			// Check
			assertEquals(SCHEME_NAME, returned.getName());
		}

		@Test
		void returnsDataschemeObject_withTablesSet() throws Exception {
			// Prepare
			when(databaseConnectionFactory.create(connectionData)).thenReturn(connection);
			when(databaseSchemeReaderService.read(connection)).thenReturn(new DatabaseSchemeMDO(SCHEME_NAME, Set.of()));
			when(tableReaderService.read(SCHEME_NAME, connection)).thenReturn(Set.of(tableB, tableA));
			// Run
			DatabaseSchemeMDO returned = unitUnderTest.readDatabaseScheme(SCHEME_NAME, connectionData);
			// Check
			assertEquals(Set.of(tableA, tableB), returned.getTables());
		}

		@Test
		void returnsDataschemeObject_withColumnOfTablesSet() throws Exception {
			// Prepare
			when(databaseConnectionFactory.create(connectionData)).thenReturn(connection);
			when(databaseSchemeReaderService.read(connection)).thenReturn(new DatabaseSchemeMDO(SCHEME_NAME, Set.of()));
			when(tableReaderService.read(SCHEME_NAME, connection)).thenReturn(Set.of(tableB, tableA));
			when(tableA.getName()).thenReturn(TABLE_NAME_A);
			when(tableB.getName()).thenReturn(TABLE_NAME_B);
			when(columnReaderService.read(SCHEME_NAME, TABLE_NAME_A, connection)).thenReturn(Set.of(columnA));
			when(columnReaderService.read(SCHEME_NAME, TABLE_NAME_B, connection)).thenReturn(Set.of(columnB));
			// Run
			unitUnderTest.readDatabaseScheme(SCHEME_NAME, connectionData);
			// Check
			verify(tableA, times(1)).setColumns(Set.of(columnA));
			verify(tableB, times(1)).setColumns(Set.of(columnB));
		}

		@Test
		void returnsDataschemeObject_withForeignKeyOfTablesSet() throws Exception {
			// Prepare
			when(databaseConnectionFactory.create(connectionData)).thenReturn(connection);
			when(databaseSchemeReaderService.read(connection)).thenReturn(new DatabaseSchemeMDO(SCHEME_NAME, Set.of()));
			when(tableReaderService.read(SCHEME_NAME, connection)).thenReturn(Set.of(tableB, tableA));
			when(tableA.getName()).thenReturn(TABLE_NAME_A);
			when(tableB.getName()).thenReturn(TABLE_NAME_B);
			when(fkReaderService.read(SCHEME_NAME, TABLE_NAME_A, connection)).thenReturn(Set.of(foreignKeyA));
			when(fkReaderService.read(SCHEME_NAME, TABLE_NAME_B, connection)).thenReturn(Set.of(foreignKeyB));
			// Run
			unitUnderTest.readDatabaseScheme(SCHEME_NAME, connectionData);
			// Check
			verify(tableA, times(1)).setForeignKeys(Set.of(foreignKeyA));
			verify(tableB, times(1)).setForeignKeys(Set.of(foreignKeyB));
		}
	}
}
