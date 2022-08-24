Prerequisites:
-install Postman
-install MySQL Workbench
-run DBScript.sql in MySql Workbench
-in src\main\resources\application.properties, please update the following properties:
*spring.datasource.username (application won't start otherwise)
*spring.datasource.password (application won't start otherwise)
*spring.mail.host
*spring.mail.port
*spring.mail.username
*spring.mail.password

This project simulates the backend of a website used by a gym.
The customer of the gym can:
-see what activities can be done in this gym (fitness, swiming, yoga, etc.)
-see how those activities ar organized in bundles (gold bundle, silver bundle, bronze bundle etc.). An activity can be part of a bundle until a specific date.
-make a reservation for a specific activity.

The website also allows uploading a CSV file that contains:
-the date;
-the hour;
-how many reservations can be made for a scheduled activity in a week.
If we try to update the schedule for the uploaded week:
-all the reservations affected by the update are going to be deleted (if the date, the hour or the activity was changed);
-if the date, the hour and the activity of a reservation was not affected by the upload, the reservation will remain in the database.


Test Case 1 - Create an activity
url POST: http://localhost:8080/api/activities
files: 
-Activity POST 1.json
-Activity POST 2.json

Test Case 2 - Retrieve an activity
url GET: http://localhost:8080/api/activities/{ActivityId}
file: Activity GET.json
-{ActivityId} represents activity.id from the db

Test Case 3 - Retrieve all activities
url GET: http://localhost:8080/api/activities


Test Case 4 - Update an activity
url PUT: http://localhost:8080/api/activities
file: Activity PUT.json
-check the database to to see if activity.name was changes
-send a new GET request for the updated entity

Test Case 5 - Delete an activity
url DELETE: http://localhost:8080/api/activities/{ActivityId}
-check if the activity is still in the database

Test Case 6 - Create a bundle
url POST: http://localhost:8080/api/bundles
files: 
-Bundle POST 1.json
-Bundle POST 2.json

Test Case 7 - Retrieve a bundle
url GET: http://localhost:8080/api/bundles/{bundleId}
file: Bundle GET.json
-{bundleId} represents bundle.id from the db
-all activities in the database will appear with "containedByBundle": false. Reason for showing: the admin will check what activities should be part of the bundle.

Test Case 8 - Retrieve all bundles
url GET: http://localhost:8080/api/bundles


Test Case 9 - Update a bundle
url PUT: http://localhost:8080/api/bundles
file: Bundle PUT.json
-check the database to to see if bundle.name was changed
-send a new GET request for the updated bundle

Test Case 10 - Delete a bundle
url DELETE: http://localhost:8080/api/bundles/{bundleId}
-check if the bundle is still in the database

Test Case 11 - Add activity to a bundle
url PUT: http://localhost:8080/api/bundles
file: Bundle add Activity PUT.json
-modify the following fields: "containedByBundle": true, "validUntil": "2022-12-25";
-check the database to to see if a new row was inserted in bundle_activity table;
-send a new GET request for the updated bundle (if the activities have "containedByBundle": true, "validUntil": "2022-12-25")

Test case 12 - Create a user:
url POST: http://localhost:8080/api/users
file: User 1 POST.json
-if you modify "email" with an actual address, an email will be sent;
-if you click the link in the email, the user will be enabled (in the db, user.unable will get 1)

Test case 13 - Create a user with the same username:
url POST: http://localhost:8080/api/users
file: User 2 with same username PUT.json
-db will not allow duplicated values for user.username. Application will reply with error message: Username newUser already exists

Test case 14 - Retrieve all users:
url GET: http://localhost:8080/api/users

Test case 15 - Retrieve specific user:
url GET: http://localhost:8080/api/users/{userId}
-{userId} represents user.id from the db

Test case 16 - Assign a bundle to a user
url PUT: http://localhost:8080/api/users/{userId}/addBundle/{bundleId}
-request should not contain a payload
-send a new GET request for the user you just added the bundle. Check if the bundle appears "bundles"
-check the bundle_user table in the database to see if a new row was inserted

Test case 17 - Assign the same bundle to a user
url PUT: http://localhost:8080/api/users/{userId}/addBundle/{bundleId}
-use the same user and bundle as before;
-the application will reply with the following message: "User newUser already contains bundle Gold"

Test case 18 - Upload the schedule for a week
url POST: http://localhost:8080/api/week
file containing the payload: week1 POST.json
file containng the csv: week1.csv (date format: yyyy-mm-dd)
-check in the database if data was inserted in "week" an "schedule" tables 

Test case 19 - Retrieve a week 
url GET: http://localhost:8080/api/week/{year}/{week}
file: week1 GET.json

Test case 20 - Create a reservation
url POST: http://localhost:8080/api/reservations
files: Reservation1 POST.json, Reservation2 POST.json
-check if data was inserted in the reservation table;
-check if schedule.available_reservations column was modified
-send a GET request to http://localhost:8080/api/Schedules and check if the result is similar with Schedules GET

Test case 21 - Update the schedule for a week where reservations exists
url POST: http://localhost:8080/api/week
file containing the payload: week2 POST.json
file containng the csv: week2.csv (date format: yyyy-mm-dd)
-the schedule for reservation 2 was modified (hour changed from 17:00 to 17:30)
-check if the reservation for 2022-01-04,17:30 still exists in the db (it should not)
-check if the reservation for 2022-01-03,16:00 still exists in the db (it should)
-check if schedule.available_reservations reflects the number of reservations in the reservation table
