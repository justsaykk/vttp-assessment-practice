package pastyearpaper;

import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Get args
        int PORT = 3000;
        List<String> docRoot = new ArrayList<>();

        if (args.length == 0) {
            docRoot.add("./target");
        }
        ;
        if (args.length == 2) {
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
        }
        ;
        if (args.length == 4) {
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
        }
        System.out.printf("PORT = %s\n", PORT);
        System.out.println(docRoot.toString());
    }
}
