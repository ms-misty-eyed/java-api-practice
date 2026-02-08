# API Practice Projects
Learning to work with REST APIs in Java.

## Projects

### Weather API
Gets current weather for any location using Open-Meteo API.
- No API key required
- Demonstrates basic GET requests
- JSON parsing with Gson
- Supports both Celsius and Fahrenheit

### Cat API
Gets random cat images and breed information using The Cat API.
- Optional API key (works without one)
- Working with JSON arrays
- Looping through multiple results
- Demonstrates proper limit handling

### GitHub API
Look up any GitHub user and display their profile information.
- No API key required
- User input with Scanner
- Working with flat JSON objects
- Displays username, followers, repos, and profile picture

### NASA APOD (TODO)
Gets NASA's Astronomy Picture of the Day.
- API key authentication
- Date-based queries
- Handling optional JSON fields

## How to Run
1. Clone the repo: `git clone https://github.com/ms-misty-eyed/java-api-practice.git`
2. Navigate to the project folder.
3. Run via Maven: `mvn clean compile exec:java -Dexec.mainClass="com.practice.Main"`

## What I Learned
- Making HTTP requests with HttpClient
- Parsing JSON responses with Gson
- Working with API documentation
- Error handling and status codes
- String formatting with `String.format()`
- Difference between JSON objects and arrays

## Technologies Used
- Java 11+
- Gson for JSON parsing
- Maven for dependency management
