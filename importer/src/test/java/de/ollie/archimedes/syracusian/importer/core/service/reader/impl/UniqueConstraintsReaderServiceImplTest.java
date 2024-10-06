package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.model.UniqueConstraintMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.UniqueConstraintsAccessor;
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
public class UniqueConstraintsReaderServiceImplTest {

	private static final String SCHEME_NAME = "scheme-name";

	@Mock
	private Connection connection;

	@Mock
	private DatabaseTypeService databaseTypeService;

	@Mock
	private TableMDO table;

	@Mock
	private UniqueConstraintsAccessor accessor0;

	@Mock
	private UniqueConstraintsAccessor accessor1;

	@Mock
	private UniqueConstraintMDO uniqueConstraint0;

	@Mock
	private List<UniqueConstraintsAccessor> accessors;

	@InjectMocks
	private UniqueConstraintsReaderServiceImpl unitUnderTest;

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
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.read(SCHEME_NAME, table, null));
		}

		@Test
		void throwsAnException_passingSchemeNameAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.read(null, table, connection));
		}

		@Test
		void throwsAnException_passingTableNameAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.read(SCHEME_NAME, null, connection));
		}

		@Test
		void returnsASetWithCorrectForeignKey_whenAnAccessorIsDefinedForDatabaseTypeUNSPECIFIEDOnly() {
			// Prepare
			Set<UniqueConstraintMDO> expected = Set.of(uniqueConstraint0);
			when(accessor0.getDatabaseType()).thenReturn(DatabaseType.UNSPECIFIED);
			when(accessor0.getUniqueConstraints(SCHEME_NAME, table, connection)).thenReturn(expected);
			when(accessors.stream()).thenReturn(Stream.of(accessor0));
			when(databaseTypeService.getDatabaseType(connection)).thenReturn(DatabaseType.HSQL);
			unitUnderTest.postConstruct();
			// Run
			Set<UniqueConstraintMDO> returned = unitUnderTest.read(SCHEME_NAME, table, connection);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsASetWithCorrectForeignKey_whenAnAccessorIsDefinedForSpecifiedDatabaseType() {
			// Prepare
			Set<UniqueConstraintMDO> expected = Set.of(uniqueConstraint0);
			when(accessor0.getDatabaseType()).thenReturn(DatabaseType.UNSPECIFIED);
			when(accessor1.getDatabaseType()).thenReturn(DatabaseType.HSQL);
			when(accessor1.getUniqueConstraints(SCHEME_NAME, table, connection)).thenReturn(expected);
			when(accessors.stream()).thenReturn(Stream.of(accessor0, accessor1));
			when(databaseTypeService.getDatabaseType(connection)).thenReturn(DatabaseType.HSQL);
			unitUnderTest.postConstruct();
			// Run
			Set<UniqueConstraintMDO> returned = unitUnderTest.read(SCHEME_NAME, table, connection);
			// Check
			assertEquals(expected, returned);
		}
	}
}
