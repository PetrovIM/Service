package http;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLReader {

    public String get(String url){
        byte[] content = new byte[0];
        try (Client client = new Client(getHost(url), 80)) {
            client.writeLine("GET " + getPath(url) + " HTTP/1.1");
            client.writeLine("Host: " + getHost(url));
            client.writeLine("");

            Map<String, String> headers = client.readHeaders();
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            content = client.readBytes(contentLength);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new String(content, StandardCharsets.UTF_8);
    }

    public String getHost(String url){
        String [] x = regexUrl(url);
        return x[0];
    }

    public String getPath(String url){
        String [] x = regexUrl(url);
        if (x[1].equals("")) return x[1] = "/";
        return x[1];
    }

    public String [] regexUrl(String url){
        String [] x = new String[2];
        String pattern = "^(https?:\\/\\/)([a-z,.]*)(.*)$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(url);
        if (m.find()){
            x[0] = m.group(2);
            x[1] = m.group(3);
        }
        return x;
    }


}
