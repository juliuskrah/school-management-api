[![CircleCI](https://circleci.com/bb/projectspartan/rest-service/tree/dev.svg?style=svg)](https://circleci.com/bb/projectspartan/rest-service/tree/dev)
# School Management RESTful Service
This repository contains an application using JAX-RS to build RESTful service. The JAX-RS implementation used for this project is `Jersey`.

## Quick Start
Run the following commands
```bash
> mvnw clean package
> java -jar target\school-management-swarm.jar -Sdev
```

Open another terminal
```bash
> curl -i -X GET -H "Accept: application/json" http://localhost:9997/api/v1/students
```

## Deployment to Heroku
[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/juliuskrah/school-management-api)

-- Other pipeline options