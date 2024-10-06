package de.ollie.archimedes.syracusian.importer.core.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Generated
public class PrimaryKeyMDO {

	private String name;
	private Set<String> memberColumnNames = new HashSet<>();

	public PrimaryKeyMDO addMemberColumnName(String columnName) {
		memberColumnNames.add(columnName);
		return this;
	}
}
