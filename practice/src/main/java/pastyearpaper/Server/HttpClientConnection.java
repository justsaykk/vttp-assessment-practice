package pastyearpaper.Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class HttpClientConnection {

    public void start(Integer port, List<String> docRoot) throws UnknownHostException, IOException {
        docRootChecks(docRoot);
    }

    private void docRootChecks(List<String> docRoot) {
        for (String el : docRoot) {
            File file = new File(el);
            if (!file.isDirectory() || !file.canRead()) { // .isDirectory() also checks if the path exists.
                System.out.println("File is not a directory/not readable\n");
                System.exit(1);
            }
            System.out.printf("The file '%s' exists and is readable\n", el);
        }
    }
}
