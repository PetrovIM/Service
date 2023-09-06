import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("localhost", 8080)) {
            System.out.println("Клиент запустился");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String serverWord;
            while ((serverWord = in.readLine()) != null){
                if (serverWord.equalsIgnoreCase("q")){
                    System.out.println("Связь с сервером завершена");
                    break;
                }
                System.out.println(serverWord);
                String x =reader.readLine();
                String y = reader.readLine();
                out.write(x+"\n");
                out.flush();
                out.write(y+"\n");
                out.flush();
                System.out.println("Отправил значение: " + x + " " + y);
                System.out.println("Ожидаю получение суммы чисел");
                String sum = in.readLine();
                System.out.println("Сумма чисел " + x + " + " + y + " равна: " + sum);
                break;
            }

        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }

    }
}
