# A User's Manual For DataMakerRI

DataMakerRI provides a RESTful interface for getting randomized
test data as an SQL insert statement or in json.  

To get an SQL insert statement you can submit a PUT request
with a body that contains the definition of a table and one
or more fields.  The Request looks similar to the example 
below : 

    PUT   http://localhost:8080/data/v1/sql/table
    
    {
        "name": "MY_TABLE_NAME",
        "rows": 5,
        "startRowNum": 20,
        "fields": [
            {
                "name": "ID",
                "dmSourceType": "id"
            },
            {
                "name": "LAST_NAME",
                "dmSourceType": "lname"
            },
            {
                "name": "AGE",
                "dmSourceType": "long",
                "rangeLowEnd": 18,
                "rangeHighEnd": 120
            }
        ]
    }

and the service responds with an SQL insert statement :

    [
        "INSERT INTO MY_TABLE_NAME (ID,LAST_NAME,AGE)  ",
        "  VALUES (20,'Vega',84), ",
        "  VALUES (21,'Hall',27), ",
        "  VALUES (22,'Patton',118), ",
        "  VALUES (23,'Cook',85), ",
        "  VALUES (24,'White',51);"
    ]

<br />

#### Table Requirements
The following fields are required 
- **name** : a name that will appear in the insert statement
- **rows** : the number of rows to generate 
- **startRowNum** : the beginning value of an id field
- **fields** : an array of one or more fields in the table

<br />

#### Field Requirements
All field definitions require a name and a dmSourceType.  Some 
dmSourceTypes require additional attributes, as you will see.

- **name** : a field name that will appear in the insert statement
- **dmSourceType** : the DataMaker source type identifies
what sort of data will be created (a city name, an integer, a date, etc...).
Note that dmSourceType is always a lower-case designation like "id" or "rtext".

<br />

#### Generating The Various Field Types In A Table Request

**dmSourceType id**
This is an incrementing integer field.  The first value is the value of startRowNum in
the table definition and always increments by one with each new row.  The name of a 
field is always distinct from its dmSourceType.  In the example above I gave the key
a name of "ID" and a dmSourceType of "id".  Don't be confused by the name that 
resembles the the dmSourceType; the field name is just a legal name in your database
definition.

**dmSourceType uuid**
The uuid type requires no special handling.  Just give the field a name and a type
of uuid and let the api create UUIDs for each occurrence of this type.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "uuid"
        }
        ...

**dmSourceType rtext**
rtext produces randomized English language text with the specified length 
greater than zero.

    "fields": [
            {
                "name": "SOME_FIELD_NAME",
                "dmSourceType": "rtext",
                "length": 25
            }
            ...


**dmSourceType textfromlist**
Sometimes you may want to have control over the items selected from 
for the return value.  Some examples might be dog breeds, product names,
company names, etc... .  This source type allows you select randomly 
from a list that you supply.  The list must have two or more elements.

**Important** : There is a fromLists element in the fields definition that maps a
field name to the list the field is populated from.  The list name matches 
the field name that it populates.  This structure allows multiple fields to be 
populated from lists that you control.

In the example below, this field is populated from a list with the same name :

    "fields": {
        ... ,
        "name": "SOME_FIELD_NAME",
        "dmSourceType": "textfromlist",
        "fromLists": {
            "SOME_FIELD_NAME": [
                "Hammer",
                "Screwdriver",
                "Drill",
                "Saw"
            ]
        },
        ...
    }

**dmSourceType **

**dmSourceType **

**dmSourceType **

**dmSourceType **

**dmSourceType **

**dmSourceType **

**dmSourceType **

**dmSourceType **

**dmSourceType **

**dmSourceType **

**dmSourceType **

**dmSourceType **

**dmSourceType **

**dmSourceType **

**dmSourceType **


