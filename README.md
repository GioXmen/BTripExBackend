## Backend server for BTrip - Business trip planner "Android" app

<p align="center">
  <img width="450" height="220" src="https://raw.githubusercontent.com/GioXmen/BTripExBackend/develop/BackEndReadme/index.png">
</p>

### Related Front-End Android app github project repository
https://github.com/GioXmen/BTrip_Planner

#### Installation pack
[Google drive link - Installation pack](https://drive.google.com/open?id=1-v1Cd9j46grtVd3sMsbdCL-JpVxU5KRj "Installation pack google drive download")

### 1. Context diagram for the whole project
- The project consists of a Android front-end application, which is used for user input, interaction and displaying results. The second part is the back-end server, which handles requests for data storage and retrieval. The server stores and retrieves data from a MySQL database. Secondly, the server is also capable of retrieving statistics data about the "COVID-19" global pandemic, serializing it into a usable data structure and passing it on to the front-end application. Lastly, the server is used for PDF report generation from user input data.

<p align="center">
  <img width="900" height="450" src="https://raw.githubusercontent.com/GioXmen/BTripExBackend/develop/BackEndReadme/ContextDiag.png">
</p>

### 2. Basic data model for Users, Trips and Events
- The data model consists of a user, that carries a username and password. The user may have more than one trip and a trip consists of name, description, destination, start date, end date and a thumbnail image. A trip may have more than one event associated to it, and an event consists name, description, location, start date, start time, end date, event type, event total expense and multiple expense images.
<p align="center">
  <img width="900" height="550" src="https://raw.githubusercontent.com/GioXmen/BTripExBackend/develop/BackEndReadme/Database_full.jpg">
</p>

### 3. Top down view of the created system - Simplified class diagram
The class diagram consists of these main parts:
- Main class - BTripRestController: Receives and sends back requests, main point of access for backend server functionality;
- Account Model package: Holds object models for serializing and packing data into usable data objects during requests. 
  - User
  - Trip
  - Event
    - EventType
  - ExpenseReport: Used for storing generated report data, before sending it to the frontend application.
  - CovidSummary: Used for storing COVID-19 statistics data retrieved from outside data source.
    - CovidGlobal
    - CovidCountries
- Utility package - Util: Used for accesing additional functions from the main class. 
  - ImageUtilityImpl: Used for retrieving thumbnail image data from an outside source, this is done when a trip without an image is added.
  - JasperReportsUtil: Used for generating expense reports with user expense and expense image input. Expense reports are generated using the JasperReports library. The report format is JRXML, which is similar to regular XML, but with added features for report generation. The JRXML code is what tells the library how to generate the PDF report with given input data.

<p align="center">
  <img width="900" height="450" src="https://raw.githubusercontent.com/GioXmen/BTripExBackend/develop/BackEndReadme/Serverio%20klasiu.jpg">
</p>
