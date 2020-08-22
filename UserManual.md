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
resembles the dmSourceType; the field name is just a legal name in your database
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
company names, etc... .  This dmSourceType allows you select randomly 
from a list that you supply.  The list must have two or more string elements.

Here is how it looks in the field definition :

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "textfromlist",
            "itemList": [
                "Hammer",
                "Screwdriver",
                "Drill",
                "Saw"
            ]
        }
        ...

**dmSourceType fname**
To select the first name of a person you will need to specify a gender.
The gender specification must be one of R (random), M (male), or F (female).

    "fields": [
            {
                "name": "SOME_FIELD_NAME",
                "dmSourceType": "fname",
                "gender": "R"
            }
            ...
    
**dmSourceType lname**
Last names are not typically gender specific, so gender is not a required 
argument.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "lname"
        }
        ...

**dmSourceType street**
Nothing special is required for this field.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "street"
        }
        ...

**dmSourceType city**
Nothing special is required for this field.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "city"
        }
        ...

**dmSourceType state**
Nothing special is required for this field.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "state"
        }
        ...

**dmSourceType bool**
The return value might be handled in three different ways; as an integer,
as a string, or as a boolean.  For that reason you need to specify the 
database type so the data are returned in a useful way.  The database type
argument must be one of int (returns 1 or 0), boolean (returns true or false), 
or string (returns 'True' or 'False').

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "bool",
            "databaseType": "int"
        }
        ...

**dmSourceType datemonyr**
To get a random date in some month and year you must specify the month
and year that you want to constrain the selection by.  Both month and
year will be numeric arguments.  The month is a number from one to twelve.
The year is an argument from 1 to 4,000.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "datemonyr",
            "month": 7,
            "year": 2018
        }
        ...

**dmSourceType dateinyr**
Specify a numeric year between 1 and 4000 to generate the random date 
value in that year.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "dateinyr",
            "year": 2019
        }
        ...

**dmSourceType datebetwyrs**
To generate a random year between two years (including the boundary years),
specify yearLowEnd and yearHighEnd values as numbers between 1 and 4000.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "datebetwyrs",
            "yearLowEnd": 2005,
            "yearHighEnd": 2015
        }
        ...

**dmSourceType pattern**
The pattern source type allows the user to generate a string that follows 
a pattern.  The pattern can contain constants, variable character places,
and variable numeric places.  Serial numbers often look this way.
A serial number might begin with "SN : ", have a mix of 4 character or 
numeric values, a dash and a mix of 8 more character and numeric values : 

'SN : K589-7a295mZ6'.

You must pass a pattern string and two characters that will stand in as 
variable places in your pattern.  See the example below and the explanation 
that follows.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "pattern",
            "pattern": "SN : K589-nannnaan",
            "charSymbol": "a",
            "numSymbol": "n"
        }
        ...

- The characters in the "SN : K589-" portion of the string are rendered 
as constants.
- All the "a" characters in the pattern are replaced with a random character
because our charSymbol specified "a" as a pattern symbol.
- All the "n" characters in the pattern are replaced with a random integer
because our numSymbol specified "n" as a pattern symbol.

**dmSourceType double**
To get a random decimal number you need to specify the long integer range that 
will constrain the number, and the number of decimal places to show; 
the precision.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "double",
            "rangeLowEnd": 50,
            "rangeHighEnd": 350,
            "precision": 3
        }
        ...

**dmSourceType long**
To get a long integer you need to specify the upper and lower limits.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "long",
            "rangeLowEnd": -300,
            "rangeHighEnd": 300
        }
        ...

**dmSourceType longfromlist**
If you want to control the long integers discretely, you can provide a 
string list of the numbers that will be selected from.  Of course, the 
numbers have to successfully parse to long integers for this to work.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "longfromlist",
            "itemList": [
                "0",
                "10",
                "100",
                "1000",
                "10000"
            ]
        }
        ...

<br />

#### Non-SQL Generating REST Endpoints

Other endpoints offered by the application provide JSON responses for
small data requests such as a randomly generated address, a string pattern,
a person's name, etc.  The calling convention for these endpoints is given
below.


