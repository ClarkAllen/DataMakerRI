# A User's Manual For DataMakerRI

DataMakerRI provides a RESTful interface for getting randomized
test data as an SQL insert statement or in commonly used
data transfer formats like json.  

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

[^1] Note that some data types have additional requirements for correct data generation.

<br />

#### Generating The Various Field Types In A Table Request

**dmSourceType id**
This is an incrementing integer field.  The first value is the value of startRowNum in
the table definition and always increments by one with each new row.  The name of a 
field is always distinct from its dmSourceType.  In the example above I gave the key
a name of "ID" and a dmSourceType of "id".  I could have named the key any legal database
field name and its dmSourceType would still be "id".

**dmSourceType uuid**
The uuid type requires no special handling.  Just give the field a name and a type
of uuid and let the api create UUIDs for each occurrence of this type.

    {
        "name": "USER_UUID",
        "dmSourceType": "uuid"
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

**dmSourceType **

**dmSourceType **


