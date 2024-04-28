import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonParser;

import com.google.gson.Gson;

public class ConversorMoeda {
    private final HttpClient httpClient;
    private final Gson gson;

    public ConversorMoeda(HttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    public double converter(double valorEntrada, String moedaEntrada, String moedaSaida) throws IOException, InterruptedException {
        TaxaCambio taxaCambio = getTaxaCambio(moedaEntrada, moedaSaida);
        double taxaCambioValor = taxaCambio.getConversionRate();
        return valorEntrada * taxaCambioValor;
    }

    private TaxaCambio getTaxaCambio(String moedaEntrada, String moedaSaida) throws IOException, InterruptedException {
        String apiKey = "fd7e8a5d3f89a0b5ad42a85e";
        String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s", apiKey, moedaEntrada, moedaSaida);

        //System.out.println("URL: " + url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiKey)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            //System.out.println("Response status code: 200");
            String responseBody = response.body();

            // Parse o JSON usando Gson para obter o valor de conversion_rate
            double conversionRate = JsonParser.parseString(responseBody).getAsJsonObject().get("conversion_rate").getAsDouble();
            //System.out.println("Conversion rate: " + conversionRate);

            TaxaCambio taxaCambio = new TaxaCambio();
            taxaCambio.setConversionRate(conversionRate);
            return taxaCambio;
        } else {
            System.out.println("Response status code: " + response.statusCode());
            String errorMessage = String.format("Desculpe, não foi possível obter a taxa de câmbio para %s para %s. Por favor, tente novamente. (Error code: %d)", moedaEntrada, moedaSaida, response.statusCode());
            System.err.println(errorMessage);
            throw new IOException(errorMessage);
        }
    }
}