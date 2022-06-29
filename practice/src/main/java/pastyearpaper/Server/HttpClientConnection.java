package pastyearpaper.Server;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import javax.imageio.ImageIO;

public class HttpClientConnection implements Runnable {
    private InputStream in;
    private OutputStream out;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket sock;
    private String[] terms;
    private List<String> docRoot;
    private String response;

    public HttpClientConnection(Socket sock, List<String> docRoot) {
        this.sock = sock;
        this.docRoot = docRoot;
    }

    public void run() {
        try {
            initializeStreams();
            String req = ois.readUTF();
            this.terms = req.split(" ");
            isGet(terms[0]);
            isFound(docRoot);
            writeBytes(docRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void isGet(String term) throws IOException {
        if (!term.equals("GET")) {
            response = "HTTP/1.1 405 Method Not Allowed\r\n\r\n" + terms[0] + " not supported\r\n";
            writeStr(response);
            close();
        }
    }

    private void isFound(List<String> dir) throws IOException {
        for (String el : dir) {
            if (el.equals("/")) {
                el = "/index.html";
            }

            File file = new File(el);

            if (!file.isDirectory() || !file.canRead()) {
                response = "HTTP/1.1 405 Not Found\r\n \r\n" + el + " not found\r\n";
                writeStr(response);
                close();
            }
        }
    }

    private void initializeStreams() throws IOException {
        in = sock.getInputStream();
        ois = new ObjectInputStream(in);
        out = sock.getOutputStream();
        oos = new ObjectOutputStream(out);
    }

    private void writeStr(String str) throws IOException {
        oos.writeUTF(str);
        oos.flush();
    }

    private void writeBytes(List<String> dir) throws IOException {
        for (String el : dir) {
            File file = new File(el);
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
            if (el.endsWith(".png")) {
                response = "HTTP/1.1 200 OK\r\n Content-Type: image/png\r\n \r\n";
                ImageIO.write(ImageIO.read(file), "png", baos);
            } else {
                byte[] bytes = Files.readAllBytes(Paths.get(el));
                baos.write(bytes);
            }
            baos.flush();
            baos.close();
        }
    }

    private void close() throws IOException {
        in.close();
        out.close();
    }

}
