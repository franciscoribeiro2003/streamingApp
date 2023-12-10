package org.netflixpp;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

//import jakarta.ws.rs.core.Response;
//import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
//import org.glassfish.jersey.media.multipart.FormDataParam;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.netflixpp.ConnectionDB.*;

@Path("/movie")
public class AccessMoviesDB {

    ObjectMapper map = new ObjectMapper();

    @Path("/resources")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getLink() {
        openDB();
        String movies = getMovies();
        closeDB();
        return movies;
    }

    private static final String VIDEOS_PATH = "/videos/high/"; // Path within resources

    @GET
    @Path("/ConvertHls/{fileName}")
    public Response streamMovie(@PathParam("fileName") String fileName) {
        try {
            // Get input video stream from resources
            InputStream videoStream = this.getClass().getResourceAsStream(VIDEOS_PATH + fileName + ".mp4");
            if (videoStream == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Video file not found").build();
            }

            // Write the video content to a temporary file
            File tempInputFile = File.createTempFile("input_", ".mp4");
            try (OutputStream outputStream = new FileOutputStream(tempInputFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = videoStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // Close the input stream
            videoStream.close();

            // Get the output directory within resources
            //String outputDirectoryPath = this.getClass().getResource("/movies/").getPath();

            // Convert video to HLS using ffmpeg
            String outputFilePath = "/home/francisco/Desktop/3ano/programacao em diapositivos moveis/streamingApp/final/Jetty/src/main/resources/movies/" + fileName + ".m3u8"; // Output HLS path within resources
            convertVideoToHLS(tempInputFile.getAbsolutePath(), outputFilePath);

            // Serve the HLS file
            return serveHLSFile(outputFilePath);
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing the file").build();
        }
    }

    // Convert video to HLS using ffmpeg
    private void convertVideoToHLS(String inputPath, String outputPath) {
        String[] command = {
                "ffmpeg",
                "-i", inputPath,
                "-c:v", "h264",
                "-hls_time", "10",
                "-hls_list_size", "0",
                outputPath
        };

        System.out.println("FFmpeg Command: " + Arrays.toString(command));

        ProcessBuilder processBuilder = new ProcessBuilder(command);

        try {
            Process process = processBuilder.start();

            // Capture FFmpeg output and error streams
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Print FFmpeg output
            String line;
            while ((line = inputReader.readLine()) != null) {
                System.out.println("FFmpeg Output: " + line);
            }

            // Print FFmpeg error (if any)
            while ((line = errorReader.readLine()) != null) {
                System.err.println("FFmpeg Error: " + line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("FFmpeg process ended with non-zero exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    // Serve the HLS file
    private Response serveHLSFile(String filePath) {
        File hlsFile = new File(filePath);

        try {
            if (hlsFile.exists()) {
                InputStream inputStream = new FileInputStream(hlsFile);
                String mimeType = "application/x-mpegURL"; // HLS MIME type
                return Response.ok(inputStream, mimeType).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("File not found").build();
            }
        } catch (FileNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error handling the file").build();
        }
    }

    @GET
    @Path("/stream/{fileName}")
    public Response streamMovie1(@PathParam("fileName") String fileName) {
        // Construct the path to the resources folder where movies are stored
        String filePath = "/videos/high/" + fileName + ".mp4"; // Assuming movies are in the resources folder

        // Load the file from the resources folder
        InputStream inputStream = this.getClass().getResourceAsStream(filePath);

        if (inputStream != null) {
            // Set the response content type based on the file type (MP4 in this case)
            String mimeType = "video/mp4"; // Change this if your file type is different

            // Return the file content
            return Response.ok(inputStream, mimeType).build();
        } else {
            // If the file doesn't exist, return a 404 Not Found response
            return Response.status(Response.Status.NOT_FOUND).entity("File not found").build();
        }
    }

    @GET
    @Path("/streamHls/{fileName}")
    public Response streamMovie2(@PathParam("fileName") String fileName) {
        // Construct the path to the directory where movies are stored
        String m3u8FilePath = "/movies/" + fileName + ".m3u8";

        try {
            List<String> tsFileList = getTsFilesFromM3u8(m3u8FilePath);
            System.out.println("tsFileList: " + tsFileList);

            if (!tsFileList.isEmpty()) {
                // Set the response content type based on the file type (MPEG-HLS in this case)
                String mimeType = "video/MP2T"; // Change this if your file type is different

                // Stream each .ts file
                for (String tsFile : tsFileList) {

                    java.nio.file.Path tsFilePath = Paths.get(tsFile);
                    InputStream inputStream = Files.newInputStream(tsFilePath);

                    // Return each .ts file content as a stream
                    Response response = Response.ok(inputStream, mimeType).build();


                }

                // After streaming all .ts files, you may need to signal the end of streaming or handle accordingly.
                // For instance, if sending via HTTP, you might send a response indicating the completion of streaming.
            } else {
                // If no .ts files found, return a 404 Not Found response
                return Response.status(Response.Status.NOT_FOUND).entity("No .ts files found in the playlist").build();
            }
        } catch (IOException e) {
            // Handle file IO errors or parsing errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error streaming files").build();
        }

        // Return a response indicating the streaming process (possibly after streaming all .ts files)
        return Response.ok("Streaming completed").build();
    }

    // Method to parse the .m3u8 file and extract .ts file paths
    private List<String> getTsFilesFromM3u8(String m3u8FilePath) throws IOException {
        List<String> tsFileList = new ArrayList<>();

        // Read the .m3u8 file and extract .ts file paths
        BufferedReader reader = new BufferedReader(new FileReader(m3u8FilePath));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.endsWith(".ts")) {
                // Add the .ts file path to the list
                tsFileList.add(line.trim());
            }
        }

        reader.close();
        return tsFileList;
    }


    @PUT
    @Path("/uploadVideo/")
    //consume video
    public Response uploadVideo(InputStream file) {
        try {
            // Write the video content to a temporary file
            File tempInputFile = File.createTempFile("input_", ".mp4");
            try (OutputStream outputStream = new FileOutputStream(tempInputFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = file.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // Close the input stream
            file.close();

            // Get the output directory within resources
            //String outputDirectoryPath = this.getClass().getResource("/movies/").getPath();

            // Convert video to HLS using ffmpeg
            String outputFilePath = "/home/francisco/Desktop/3ano/programacao em diapositivos moveis/streamingApp/final/Jetty/src/main/resources/movies/" + "test" + ".m3u8"; // Output HLS path within resources
            convertVideoToHLS(tempInputFile.getAbsolutePath(), outputFilePath);

            // Serve the HLS file
            return serveHLSFile(outputFilePath);
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing the file").build();
        }
    }

    // upload a mp4 from a local file to the server that will be resources/videos/high in a mp4 file format
    @POST
    @Path("/storeMp4/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response storeMp4(InputStream file) {
        try {
            // Write the video content to a temporary file
            File tempInputFile = File.createTempFile("input_", ".mp4");
            try (OutputStream outputStream = new FileOutputStream(tempInputFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = file.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // save the mp4 file in the server resoureces, since resources is a foder of the project
            //get resources path
            String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/videos/high/";
            //save the file in the resources folder
            saveFile(file, resourcesPath + "test.mp4");


        }
        catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing the file").build();
        }
        return null;
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createMovie(String credentialsJson) {
        openDB();
        String movieDetails = createMovieDB(credentialsJson);
        closeDB();
        return movieDetails;
    }

    private String createMovieDB(String credentialsJson){
        String[] parts = credentialsJson.split(",");
        String moviename = parts[0].split(":")[1].replaceAll("\"", "").trim();
        String username = parts[1].split(":")[1].replaceAll("\"", "").trim();
        String year = parts[2].split(":")[1].replaceAll("\"", "").trim();
        String duration = parts[3].split(":")[1].replaceAll("\"", "").replaceAll("}", "").trim();

        PreparedStatement SQLnext_id = null;
        PreparedStatement stm = null;
        try {
            SQLnext_id = connection.prepareStatement(
                    "SELECT AUTO_INCREMENT from information_schema.TABLES " +
                            "where TABLE_SCHEMA = 'BOOSBTV' and TABLE_NAME= 'MOVIE';");

            ResultSet next_id = SQLnext_id.executeQuery();

            if (!next_id.next()) {
                closeDB();
                return Response.status(401).entity("Error connecting to the database. Exiting upload.").build().toString();
            }

            String pathHigh = System.getProperty("user.dir") + "/src/resources/videos/high/" + moviename + ".mp4";
            String pathLow = System.getProperty("user.dir") + "/src/resources/videos/low/" + moviename + "_lowVersion.mp4";
            //String Thumbnail = System.getProperty("user.dir") + "/src/resources/imgs/" + moviename + "-poster.jpeg";
            String linkHigh = "http://192.168.0.127:8080/movie/stream/" + moviename;


            // Query database to check user existence and retrieve details
            String query = "INSERT INTO MOVIE (name, pathHigh, pathLow, year, duration, linkLow, linkHigh, uploadedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, moviename);
            statement.setString(2, "pathHigh");
            statement.setString(3, "pathLow");
            statement.setString(4, year);
            statement.setString(5, duration);
            statement.setString(6, "linkLow");
            statement.setString(7, linkHigh);
            //statement.setString(8, "Thumbnail");
            statement.setString(8, username);
            statement.executeUpdate();

            // Check if the user exists
            return "Movie created successfully";
        } catch (SQLException e) {
            e.printStackTrace();
            // Return an error message as JSON in case of exception
            return "Error creating movie";
        }
    }



    public static void saveFile(InputStream is, String fileLocation) throws FileNotFoundException {
        //System.out.println("Physically storing the video on {}", fileLocation);
        try {
            OutputStream os = Files.newOutputStream(new File(fileLocation).toPath());
            byte[] buffer = new byte[256];
            int bytes = 0;
            while ((bytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytes);
            }
            os.close();
            is.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    //Post /movie/upload
    /*
    @Path("/upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload (@FormDataParam("moviename") String moviename,
                            @FormDataParam("username") String username,
                            @FormDataParam("movieFile") InputStream file,
                            @FormDataParam("movieFile") FormDataContentDisposition dp,
                            @FormDataParam("thumb") InputStream poster,
                            @FormDataParam("year") int year,
                            @FormDataParam("totaltime") int t) {

        if(moviename == null) return Response.status(401).entity("Upload faced an error. Exiting upload.").build();
        String file_format = dp.getFileName().substring(dp.getFileName().lastIndexOf("."));
        openDB();
        PreparedStatement SQLnext_id = null;
        PreparedStatement stm = null;
        try {
            SQLnext_id = connection.prepareStatement(
                    "SELECT AUTO_INCREMENT from information_schema.TABLES " +
                            "where TABLE_SCHEMA = 'BOOSBTV' and TABLE_NAME= 'MOVIE';");

            ResultSet next_id = SQLnext_id.executeQuery();

            if (!next_id.next()) {
                closeDB();
                return Response.status(401).entity("Error connecting to the database. Exiting upload.").build();
            }

            String name = moviename + "_" + next_id.getInt(1);

            String pMovieHigh = System.getProperty("user.dir") + "/src/resources/videos/high/" + name + ".mp4";
            String pMovieLow = System.getProperty("user.dir") + "/src/resources/videos/low/" + name + "_lowVersion.mp4";
            String pPoster = System.getProperty("user.dir") + "/src/resources/imgs/" + moviename + "-poster_" + next_id.getInt(1) + ".jpeg";
            String local=  "http://movie/nginx/src/main/resources/movies/";
            String linkHigh = local + name + ".mp4";
            String linkLow = local + name + "_lowVersion" + ".mp4";
            String linkPoster = local + moviename + "-poster"+ "_" + next_id.getInt(1) + ".jpeg";
            /*
            String linkHigh = "https://storage.googleapis.com/moviespdm/" + name + ".mp4";
            String linkLow = "https://storage.googleapis.com/moviespdm/" + name + "_lowVersion" + ".mp4";
            String linkPoster = "https://storage.googleapis.com/moviespdm/" + moviename + "-poster"+ "_" + next_id.getInt(1) + ".jpeg";
            */
            /*
            saveFile(file, pMovieHigh);

            /*
            if (poster == null) {
                thumbnail(pMovieHigh, pPoster.substring(0, pPoster.lastIndexOf(".")));
            } else {
                saveFile(poster, pPoster);
            }
            */
            /*
            if (poster != null) {
                saveFile(poster, pPoster);
            }

            stm = connection.prepareStatement(
                    "insert into MOVIE " +
                            "(name,pathHigh,pathLow,year,duration,linkLow,linkHigh,Thumbnail,uploadedBy) " +
                            "values (?,?,?,?,?,?,?,?,?)"
            );

            stm.setString(1, moviename); // para ?
            stm.setString(2, pMovieHigh); // para ?
            stm.setString(3, pMovieLow); // para ?
            stm.setInt(4, year); // para ?
            stm.setInt(5, t); // para ?
            stm.setString(6, linkLow); // para ?
            stm.setString(7, linkHigh); // para ?
            stm.setString(8, linkPoster); // para ?
            stm.setString(9, username); // para ?

            int rs2 = stm.executeUpdate();

            if(rs2>0) {
                closeDB();

                if(file_format.equals(".mp4")) {
                    HelperThreads ht = new HelperThreads(pMovieHigh, pMovieLow);
                    ht.start();
                    script(0, pMovieHigh);
                    script(0, pPoster);


                } else {
                    HelperThreads1 h1 = new HelperThreads1(pMovieHigh,pMovieHigh.substring(0,pMovieHigh.lastIndexOf(".")), pMovieLow, pPoster);
                    h1.start();
                }

                return Response.status(200).entity("Upload done!").build();
            }
            closeDB();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Response.status(401).entity("Movie not found").build();
    }


    public static  void script(int n, String movie) throws IOException {
        //System.out.println(movie);
        String[] shell;
        if (n == 0) {
            shell = new String[]{"sh", System.getProperty("user.dir") + "/src/resources/scripts/uploadMovie.sh", movie};
        } else if(n == 1){
            shell = new String[]{"sh", System.getProperty("user.dir") + "/src/resources/scripts/removeMovie.sh", movie};
        } else {
            shell = new String[]{"sh", System.getProperty("user.dir") + "/src/resources/scripts/removeThumb.sh", movie};
        }
        Process proc = Runtime.getRuntime().exec(shell);
        BufferedReader read = new BufferedReader(new InputStreamReader(
                proc.getInputStream()));
        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void thumbnail(String input, String output) throws IOException {
        String[] shell = {"sh",
                System.getProperty("user.dir") + "/src/resources/scripts/generateThumb.sh",
                input,
                output};

        Process proc = Runtime.getRuntime().exec(shell);
        BufferedReader read = new BufferedReader(new InputStreamReader(
                proc.getInputStream()));
        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    */





    //Post ..../movie/search/







    //sem prioridade
    //Post /movie/delete/
    //Post /user/delete
}
