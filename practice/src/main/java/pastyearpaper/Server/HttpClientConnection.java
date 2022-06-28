package pastyearpaper.Server;

import java.io.*;
import java.net.*;

public class HttpClientConnection implements Runnable {
    private InputStream in;
    private OutputStream out;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket sock;

    public HttpClientConnection(Socket sock) {
        this.sock = sock;
    }

    public void run() {
        try {
            initializeStreams();
            String req = ois.readUTF();
            isGet(req);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void isGet(String req) throws IOException {
        String[] terms = req.split(" ");
        if (!terms[0].equals("GET")) {
            String response = "HTTP/1.1 405 Method Not Allowed\r\n\r\n" + terms[0] + " not supported\r\n";
            write(response);
            close();
        }
    }

    private void initializeStreams() throws IOException {
        in = sock.getInputStream();
        ois = new ObjectInputStream(in);
        out = sock.getOutputStream();
        oos = new ObjectOutputStream(out);
    }

    private void write(String str) throws IOException {
        oos.writeUTF(str);
        oos.flush();
    }

    private void close() throws IOException {
        in.close();
        out.close();
    }

}
