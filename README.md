# DataMakerRI
### A RESTful API for generating randomized test data as json or as SQL

run with : mvn spring-boot:run 

or with  : mvn spring-boot:run  --debug

The Dockerized version of this application is on 
Docker Hub : https://hub.docker.com/repository/docker/clarkallen/datamakerri.

You can test the endpoints with the related project DataMakerIT 
which is also hosted on GitHub : https://github.com/ClarkAllen/DataMakerIT.

Some example calls to the RESTful API :

You can query for data as json or ask
for the data as a SQL insert statement.

To get an insert statement you can define
a table and fields like so : 

    PUT request
    http://localhost:8080/data/v1/sql/table
    
with this body : 

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

and the call returns something like this : 

    [
        "INSERT INTO MY_TABLE_NAME (ID,LAST_NAME,AGE)  ",
        "  VALUES (20,'Vega',84), ",
        "  VALUES (21,'Hall',27), ",
        "  VALUES (22,'Patton',118), ",
        "  VALUES (23,'Cook',85), ",
        "  VALUES (24,'White',51);"
    ]

<br />

RESTful endpoints exist for different data by type.
For example, to get a randomly generated address to a person :

    http://localhost:8080/data/v1/alph/address?gender=R&namefmt=FIRST_MIDDLE_LAST

Which returns something like the following :

    {
        "name": "Virginia Angelica Duncan",
        "line1": "1707 Autumn ST",
        "city": "Winthrop",
        "state": "GA",
        "zip": "20443-0413"
    }

<br />

You can get the pieces separately if you want : 

    http://localhost:8080/data/v1/alph/city
    
which returns a random city name : 

    {
        "name": "Hillsboro"
    }

<br />

Get a person's name :

    http://localhost:8080/data/v1/alph/person?gender=r&namefmt=LAST_FIRST
    
    {
        "gender": "F",
        "nameFmt": "LAST_FIRST",
        "name": "Brady, Megan",
        "first": "Megan",
        "middle": "",
        "last": "Brady"
    }

<br />

A pattern to be populated randomly (like serial numbers) : 

    PUT request
    http://localhost:8080/data/v1/num/pattern

with this body :
 
    {
        "pattern": "SN: cnnnn-nccn-Tncnnn 99",
        "charSymbol": "c",
        "numSymbol": "n"
    }

can get a response like this : 

    {
        "value": "SN: V6419-1VU9-T1i853 99"
    }

<br />

A date in some range of years : 

    http://localhost:8080/data/v1/date/range?lowyear=1998&highyear=2002
    
    {
        "iso8601": "2000-08-03T03:42:09.961Z",
        "milliseconds": 965274129961
    }

<br />

Get postal directionals :

    http://localhost:8080/data/v1/alph/directional
    
    {
        "shortText": "SE",
        "longText": "Southeast"
    }

<br />

A random gender : 

    http://localhost:8080/data/v1/alph/gender?type=R
    
    {
        "shortText": "M",
        "longText": "Male"
    }

<br />

A state in the US : 

    http://localhost:8080/data/v1/alph/state
    
    {
        "name": "South Carolina",
        "abbrev": "SC",
        "fipsCode": "45",
        "region": "South",
        "regionNumber": "3",
        "division": "South Atlantic",
        "divisionNumber": "5"
    }

<br />

A street :

    http://localhost:8080/data/v1/alph/street
    
    {
        "street": "SW Bagnal TER APT 765"
    }

<br />

A UUID :

    http://localhost:8080/data/v1/alph/uuid
    
    {
        "value": "1a25024a-abb7-4d12-91d0-5bcfd3d5ae1a"
    }

<br />

A boolean : 

    http://localhost:8080/data/v1/bool/boolean
    
    {
        "intValue": 0,
        "truth": false,
        "stringValue": "False"
    }

<br />

A double in some long integer range : 

    http://localhost:8080/data/v1/num/double/range?lowEnd=10000&highEnd=50000
    
    {
        "number": 23940.521701880156,
        "strNum": "23940.521701880156"
    }

<br />

And for long integers too : 

    http://localhost:8080/data/v1/num/long/range?lowEnd=10000&highEnd=50000
    
    {
        "number": 32425,
        "strNum": "32425"
    }

