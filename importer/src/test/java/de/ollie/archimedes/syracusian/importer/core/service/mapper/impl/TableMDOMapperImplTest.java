package de.ollie.archimedes.syracusian.importer.core.service.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.factory.UUIDFactory;
import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.model.Table;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TableMDOMapperImplTest {

	private static final String NAME = "name";
	private static final UUID UID = UUID.randomUUID();

	@Mock
	private TableMDO mdo;

	@Mock
	private UUIDFactory uuidFactory;

	@InjectMocks
	private TableMDOMapperImpl unitUnderTest;

	@Nested
	class TestsOfTheMethod_toModel_TableMDO {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.toModel(null));
		}

		@Test
		void returnsTableWithTheCorrectId_passingAnMDO() {
			// Prepare
			when(uuidFactory.create()).thenReturn(UID);
			// Run
			Table returned = unitUnderTest.toModel(mdo);
			// Check
			assertEquals(UID, returned.getId());
		}

		@Test
		void returnsTableWithTheCorrectName_passingAnMDOWithName() {
			// Prepare
			when(mdo.getName()).thenReturn(NAME);
			// Run
			Table returned = unitUnderTest.toModel(mdo);
			// Check
			assertEquals(NAME, returned.getName());
		}
	}
}
