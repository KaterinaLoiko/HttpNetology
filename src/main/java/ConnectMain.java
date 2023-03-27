import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ConnectMain {

  public static final ObjectMapper mapper = new ObjectMapper();
  public static final String URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

  public static void main(String[] args) throws IOException {
    CloseableHttpClient httpClient = HttpClientBuilder.create()
        .setDefaultRequestConfig(RequestConfig.custom()
            .setConnectTimeout(5000)
            .setSocketTimeout(30000)
            .setRedirectsEnabled(false)
            .build())
        .build();
    HttpGet request = new HttpGet(URL);
    CloseableHttpResponse response = httpClient.execute(request);

    List<ResponseCat> cats = mapper.readValue(response.getEntity().getContent(),
        new TypeReference<>() {
        });
    List<ResponseCat> sortedCat = cats.stream()
        .filter(value -> value.getUpvotes() != null && value.getUpvotes() > 0).collect(
            Collectors.toList());
    sortedCat.forEach(System.out::println);
  }
}
