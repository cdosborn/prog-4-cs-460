## Installing the webapp
Create a symlink so the webapp's root is the folder 'root' in this repo
```
ln -snf /path/to/root/in/this/repo /path/to/webapps/ROOT
```

## Running the webapp
Run the following script to compile the classes, and restart tomcat.

Note: you have to edit the paths it uses to restart tomcat.
```
./restart.sh
```

## Working with tables
In order to dev, login to oracle like so:
```
sqlpl  cdosborn/a1211@oracle.aloe
```

The sql is organized into several files.

    > @create -- Creates all tables
    > @drop -- Deletes all tables
    > @insert -- Inserts all table data

## Making requests to the API
Note: check the url in each of the requests and adjust the port accordingly
### Get all /api/patients
```
curl "http://lectura.cs.arizona.edu:41211/api/patients/"
```
### Post to /api/patients
```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'fname=alfred&lname=alfonzo&dob=1967-2-15&patient#=5' "http://lectura.cs.arizona.edu:41211/api/patients/"
```
### Delete a patient
```
curl -X DELETE "http://lectura.cs.arizona.edu:41211/api/patients?patient%23=1"
```

## Create the entity model diagram
```
brew install graphviz
dot -Tpng doc/er-model.dot -o doc/er-model.png
```
