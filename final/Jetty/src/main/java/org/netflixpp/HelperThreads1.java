package org.netflixpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//import static org.netflixpp.AccessMoviesDB.script;

public class HelperThreads1 extends Thread{
    String input;
    String output;
    String pMovieHigh;
    String pMovieLow;
    String pPoster;

    HelperThreads1(String input, String output, String pMovieLow, String pPoster) {
        this.input = input;
        this.output = output;
        this.pMovieLow = pMovieLow;
        this.pPoster = pPoster;
    }
    public void run() {

        String[] shell = {"sh",
                System.getProperty("user.dir") + "/src/resources/scripts/toMp4.sh",
                input,
                output};

        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(shell);

            BufferedReader read = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));

            proc.waitFor();
            // System.out.println(p);

            HelperThreads ht = new HelperThreads(pMovieHigh, pMovieLow);
            ht.start();

            //script(0, pMovieHigh);

            //script(0, pPoster);

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
