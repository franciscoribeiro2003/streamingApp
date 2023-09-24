1. **User Uploads a Video:**
   
   - A user in your Android app initiates the video upload process, selecting a video file from their device.

2. **App Communicates with Server:**
   
   - The app communicates with your server to request permission to upload the video. This communication involves authentication and authorization to ensure the user has the necessary permissions.

3. **Server Generates Pre-Signed URL:**
   
   - Your server generates a pre-signed URL for the video upload, allowing the user to upload directly to your cloud storage.

4. **User Uploads the Video:**
   
   - The app sends the video file directly to the cloud storage using the pre-signed URL.

5. **Server Receives the Uploaded Video:**
   
   - Once the video is uploaded to the cloud storage, your server is notified or polls the storage service to confirm that the video has been successfully uploaded.

6. **Server-Side Validation and Sanitization:**
   
   - At this point, you use FFmpeg to encode the uploaded video. This involves:
     - Creating high-resolution (1080p) and low-resolution (360p) versions of the video.
     - Checking and adjusting video format, codec, and quality as needed to meet your streaming service's requirements.
     - Generating video thumbnails or extracting metadata if necessary.
     - Ensuring the video meets security and content requirements.

7. **Store Encoded Videos:**
   
   - Once FFmpeg has finished encoding, you store the encoded videos (both high and low resolution) in your cloud storage.

8. **Cataloging and Database Update:**
   
   - Update your database with information about the uploaded video, including references to the newly encoded video files and their metadata.
