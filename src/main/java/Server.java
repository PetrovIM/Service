import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Сервер запущен");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключился");
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                String hello = "Введи числа для суммирования";
                writer.write(hello);
                writer.newLine();
                writer.flush();
                System.out.println("Ожидаю получения чисел...");
                String x = reader.readLine();
                String y = reader.readLine();
                int one = Integer.parseInt(x);
                int two = Integer.parseInt(y);
                String sum = Integer.toString(sum(one,two));
                System.out.println("Передаю сумму:" + sum);
                writer.write(sum);
                writer.newLine();
                writer.flush();

            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static int sum(int x, int y){
        return x + y;
    }
}
