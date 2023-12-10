package org.netflixpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//import static org.netflixpp.AccessMoviesDB.script;

public class HelperThreads extends Thread{
    String input;
    String output;
    HelperThreads(String input, String output) {
        this.input = input;
        this.output = output;
    }
    public void run() {

        String[] shell = {"sh",
                System.getProperty("user.dir") + "/src/resources/scripts/mp4_to_360p.sh",
                input,
                output};

        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(shell);

            BufferedReader read = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));

            int p = proc.waitFor();

            //script(0, output);

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
