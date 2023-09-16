package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpClientMain {
    public static void main(String[] args) throws IOException {
        try (Client client = new Client("help.websiteos.com", 80)) {
            client.writeLine("GET /websiteos/example_of_a_simple_html_page.htm HTTP/1.1");
            client.writeLine("Host: help.websiteos.com");
            client.writeLine("");
            while (true){
                String line = client.readLine();
                System.out.println(line);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    static class Client implements AutoCloseable {
        private Socket clientSocket;
        OutputStream out;
        InputStream in;

        public Client(String ip, int port) throws IOException {
            this.clientSocket = new Socket(ip, port);
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();
            System.out.println("Клиент запустился");
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


        @Override
        public void close() throws Exception {
            out.close();
            in.close();
            clientSocket.close();
            System.out.println("Клиент закрылся");
        }
    }
}
