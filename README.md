Setup
-----

### Running the application

A basic spring boot application is already provided running on java 11 (mainclass: `com.afkl.travel.com.afkl.travel.exercise.Application`).

You can either run the application buy manually running the above mainclass or use gradle to run it by running the command './gradlew bootRun '

Note: make sure to have the java-11 setup correctly

The application then will start running on port 9090 and supported by swagger, so if you go to http://localhost:9090/swagger-ui/index.html it will show the swagger page for the application APIs.
These APIs require user authentication to be used. so please use the username='someuser' and password='psw'

To be able to check the Metrics you can have an overview by going to http://localhost:9090/actuator/metrics
These metrics also require user authentication, but it has a different credentials. please use username='ops' and passwor='psw'. it might require different browser session if already logged in with another user.

Metrics:

        name: "travel.locations.timer.total-count")
        description("Total number of requests processed")

        name: "travel.locations.timer.2xx-total-count"
        description("Total number of requests resulted in an OK response")

        name: "travel.locations.timer.4xx-total-count"
        description("Total number of requests resulted in a 4xx response")

        name: "travel.locations.timer.5xx-total-count")
        description("Total number of requests resulted in a 5xx response")

        name: "travel.locations.timer.max")
        description("Max response time of all requests")

        name: "travel.locations.timer.average")
        description("Average response time of all requests")

