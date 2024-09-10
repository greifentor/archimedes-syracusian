package de.ollie.archimedes.syracusian.importer.core.model;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@Data
@Generated
public class DatabaseSchemeMDO {

	private String name;
	private Set<TableMDO> tables = Set.of();

	public List<String> getTableNames() {
		return tables.stream().map(t -> t.getName()).sorted().toList();
	}
}
