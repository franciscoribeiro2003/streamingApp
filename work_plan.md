

## 1. Setting Up the Tech Stack:

- Java: The primary language for both backend and frontend development.
- Jetty+Jersey: For the Application Server.
- Nginx: For the reverse proxy.
- MariaDB: For managing the catalog and metadata.
- Cassandra: For storing user-related data.
- Netty (or Android network API): For implementing the lightweight BitTorrent protocol.
- SQLite: For lightweight database usage.

## 2. Setting Up Development Environment:

- Install and configure the necessary development tools for Java, Android app development, and database management.
- Set up a version control system (e.g., Git) for code management.

## 3. Database Design:

- Design the database schema for catalog metadata storage.
- Implement the database using MariaDB and SQLite as per the requirements.

## 4. Implementing the Backend:

- Develop the backend for the streaming service using Java.
- Create RESTful APIs for accessing and managing the catalog.
- Implement logic for user authentication and authorization.
- Set up links to video storage (you can use a distributed file system or cloud storage).

## 5. Video Encoding:

- Use FFmpeg to create high-resolution (1080p) and low-resolution (360p) versions for each movie.
- Store the encoded videos with proper links in your storage system.

## 6. Building the Android Apps:

- Develop two Android applications - one for video streaming and another for the CMS.
- Implement user authentication and password management in both apps.

## 7. Streaming App:

- Create a startup/loading screen with a logo.
- Develop an interface for browsing and selecting movies.
- Implement video streaming functionality.
- Implement a mesh protocol for file transfer between clients.

## 8. CMS App:**

- Build a user-friendly CMS for uploading and removing movies.
- Automatically generate low-resolution versions of uploaded movies.
- Implement user management (creation and removal) for the streaming app.

## 9. Mesh Subsystem (BitTorrent Protocol):

- Implement a lightweight BitTorrent protocol for file distribution.
- Divide files into chunks with SHA256 hashes.
- Set up one seeder per file and support multiple clients per file.
- Use Netty or Android network APIs for communication.

## 10. Testing and Debugging:

- Thoroughly test each component and functionality.
- Debug any issues and make necessary improvements.

## 11. Deployment:

- Deploy your streaming service on a suitable server environment.
- Configure Nginx as a reverse proxy to manage incoming requests.

## 12. Documentation and Submission:

- Create documentation that explains the architecture, design choices, and instructions for using your Netflix++ service.
- Prepare your assignment submission as required by your course guidelines.

## 13. Final Testing:

- Perform final testing to ensure all components work together seamlessly.

## 14. Presentation (if required):

- Prepare a presentation to demonstrate your Netflix++ service and its features to the class or professor.

Remember to follow best practices in software development, such as modular code design, version control, and documentation, throughout the project. Frequent testing and incremental development will help you manage the complexity of this assignment effectively.
