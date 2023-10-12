package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client implements AutoCloseable {
    private Socket clientSocket;
    OutputStream out;
    InputStream in;

    public Client(String ip, int port) throws IOException {
        this.clientSocket = new Socket(ip, port);
        out = clientSocket.getOutputStream();
        in = clientSocket.getInputStream();
//        System.out.println("Клиент запустился");
    }

    public void writeLine(String line) throws IOException {
        line += "\r\n";
        byte[] q = line.getBytes();
        out.write(q);
        out.flush();
    }

    public String readLine() throws IOException {
        int z;
        String str = "";
        while ((z = in.read()) != -1) {
            char x = (char) z;
            if (x != '\n') {
                if (x != '\r') {
                    str += x;
                }
            } else {
                break;
            }
        }
        return str;
    }

    public Map<String, String> readHeaders() throws IOException {
        Map<String, String> headers = new HashMap<>();
        while (true) {
            String line = readLine();
            if (line.isEmpty()) {
                break;
            }
            if (line.contains(":")) {
                String[] temp = line.split(":");
                headers.put(temp[0].trim(), temp[1].trim());
            }
        }
        return headers;
    }


    public byte[] readBytes(int countByte) throws IOException {
        byte[] arrayByte = new byte[countByte];
        int totalRead = 0;
        while (totalRead < countByte) {
            int count = in.read(arrayByte, totalRead, arrayByte.length - totalRead);
            totalRead += count;
        }
        return arrayByte;
    }


    @Override
    public void close() throws Exception {
        out.close();
        in.close();
        clientSocket.close();
//        System.out.println("Клиент закрылся");
    }
}
