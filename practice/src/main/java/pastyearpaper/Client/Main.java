package pastyearpaper.Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

import pastyearpaper.Server.HttpServer;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {
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
        HttpServer httpServer = new HttpServer();
        httpServer.start(PORT, docRoot);
    }
}
