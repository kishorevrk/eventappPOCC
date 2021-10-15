# Event Reg
**Event Reg** is an Android app that helps its users to register for any upcoming event. Users can keep track of the events registered and not registered. A Custom built backend API is hosted in a Cloud Server called `Heroku` run by `Salesforce`.

## Screenshots
![EventReg Screenshots](screenshots/event_reg_screenshots.png)

## How is the app built?
This app is built natively using Android Studio with Java. The backend API is built using `Node.js` and `Express.js` which is then hosted to `Heroku`. [Click here](https://github.com/DineshBS44/EventRegAPI) to view the Github repo for the Backend.

## Features
* Login using Email and Phone number
* View the details for the Booked Events
* View the details for the Events that are not booked
* Book an event that is not booked earlier
* View the official Event page in Bookmyshow using the link in EventDetail page

## Components/DataStorage used
* **Navigation** - For simplified navigation through fragments
* **Shared Preferences** - For storing information regarding Login

## Other libraries/services used
* **Glide** - For loading images into ImageView
* **Gson** - For converting JSON to POJO and vice versa
* **Lottie** - For loading animations from lottiefiles.com
* **Retrofit** - For making Network calls
* **Custom API** - For getting the data
* **Heroku** - To deploy the API
* **Node.js** - To build the API
* **Express.js** - For configuring the API endpoints

## Developer
* **Dinesh B S** [(@DineshBS44)](https://github.com/DineshBS44)

## License
Licensed under MIT License :  https://opensource.org/licenses/MIT

<br>
<br>