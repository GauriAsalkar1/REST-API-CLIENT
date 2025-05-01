# REST-API-CLIENT

COMPANY: CODTECH IT SOLUTIONS

NAME: GAURI DEVIDAS ASALKAR

INTERN ID: CT04DA238

DOMAIN: JAVA PROGRAMMING

DURATION: 4 WEEKS

MENTOR: NEELA SANTOSH

DESCRIPTION OF THE TASK: This project involves developing a Java application that interacts with public REST APIs to fetch and display weather data for any specified city. The program serves as a client that makes HTTP requests to external weather services, processes JSON responses, and presents the information in a structured, user-friendly format. The application demonstrates core programming concepts such as API integration, HTTP communication, JSON parsing, and data visualization, making it an ideal project for showcasing backend development skills during an internship. The application begins by prompting the user to enter a city name, such as Mumbai, Delhi, or Bangalore. Using the Open-Meteo Geocoding API, it converts this city name into geographic coordinates (latitude and longitude). This step is crucial because weather APIs typically require coordinates rather than city names to fetch accurate data. The program handles this conversion seamlessly, ensuring the user does not need to manually look up or input coordinates. Once the coordinates are obtained, the application sends a second request to the Open-Meteo Weather API to retrieve real-time weather conditions for the specified location. The weather data received from the API includes several key metrics: Temperature (in Celsius), Wind speed (in km/h) and direction (in degrees, converted to compass directions like "North" or "Southwest"), Daylight status (whether it is currently daytime or nighttime at the location), Timestamp of the data. Additionally, the application enhances usability by providing a weather interpretation that describes the conditions in plain language. For instance, temperatures above 35°C are labeled "Very hot", while wind speeds exceeding 20 km/h are noted as "Strong winds." This feature makes the data more accessible to users who may not be familiar with raw meteorological metrics. To ensure robustness, the code includes error handling for scenarios like invalid city names or API failures. If the Geocoding API cannot find the city, the program gracefully informs the user and exits. Similarly, if the Weather API request fails (e.g., due to network issues), the user receives a clear error message instead of a cryptic exception.
This project meets all the requirements for the internship deliverable:
REST API Consumption: It interacts with two public APIs (Geocoding and Weather) using HTTP GET requests.
JSON Handling: It parses nested JSON structures to extract specific weather metrics.
Structured Output: Data is displayed in a logical, easy-to-read format.
User Interaction: The program accepts dynamic input (city names) and provides immediate feedback.
By completing this task, I demonstrated proficiency in:
Java programming (methods, classes, error handling).
API integration (sending requests, processing responses).
Data parsing (working with JSON).
User experience design (clear prompts and output).

OUTPUT:

![Image](https://github.com/user-attachments/assets/b24c69e4-ffa9-45b4-a1e3-83ff91643552)
