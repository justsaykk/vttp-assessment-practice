package pastyearpaper.Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class HttpServer {

    public void start(Integer port, List<String> docRoot) throws UnknownHostException, IOException {
        ServerSocket server;
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {
            server = new ServerSocket(port);
            Boolean isOpen = true;

            while (isOpen) {
                System.out.println("Waiting for client connection");
                server.accept();
                Boolean successfulConnection = true;
                if (successfulConnection) {
                    System.out.println("Starting HttpClientConnection");
                    HttpClientConnection session = new HttpClientConnection();
                    threadPool.execute(session);
                } else
                    System.out.println("Connection failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
