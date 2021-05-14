# AirCheck

This application, AirCheck, is a place where a person can easily find out about the current air quality of a location, it also provides an API for users that might want to use it in their applications/services.


For more details please read the project_report.pdf, also there is a video demo of the application available here:
https://drive.google.com/file/d/1onYrT5VadueGoAsSPyv59svgAKUQnh0t/view?usp=sharing

## How to Run Locally

### Frontend

`cd frontend-react`

`npm i`

`npm start`

Deployed on: http://localhost:3000/

### API

`cd backend-spring-boot/air-quality`

`mvn install`

`mvn org.springframework.boot:spring-boot-maven-plugin:run`

Available at: http://localhost:8080/api/v1

API Documentation: http://localhost:8080/swagger-ui.html
