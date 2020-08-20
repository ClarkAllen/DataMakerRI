# User's Manual for DataMakerRI

DataMakerRI provides a RESTful interface for getting randomized
test data as an SQL insert statement or in commonly used
data transfer formats like json.  

To get an SQL insert statement you can submit a put request
with a body that contains the definition of a table and one
or more fields.  The Request looks similar to the example 
below : 

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

#### Table Requirements
- name : a name that will appear in the insert statement
- rows : the number of rows to generate 
- startRowNum : the beginning value of an id field
- fields : an array of one or more fields in the table

#### Field Requirements
- name : a field name that will appear in the insert
statement
- dmSourceType : the DataMaker source type that identifies
what sort of data will be created (a city name, an integer, a date, etc...)

[^1] Note that some data types have additional requirements for correct data generation.


