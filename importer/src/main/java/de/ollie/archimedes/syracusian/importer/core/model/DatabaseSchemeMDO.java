package de.ollie.archimedes.syracusian.importer.core.model;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import java.util.List;
import java.util.Optional;
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

	public Optional<TableMDO> findTableByName(String tableName) {
		ensure(tableName != null, "table name cannot be null!");
		return tables.stream().filter(table -> tableName.equals(table.getName())).findFirst();
	}

	public List<String> getTableNames() {
		return tables.stream().map(t -> t.getName()).sorted().toList();
	}
}
