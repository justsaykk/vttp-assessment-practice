package pastyearpaper.Client;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientSession {

    private Socket sock;
    private InputStream is;
    private ObjectInputStream ois;
    private OutputStream os;
    private ObjectOutputStream oos;
    private List<String> docRoot;

    public ClientSession(Socket sock, List<String> docRoot) {
        this.sock = sock;
        this.docRoot = docRoot;
    }

    public void start() throws IOException {
        initializeStreams(sock);
        Boolean isOpen = true;
        while (isOpen) {
            request(docRoot);
            String response = read();
            System.out.println(response);
        }
        close();
    }

    private void initializeStreams(Socket sock)
            throws IOException {
        os = sock.getOutputStream();
        oos = new ObjectOutputStream(os);
        is = sock.getInputStream();
        ois = new ObjectInputStream(is);
    }

    private void close() throws IOException {
        is.close();
        os.close();
    }

    private void request(List<String> request) throws IOException {
        oos.writeObject(request);
        oos.flush();
    }

    private String read() throws IOException {
        return ois.readUTF();
    }
}
