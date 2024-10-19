package de.ollie.archimedes.syracusian.importer.core.factory.impl;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UUIDFactoryImplTest {

	@InjectMocks
	private UUIDFactoryImpl unitUnderTest;

	@Nested
	class TestsOfMethod_create {

		@Test
		void returnsAnUUID() {
			assertNotNull(unitUnderTest.create());
		}

		@Test
		void returnsNotSameUUIDs_callingMethodTwice() {
			assertNotSame(unitUnderTest.create(), unitUnderTest.create());
		}

		@Test
		void returnsDifferentUUIDs_callingMethodTwice() {
			assertNotEquals(unitUnderTest.create(), unitUnderTest.create());
		}
	}
}
