## Teck Stack

1. **Java:** The entire project is to be implemented using Java, which will serve as the primary programming language for both the backend and frontend components.

2. **Jetty+Jersey:** Jetty is a web server and servlet container, and Jersey is a framework for building RESTful web services in Java. You'll use these technologies to set up your Application Server to handle HTTP requests and manage APIs.

3. **Nginx:** Nginx is a high-performance reverse proxy server. In your project, Nginx will act as a reverse proxy, routing incoming HTTP requests to the appropriate backend services, such as your Application Server. It helps with load balancing and routing traffic efficiently.

4. **MariaDB:** MariaDB is an open-source relational database management system (RDBMS) and a MySQL fork. You will use MariaDB to store the catalog metadata, which includes information about the available movies, user data, and other related information.

5. **Cassandra:** Cassandra is a distributed NoSQL database. It will be used for storing user-related data, providing scalability and high availability. Cassandra is suitable for scenarios where you need to handle large volumes of data across multiple nodes.

6. **Netty (or Android Network API):** Netty is an asynchronous event-driven network application framework for high-performance protocol servers and clients. You will use it to implement a lightweight BitTorrent protocol for file transfer between seeders and clients. Alternatively, you can use Android's low-level network APIs if you prefer to work at a lower level.

7. **SQLite:** SQLite is a lightweight, serverless, and self-contained SQL database engine. While MariaDB is used for catalog metadata, SQLite can be used for lightweight local database needs, such as storing configuration settings or caching data within your Android apps.

8. **FFmpeg:** FFmpeg is a multimedia framework that can be used for video and audio processing. You will utilize FFmpeg to create high-resolution (1080p) and low-resolution (360p) versions of each movie in your catalog. These versions are necessary to cater to different devices and bandwidths.

9. **Distributed File Storage:** Although not explicitly mentioned in your tech stack, you will need a distributed file storage solution (e.g., a file system or cloud storage) to store the actual video files. You will store links to these video files in your MariaDB database.

10. **Other Libraries and Resources:** Your project may involve using additional libraries and resources as needed. For example, you might use Android libraries for user interface design, authentication, and networking within your Android apps.

In summary, your tech stack encompasses a range of technologies, from Java for coding to various databases (MariaDB, Cassandra, SQLite) for data storage, and specialized tools like FFmpeg for video processing. This combination of technologies enables you to build a robust streaming service with authentication, video storage, content management, and a lightweight BitTorrent protocol for file distribution.


