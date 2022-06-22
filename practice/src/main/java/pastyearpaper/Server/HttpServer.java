package pastyearpaper.Server;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class HttpServer {
    String localhost = "localhost";

    public void start(Integer port, List<String> docRoot) throws UnknownHostException, IOException {
        System.out.printf("Attempting to connect to server at %s, port %d\n",
                localhost, port);
        Socket sock = new Socket(localhost, port);
        System.out.printf("Connected to server at %s, port %d\n", localhost, port);
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
