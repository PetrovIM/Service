package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HttpServiceMain {
    public static void main(String[] args) throws IOException {
        Server server = new Server(80);
        Session session = server.waitSession();
        List<String> headerList = session.readHeaders();
        headerList.forEach(System.out::println);
        System.out.println("Over =========");
        String content = "Привет, меня зовут Илья!";
        byte[] contentBytes = content.getBytes();
        int lengthContent = contentBytes.length;
        session.writeLine("HTTP/1.1 200 OK\r\n");
        session.writeLine("Content-Type: text/html; charset=utf-8\r\n");
        session.writeLine("Content-Length: " + lengthContent + "\r\n");
        session.writeLine("\r\n");
        session.writeBytes(contentBytes);


    }

    private static void processRequest(String request) {
        System.out.println(request);
    }


    static class Server {
        private ServerSocket serverSocket;

        public Server(int port) throws IOException {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Сервер запустился");
        }

        public Session waitSession() throws IOException {
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            return new Session(socket);
        }
    }

    static class Session {
        OutputStream out;
        InputStream in;

        public Session(Socket socket) throws IOException {
            out = socket.getOutputStream();
            in = socket.getInputStream();
        }

        public List<String> readHeaders() {
            List<String> headers = new ArrayList<>();
            while (true) {
                String line = readLine();
                if (line.isEmpty()) {
                    break;
                }
                headers.add(line);
            }
            return headers;
        }

        public String readLine() {
            int c;
            String str = "";
            try {
                while ((c = in.read()) != -1) {
                    char x = (char) c;
                    if (x != '\n') {
                        if (x != '\r') {
                            str += x;
                        }
                    } else {
                        break;
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            return str;
        }

        public void writeLine(String response) throws IOException {
            byte[] req = response.getBytes();
            out.write(req);
            out.flush();
        }

        public void writeBytes(byte [] response) throws IOException {
            out.write(response);
            out.flush();
        }
    }
}
