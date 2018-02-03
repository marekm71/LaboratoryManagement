package pl.wat.wcy.prz.i5b4s1.utils;

import com.google.gson.Gson;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

public class TimeUser{
    private final static Logger logger = Logger.getLogger(TimeUser.class);
    private static String TIME = "https://script.googleusercontent.com/macros/echo?user_content_key=xA4Yj-H1nCdR4-hfAD88jx1KWNNMnoG4PGLn0M69tJZA7cgEXLkuzw2HVm3n5IBf4t0UKnIl3piGZS-zSC0zPQ3Ar0kHf79ym5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnJ9GRkcRevgjTvo8Dc32iw_BLJPcPfRdVKhJT5HNzQuXEeN3QFwl2n0M6ZmO-h7C6bwVq0tbM60-njvSvl4kgC4&lib=MwxUjRcLr2qLlnVOLh12wSNkqcO1Ikdrk";

    public Service<Time> getTime(){
        return new Service<Time>() {
            @Override
            protected Task<Time> createTask() {
                return new Task<Time>() {
                    @Override protected Time call() throws Exception {
                        Time time;
                        URL url = null;
                        try {
                            url = new URL(TIME);
                        } catch (MalformedURLException e) {
                            logger.error("Błędny adres", e);
                        }
                        InputStreamReader reader = null;
                        try {
                            reader = new InputStreamReader(url.openStream());
                        } catch (IOException e) {
                            logger.error("Błąd związany z odczytem danych", e);
                        }
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        String response = bufferedReader.lines().collect(Collectors.joining());
                        Gson g = new Gson();
                        time=g.fromJson(response,Time.class);
                        return time;
                    }
                };
            }
        };
    }
}




