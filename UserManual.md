# A User's Manual For DataMakerRI

Written for DataMakerRI version 1.0.0 

as of 24 Aug 2020

by Clark Allen

<br/>

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

<br/>

#### Table Requirements
The following fields are required 
- **name** : a name that will appear in the insert statement
- **rows** : the number of rows to generate 
- **startRowNum** : the beginning value of an id field
- **fields** : an array of one or more fields in the table

<br/>

#### Field Requirements
All field definitions require a name and a dmSourceType.  Some 
dmSourceTypes require additional attributes, as you will see.

- **name** : a field name that will appear in the insert statement
- **dmSourceType** : the DataMaker source type identifies
what sort of data will be created (a city name, an integer, a date, etc...).
Note that dmSourceType is always a lower-case designation like "id" or "rtext".

<br/>

#### Generating The Various Field Types In A Table Request

**dmSourceType id**

This is an incrementing integer field.  The first value is the value of startRowNum in
the table definition and always increments by one with each new row.  The name of a 
field is always distinct from its dmSourceType.  In the example above I gave the key
a name of "ID" and a dmSourceType of "id".  Don't be confused by the name that 
resembles the dmSourceType; the field name is just a legal name in your database
definition.

<br/>

**dmSourceType uuid**

The uuid type requires no special handling.  Just give the field a name and a type
of uuid and let the api create UUIDs for each occurrence of this type.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "uuid"
        }
        ...

<br/>

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


<br/>

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

<br/>

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
    
<br/>

**dmSourceType lname**

Last names are not typically gender specific, so gender is not a required 
argument.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "lname"
        }
        ...

<br/>

**dmSourceType street**

Nothing special is required for this field.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "street"
        }
        ...

<br/>

**dmSourceType city**

Nothing special is required for this field.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "city"
        }
        ...

<br/>

**dmSourceType state**

Nothing special is required for this field.

    "fields": [
        {
            "name": "SOME_FIELD_NAME",
            "dmSourceType": "state"
        }
        ...

<br/>

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

<br/>

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

<br/>

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

<br/>

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

<br/>

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

<br/>

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

<br/>

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

<br/>

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

<br/>

#### Non-SQL Generating REST Endpoints

Other endpoints offered by the application provide JSON responses for
small data requests such as a randomly generated address, a string pattern,
a person's name, etc.  The calling convention for these endpoints is given
below.

**1. Generate a random address**

    GET  http://host:port/data/v1/alph/address?gender=R&namefmt=FIRST_LAST

Example response : 

    {
        "name": "Hope Walsh",
        "line1": "3331 View South ST NE BSMT",
        "city": "Culver",
        "state": "NM",
        "zip": "94640-8720"
    }
    
Parameters 
* gender : Gender can be one of R (a random gender is chosen), 
M (male), or F (female).
The gender becomes important in generating a name.
* namefmt : There are quite a few options
    * FIRST_MI_LAST : Bob C. Smith
    * FIRST_MIDDLE_LAST : Bob Colby Smith
    * FIRST_LAST : Bob Smith
    * FI_LAST : B. Smith
    * FI_MI_LAST : B. C. Smith
    * LAST_FIRST : Smith, Bob
    * LAST_FI : Smith, B.
    * LAST_FI_MI : Smith, B. C.
    * RANDOM_NAME_FORMAT : One of the formats given above chosen randomly.

<br/>

**2. Generate a random city name**

    GET  http://host:port/data/v1/alph/city

Example response : 

    {
        "name": "Sunriver"
    }

<br/>

**3. Generate a postal directional**

    GET  http://host:port/data/v1/alph/directional

Example response : 

    {
        "shortText": "NW",
        "longText": "Northwest"
    }

<br/>

**4. Generate a gender**

    GET  http://host:port/data/v1/alph/gender?type=R
    
Example response : 

    {
        "shortText": "M",
        "longText": "Male"
    }

Parameters
* type : Type can be one of R, M, or F.

<br/>

**5. Generate a random character**

    GET  http://host:port/data/v1/alph/char

Example response : 

    {
        "symbol": "b"
    }

<br/>

**6. Select a random character from a list**
    
    PUT  http://host:port/data/v1/alph/char

with this body : 

    ["q", "w", "e", "r", "t", "y"]

Example response : 

    {
        "symbol": "w"
    }

<br/>

**7. Generate a random money designation**

    GET  http://host:port/data/v1/alph/moneysym/rand

Example response : 

    {
        "symbol": "Rp",
        "abbr": "IDR",
        "descrip": "Indonesian Rupiah"
    }

<br/>

**8. Generate the name of a person**

    GET  http://host:port/data/v1/alph/person?gender=R&namefmt=FIRST_LAST

Example response : 

    {
        "gender": "F",
        "nameFmt": "FIRST_LAST",
        "name": "Kate Lopez",
        "first": "Kate",
        "middle": "",
        "last": "Lopez"
    }

Parameters
* gender : Gender can be one of R, M, or F.
* namefmt : There are quite a few options
    * FIRST_MI_LAST : Bob C. Smith
    * FIRST_MIDDLE_LAST : Bob Colby Smith
    * FIRST_LAST : Bob Smith
    * FI_LAST : B. Smith
    * FI_MI_LAST : B. C. Smith
    * LAST_FIRST : Smith, Bob
    * LAST_FI : Smith, B.
    * LAST_FI_MI : Smith, B. C.
    * RANDOM_NAME_FORMAT : One of the formats given above chosen randomly.

<br/>

**9. Generate a U. S. state designation**

    GET  http://host:port/data/v1/alph/state

Example response : 

    {
        "name": "Kentucky",
        "abbrev": "KY",
        "fipsCode": "21",
        "region": "South",
        "regionNumber": "3",
        "division": "East South Central",
        "divisionNumber": "6"
    }

<br/>

**10. Generate a street location**

    GET  http://host:port/data/v1/alph/street

Example response : 

    {
        "street": "NW Eric EXPY"
    }

<br/>

**11. Generate a UUID**

    GET  http://host:port/data/v1/alph/uuid
    
Example response : 

    {
        "value": "33da7d5d-14df-4664-b0e5-82123854f479"
    }

<br/>

**12. Select a text item from a list**

Sometimes you may need to limit or control the text items that are returned.  Maybe these items
pertain to some business problem for which there is no freely available api to create
the test data.  Here you can provide your own list of items to select from to suit your need.

    PUT  http://host:port/data/v1/alph/fromlist

with some string list in the body : 

    [
        "New York",
        "London",
        "Paris",
        "Munich",
        "Rome"
    ]

Example response : 

    {
        "value": "Rome"
    }

<br/>

**13. Generate some random English text with the specified length**

    GET  http://host:port/data/v1/alph/textoflength?length=35

Example response : 

    {
        "value": "its fee calmly learning contradict "
    }

<br/>

**14. Generate a postal street suffix**

    GET  http://host:port/data/v1/alph/uspssuffix
    
Example response :

     {
         "commonSuffix": "STR",
         "uspsStandardSuffix": "ST",
         "mixedCaseName": "Street"
     }

<br/>

**15. Generate a USPS secondary address unit**

    GET  http://host:port/data/v1/alph/uspsunit

Example response :

     {
         "shortText": "APT",
         "longText": "Apartment"
     }

<br/>

**16. Generate a random boolean with default representations**

There are different ways to represent truth, but this call is opinionated
and represents integer booleans (1 or 0), string booleans and primitives in the
way I usually see them.

    http://host:port/data/v1/bool/boolean

Example response : 

    {
        "intValue": 1,
        "truth": true,
        "stringValue": "True"
    }

<br/>

**17. Generate a boolean with control over its integer values for true and false**

    GET  http://host:port/data/v1/bool/intbool?trueValue=1&falseValue=-1

Example response : 

    {
        "intValue": -1,
        "truth": false,
        "stringValue": "False"
    }

<br/>

**18. Generate a boolean with control over its text values for true and false**

    GET  http://host:port/data/v1/bool/strbool?trueValue=Yes&falseValue=No

Example response : 

    {
        "intValue": 1,
        "truth": true,
        "stringValue": "Yes"
    }

<br/>

**19. Generate a random date in the specified calendar year**

    GET  http://localhost:8080/data/v1/date/year?year=1998

Example response : 

    {
        "iso8601": "1998-09-30T14:04:54.398-04:00",
        "milliseconds": 907178694398
    }

<br/>

**20. Generate a date in the specified month and year**

    GET  http://localhost:8080/data/v1/date/monthyear?month=5&year=1997

Example response : 

    {
        "iso8601": "1997-05-03T07:13:35.029-04:00",
        "milliseconds": 862658015029
    }

<br/>

**21. Generate a random date in the specified year range**

    GET  http://localhost:8080/data/v1/date/range?lowyear=1981&highyear=1989

Example response : 

    {
        "iso8601": "1988-05-15T16:58:37.408-04:00",
        "milliseconds": 579733117408
    }

<br/>

**22. Generate an alphanumeric string pattern**

Use this call to generate things like serial numbers, licence plate numbers, 
model numbers, etc... .  The pattern in the body contains character and numeric
literals and placeholders where a random character and a random number are to 
be inserted into the output.

    PUT  http://host:port/data/v1/num/pattern

with this body : 

    {
        "pattern": "SN: cnnnn-nccn-Tncnnn 99",
        "charSymbol": "c",
        "numSymbol": "n"
    }

in the pattern above, "SN: ", "-", "-T", and " 99" are literals that will
be carried over into the output without substitution.  All the c and n 
characters will be replaced with random character or numeric values.

Example response : 

    {
        "value": "SN: d0922-2wp1-T4b629 99"
    }

<br/>

**23. Generate a random double floating point value in the specified long integer range**

    GET  http://localhost:8080/data/v1/num/double/range?lowEnd=10000&highEnd=50000

Example response : 

    {
        "number": 12831.771756054339,
        "strNum": "12831.771756054339"
    }

<br/>

**24. Generate a floating point number in the given integer range**

    GET  http://localhost:8080/data/v1/num/float/range?lowEnd=1000&highEnd=5000

Example response : 

    {
        "number": 3301.22,
        "strNum": "3301.22"
    }

<br/>

**25. Generate an integer in the specified range**

    GET  http://localhost:8080/data/v1/num/int/range?lowEnd=1000&highEnd=5000

Example response : 

    {
        "number": 3175,
        "strNum": "3175"
    }

<br/>

**26. Select an integer from a list**

The input list is a string list containing integers.

    PUT  http://localhost:8080/data/v1/num/int/fromlist

with this body :

    ["1", "10", "100", "1000", "10000"]

Example response : 

    {
        "number": 100,
        "strNum": "100"
    }

<br/>

**27. Generate a long integer in the specified range**

    http://localhost:8080/data/v1/num/long/range?lowEnd=10000&highEnd=50000

Example response : 

    {
        "number": 21340,
        "strNum": "21340"
    }

<br/>

**28. Select a long integer from a list**

The input list is a string list of long integers.

    PUT  http://localhost:8080/data/v1/num/long/fromlist

with this body :

    ["1000", "10000", "100000", "1000000", "10000000"]

Example response : 

    {
        "number": 10000000,
        "strNum": "10000000"
    }



