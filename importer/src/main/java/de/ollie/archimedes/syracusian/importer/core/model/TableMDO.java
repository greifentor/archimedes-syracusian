package de.ollie.archimedes.syracusian.importer.core.model;

import java.util.HashSet;
import java.util.List;
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

	public List<String> getColumnNames() {
		return toNamesList(columns.stream());
	}

	private List<String> toNamesList(Stream<ColumnMDO> stream) {
		return stream.map(ColumnMDO::getName).sorted().toList();
	}

	public List<String> getNotNullColumnNames() {
		return toNamesList(columns.stream().filter(c -> !c.isNullable()));
	}
}
