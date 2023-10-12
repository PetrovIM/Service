package http;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class URLReader {

    public String get(String url){
        byte[] content = new byte[0];
        try (Client client = new Client("help.websiteos.com", 80)) {
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

    public String getHost(String url) throws URISyntaxException {
        String [] x;
        x = parsingUrl(url);
        return x[0];
    }

    public String getPath(String url) throws URISyntaxException {
        String [] x;
        x = parsingUrl(url);
        return x[1];
    }

    public String[] parsingUrl(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String host = uri.getHost();
        String path = uri.getPath();
        String [] splitUrl = new String[2];
        splitUrl[0] = host;
        splitUrl[1] = path;
        return splitUrl;
    }


}
