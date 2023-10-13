package http;

public class Main {
    public static void main(String[] args){
        URLReader urlReader = new URLReader();
        System.out.println(urlReader.get("http://help.websiteos.com"));
    }
}
