package pastyearpaper.Client;

import java.io.*;
import java.net.*;
import java.util.*;

/*
General Steps to follow when creating server <-> client connections
    1. Create a class to handle the session (especially for multithreaded operations)
    2. In the client-side session class perform the following:
        2a. Create output streams & input streams (in this order)
        2b. Make sure to handle both the client-side request and the server-side response
*/
public class Main {
    public static void main(String[] args) throws IOException {
        // Get args
        int PORT = 3000;
        List<String> docRoot = new ArrayList<>();

        switch (args.length) {
            case 0:
                docRoot.add("./target");
                break;
            case 2:
                switch (args[0]) {
                    case "--docRoot":
                        for (String string : args[1].split(":")) {
                            docRoot.add(string);
                        }
                        break;

                    case "--port":
                        PORT = Integer.parseInt(args[1]);
                        docRoot.add("./target");
                        break;

                    default:
                        break;
                }
                break;

            case 4:
                switch (args[0]) {
                    case "--port":
                        PORT = Integer.parseInt(args[1]);
                        for (String string : args[3].split(":")) {
                            docRoot.add(string);
                        }
                        break;
                    case "--docRoot":
                        PORT = Integer.parseInt(args[3]);
                        for (String string : args[1].split(":")) {
                            docRoot.add(string);
                        }
                    default:
                        break;
                }
                break;

            default:
                break;
        }

        // Start new HttpServer
        Socket sock = new Socket("localhost", PORT);
        ClientSession clientSession = new ClientSession(sock, docRoot);
        clientSession.start();
    }
}
