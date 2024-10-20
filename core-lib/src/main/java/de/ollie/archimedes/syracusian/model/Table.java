package de.ollie.archimedes.syracusian.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class Table {

	private UUID id;
	private String name;
	private List<Column> columns = new ArrayList<>();
	private List<ForeignKey> foreignKeys = new ArrayList<>();
	private List<Index> indices = new ArrayList<>();
	private List<PrimaryKey> primaryKeys = new ArrayList<>();
	private List<UniqueConstraint> uniqueConstraints = new ArrayList<>();
}
