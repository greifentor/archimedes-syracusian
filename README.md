# Archimedes Syracusian Edition

## Abstract

Archimedes is a tool for relational database development. It allows to create database
schemes finally with a GUI, import database schemes from existing databases and create 
update scripts for bringing changes made in the application to the database.

Additionally the will be a hook to bind code generators for boiler plate code generation
based on the database scheme.

## Naming

As Syracus was last city were the real Archimedes lived, I took that name as this
version of the Archimedes application should be the final one.

## History

Archimedes as provided also in my on my Github account, has been created in 2004. Over
the years the application got new functionalities and lost unused ones. More than once 
I tried to re-factor the software and bring it to higher level of quality in code, 
stability and usage, but failed by different reasons. The Syracusian should be another
approach to get a more modern and more modular version of the application. 

## Planned Features

* Import database schemes from existing databases.
* Create new and modify existing database schemes.
* Create update scripts to update an existing database scheme with the database scheme as represented in the application.
* Allow to link specific code generators to the application, which are able to create code based on the database scheme as shown in the application.

## Frame Conditions

* Test driven development.
* Compliance with SOLID.
* Open interfaces to allow users to link own code generators, database scheme importers and update script factories.

## Architecture

For details look here: [architecture documentation](docs/architecture/architecture.md)