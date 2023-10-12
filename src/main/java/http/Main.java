package http;

public class Main {
    public static void main(String[] args){
        URLReader urlReader = new URLReader();
        System.out.println(urlReader.get("http://help.websiteos.com/websiteos/example_of_a_simple_html_page.htm"));
    }
}
