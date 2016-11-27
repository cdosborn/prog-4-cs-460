# Working with tables
In order to dev, login to oracle like so:
sqlpl  cdosborn/a1211@oracle.aloe

The sql is organized into several files.
@create -- Creates all tables
@drop -- Deletes all tables
@insert -- Inserts all table data

# Create the entity model diagram
```
brew install graphviz
dot -Tpng doc/er-model.dot -o doc/er-model.png
```
