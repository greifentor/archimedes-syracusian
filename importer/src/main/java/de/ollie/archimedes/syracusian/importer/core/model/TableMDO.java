package de.ollie.archimedes.syracusian.importer.core.model;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Generated
public class TableMDO {

	private String name;
	private Set<ColumnMDO> columns = new HashSet<>();
	private Set<ForeignKeyDBO> foreignKeys = new HashSet<>();

	public Optional<ColumnMDO> findColumnByName(String columnName) {
		ensure(columnName != null, "column name cannot be null!");
		return columns.stream().filter(c -> columnName.equals(c.getName())).findFirst();
	}

	public List<String> getColumnNames() {
		return toNamesList(columns.stream());
	}

	private List<String> toNamesList(Stream<ColumnMDO> stream) {
		return stream.map(ColumnMDO::getName).sorted().toList();
	}

	public List<String> getNotNullColumnNames() {
		return toNamesList(columns.stream().filter(c -> !c.isNullable()));
	}

	public List<String> getPKColumnNames() {
		return toNamesList(columns.stream().filter(c -> c.isPkMember()));
	}

	public TableMDO setPksByColumnNames(Set<String> pkColumnNames) {
		ensure(pkColumnNames != null, "pk column names cannot be null!");
		pkColumnNames.forEach(cn -> findColumnByName(cn).ifPresent(c -> c.setPkMember(true)));
		return this;
	}
}
