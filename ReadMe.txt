## Starting the webapp
Run the following script to compile the classes, and restart tomcat.

Note: you have to edit the paths it uses to restart tomcat and the paths to
the java files.
```
./restart.sh
```

## Working with tables
In order to dev, login to oracle like so:
```
sqlpl  cdosborn/a1211@oracle.aloe
```

All the db data is organized into several sql files.

    > @create -- Create all tables
    > @drop -- Delete all tables
    > @insert -- Insert all table data
    > @reset -- Run all prior scripts to rebuild db


## Create the entity model diagram
```
brew install graphviz
dot -Tpng doc/er-model.dot -o doc/er-model.png
```

## Contributions
Connor Osborn <cdosborn@email.arizona.edu>

- Worked on db schema
- Worked on er-diagram
- Worked on db-normalization
- Designed web app layout
- Implemented views (patients, charges)
- Implemented 5 queries

Margarita Norzagary <mnorzagaray@email.arizona.edu>

- Worked on db schema
- Worked on er-diagram
- Implemented views (appointments, visits, services)
- Populated database with values
- Added documentation for all classes
