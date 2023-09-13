package byteservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServiceMain {
    public static void main(String[] args) throws IOException {
        Server server = new Server(80);
        Session session = server.waitSession();
        String request = session.readRequest();
        String response = processRequest(request);
        session.writeResponse(response);
    }

    private static String processRequest(String request) {
        String[] num = request.split("\\+");
        int one = Integer.parseInt(num[0]);
        int two = Integer.parseInt(num[1]);
        return sum(one, two);
    }

    public static String sum(int one, int two) {
        String sum = String.valueOf(one + two);
        return sum + " ";
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

        public String readRequest() {
            int c;
            String str = "";
            try {
                while ((c = in.read()) != -1) {
                    char x = (char) c;
                    if (x != ' ') {
                        str += x;
                    } else break;
                }
                System.out.println("Принял запрос");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            return str;
        }

        public void writeResponse(String response) throws IOException {
            byte[] req = response.getBytes();
            out.write(req);
            out.flush();
            System.out.println("Отправил ответ");
            System.out.println("Сервер закрылся");
        }
    }
}
