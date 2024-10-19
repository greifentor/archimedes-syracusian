package de.ollie.archimedes.syracusian.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@Data
@Generated
public class Datascheme {

	private UUID id;
	private String name;
}
