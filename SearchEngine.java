import java.io.IOException;
import java.util.ArrayList;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> list = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "Pass an input!";
        } else if (url.getPath().equals("/increment")) {
            num += 1;
            return String.format("Number incremented!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    list.add(parameters[1]);
                    return String.format("added: %s", parameters[1]);
                }
            }
            else if (url.getPath().contains("/search")){
                ArrayList<String> hasString = new ArrayList<String>();
                String[] parameters = url.getQuery().split("=");
                for (String added: list){
                    if (added.contains(parameters[1])){
                        hasString.add(added);
                    }
                }
                return "String that contains " + parameters[1] + ": " + hasString.toString();
            }
            return "404 Not Found!";
        }
    }
}
class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
