[![CircleCI](https://circleci.com/bb/projectspartan/rest-service/tree/staging.svg?style=svg)](https://circleci.com/bb/projectspartan/rest-service/tree/staging)
# School Management RESTful Service
This repository contains an application using JAX-RS to build RESTful service. The JAX-RS implementation used for this project is `Jersey`.

## Running
```bash
> mvnw clean compile exec:java
> curl http://localhost:8080/v1/students
```

## Deployment to Heroku
[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/juliuskrah/school-management-api)

-- Other pipeline options