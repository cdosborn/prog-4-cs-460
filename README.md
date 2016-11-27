# Installing the webapp
Create a symlink so the webapp's root is the folder 'root' in this repo
ln -snf /path/to/root/in/this/repo /path/to/webapps/ROOT

# Running the webapp
Run the following script to compile the classes, and restart tomcat.
Note: you have to edit the paths it uses to restart tomcat.
./restart.sh

# Working with tables
In order to dev, login to oracle like so:
sqlpl  cdosborn/a1211@oracle.aloe

The sql is organized into several files.

    > @create -- Creates all tables
    > @drop -- Deletes all tables
    > @insert -- Inserts all table data

# Create the entity model diagram
```
brew install graphviz
dot -Tpng doc/er-model.dot -o doc/er-model.png
```
