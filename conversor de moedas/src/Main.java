import java.net.http.HttpClient;
import java.util.Scanner;
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();

        ConversorMoeda conversorMoeda = new ConversorMoeda(httpClient, gson);

        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner, conversorMoeda);
        menu.executar();
    }
}