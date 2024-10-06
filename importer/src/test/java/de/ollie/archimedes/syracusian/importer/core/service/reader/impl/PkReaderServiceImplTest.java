package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.model.ColumnMDO;
import de.ollie.archimedes.syracusian.importer.core.model.PrimaryKeyMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.PkAccessor;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import java.sql.Connection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PkReaderServiceImplTest {

	private static final String COLUMN_NAME = "column-name";
	private static final String SCHEME_NAME = "scheme-name";
	private static final String TABLE_NAME = "table-name";

	@Mock
	private ColumnMDO column;

	@Mock
	private PkAccessor accessor0;

	@Mock
	private PkAccessor accessor1;

	@Mock
	private Connection connection;

	@Mock
	private DatabaseTypeService databaseTypeService;

	@Mock
	private List<PkAccessor> accessors;

	@InjectMocks
	private PkReaderServiceImpl unitUnderTest;

	@Nested
	class TestsOfMethod_postConstruct {

		@Test
		void throwsAnException_whenNoAccessorIsDefined() {
			assertThrows(IllegalStateException.class, () -> unitUnderTest.postConstruct());
		}

		@Test
		void throwsAnException_whenNoAccessorIsDefinedForDatabaseTypeUNSPECIFIED() {
			// Prepare
			when(accessor0.getDatabaseType()).thenReturn(DatabaseType.HSQL);
			when(accessors.stream()).thenReturn(Stream.of(accessor0));
			// Run & Check
			assertThrows(IllegalStateException.class, () -> unitUnderTest.postConstruct());
		}
	}

	@Nested
	class TestsOfMethod_read_String_String_Connection {

		@Test
		void throwsAnException_passingConnectionAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.read(SCHEME_NAME, TABLE_NAME, null));
		}

		@Test
		void throwsAnException_passingSchemeNameAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.read(null, TABLE_NAME, connection));
		}

		@Test
		void throwsAnException_passingTableNameAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.read(SCHEME_NAME, null, connection));
		}

		@Test
		void returnsASetWithTheCorrectColumnNames_whenAnAccessorIsDefinedForDatabaseTypeUNSPECIFIEDOnly() {
			// Prepare
			PrimaryKeyMDO expected = new PrimaryKeyMDO().setMemberColumnNames(Set.of(COLUMN_NAME));
			when(accessor0.getDatabaseType()).thenReturn(DatabaseType.UNSPECIFIED);
			when(accessor0.getPk(SCHEME_NAME, TABLE_NAME, connection)).thenReturn(expected);
			when(accessors.stream()).thenReturn(Stream.of(accessor0));
			when(databaseTypeService.getDatabaseType(connection)).thenReturn(DatabaseType.HSQL);
			unitUnderTest.postConstruct();
			// Run
			PrimaryKeyMDO returned = unitUnderTest.read(SCHEME_NAME, TABLE_NAME, connection);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsASetWithTheCorrectColumnNames_whenAnAccessorIsDefinedForSpecifiedDatabaseType() {
			// Prepare
			PrimaryKeyMDO expected = new PrimaryKeyMDO().setMemberColumnNames(Set.of(COLUMN_NAME));
			when(accessor0.getDatabaseType()).thenReturn(DatabaseType.UNSPECIFIED);
			when(accessor1.getDatabaseType()).thenReturn(DatabaseType.HSQL);
			when(accessor1.getPk(SCHEME_NAME, TABLE_NAME, connection)).thenReturn(expected);
			when(accessors.stream()).thenReturn(Stream.of(accessor0, accessor1));
			when(databaseTypeService.getDatabaseType(connection)).thenReturn(DatabaseType.HSQL);
			unitUnderTest.postConstruct();
			// Run
			PrimaryKeyMDO returned = unitUnderTest.read(SCHEME_NAME, TABLE_NAME, connection);
			// Check
			assertEquals(expected, returned);
		}
	}
}
