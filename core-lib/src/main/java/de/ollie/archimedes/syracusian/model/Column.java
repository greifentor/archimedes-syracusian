package de.ollie.archimedes.syracusian.model;

import java.util.UUID;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class Column {

	private UUID id;
	private String name;
}
