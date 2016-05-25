package org.nda.javafx.piradioplayer2.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by NDA on 25.05.2016.
 */
public class License {

    private static License instance = null;

    private License() {}

    public static License getInstance(){
        if (instance == null){
            instance = new License();
        }
        return instance;
    }

    public boolean checkConnection(){
        try {
            URL url = new URL("http://google.com");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            return (Objects.equals(con.getResponseCode(), 200));
        } catch (IOException e) {
            new ErrorHandler(e,null);
            return false;
        }
    }

    public boolean checkLicense() {
        Pattern p = Pattern.compile(">(.*)</code>");
        String status = "";
        try {
            URL yahoo = new URL("http://nda86.github.io/license.html");
            URLConnection yc = yahoo.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;
            String html = "";

            while ((inputLine = in.readLine()) != null){
                html = html + inputLine + "\n";
            }

            Matcher m = p.matcher(html);
            if (m.find()){
                status = m.group(1);
            }
            in.close();

            return (Objects.equals(status, "true"));
        }
        catch (Exception e){
            new ErrorHandler(e, null);
            return false;
        }
    }
}
