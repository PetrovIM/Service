package byteservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) throws IOException{
        try (Client client = new Client("localhost",80)) {
            client.writeRequest("111+22 ");
            client.readResponse();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    static class Client implements AutoCloseable{
        private Socket clientSocket;
        OutputStream out;
        InputStream in;

        public Client(String ip, int port) throws IOException {
            this.clientSocket = new Socket(ip,port);
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();
            System.out.println("Клиент запустился");
        }

        public void writeRequest(String request) throws IOException {
            byte[] q = request.getBytes();
            out.write(q);
            out.flush();
            System.out.println("Отправил запрос: " + request);
            System.out.println("Жду результат");

        }
        public void readResponse() throws IOException {
            int z;
            String str = "";
            while ((z = in.read()) != -1) {
                char x = (char) z;
                if (x != ' ') {
                    str += x;
                } else break;
            }
            System.out.println("Получен ответ: " + str);
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
