
# Locawork

Location based job finding and offering solution.


## External references

- [Endpoint server location](https://locawork-web-api.herokuapp.com/)

## How to log server

Download [Heroku CLI](https://locawork-web-api.herokuapp.com/)

Login to your account

```
heroku login
```
View server logs
```
heroku logs --tail -a locawork-web-api
```

View database
```
heroku pg:psql postgresql-curved-17907 --app locawork-web-api
```

## The format

The backend is secured using Jason Web Token.
The token validity is defined in application configuration file programmatically.

The data structure is application/json

Must give token with header, to maintain the user validity in the session.




## Adding database extensions

```
CREATE EXTENSION earthdistance;
CREATE EXTENSION cube;
CREATE EXTENSION pgcrypto;
```


## Update database with code based approach(inital stage)
### Change application.properties
```
spring.jpa.hibernate.ddl-auto=create
```
## Or create with Flyway(the application has live data)
```
# spring.flyway.clean-disabled=true
spring.flyway.clean-on-validation-error=false
spring.flyway.location=classpath:db/migration
spring.flyway.table=schema_history
```
# Endpoints
###Getting all jobs
```https://locawork-web-api.herokuapp.com/jobs/```
### Updating job
Updating job with id 4<br>
``` https://locawork-web-api.herokuapp.com/jobs/4```
```
{
	"id": 4,
	"title": "Muru niitmine",
	"description" : "Tule niida aias muru. Kokku on 100 ruutmeetrit.",
	"salary" : 100,
	"longitude": "58.698017",
	"latitude": "-152.522067"
}
```

### Deleting job
Deleting job with id 4 <br>
``` https://futumap.herokuapp.com/jobs/4```
###Gettin jobs by location in a distance
Gettin jobs by location in a distance. Distance is in METERS! Add your longitude and latitude after '='.
Example with Longitude 152.522067 and latitude 58.698017<br>
googleAccountId is currently logged in user to filter out account from finding job.

```https://locawork.herokuapp.com/jobs/getjobsbylocation?longitude=-152.522067&latitude=58.698017&distance=10000000&googleAccountId=108921010361347349816```