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
public class IndexMDO implements ColumnProvider {

	private String name;
	private Set<String> columnNames = new HashSet<>();

	public IndexMDO addColumn(String columnName) {
		columnNames.add(columnName);
		return this;
	}
}
