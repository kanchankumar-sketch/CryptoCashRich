package in.reinventing.cashrich.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.reinventing.cashrich.dtos.CryptocurrencyDataDTO;
import in.reinventing.cashrich.entities.CryptocurrencyData;
import in.reinventing.cashrich.entities.User;
import in.reinventing.cashrich.repository.CryptocurrencyDataRepository;
import in.reinventing.cashrich.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CryptocurrencyService {

    @Autowired
    private CryptocurrencyDataRepository cryptocurrencyDataRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AuthenticatedUserDetailsService authenticatedUserDetailsService;

    @Autowired
    private RestTemplate restTemplate;
    
    ResponseEntity<String> responseObject=null;

    @Value("${coinmarketcap.api.key}")
    private String apiKey;

    public CryptocurrencyDataDTO searchCryptocurrencyData(List<String> symbols) {
    	
    	String username=authenticatedUserDetailsService.getCurrentUsername();
    	
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        String symbolString = String.join(",", symbols);
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=" + symbolString;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        responseObject=response;
        // Parse the response (implementation not shown here)
        CryptocurrencyDataDTO cryptocurrencyData = new CryptocurrencyDataDTO();
        List<CryptocurrencyData> cryptoCurrencyDataList=new ArrayList<>();
        // Save the data to the database
        for (String symbol : symbols) {
            CryptocurrencyData data = new CryptocurrencyData();
            data.setSymbol(symbol);
            try {
	            Double priceExtracted=extractPrice(symbol);
	            BigDecimal price=new BigDecimal(priceExtracted);
	            data.setPrice(price);
            }catch(Exception e) {
            	e.printStackTrace();
            }
            data.setTimestamp(LocalDateTime.now());
            data.setUser(user);
            cryptoCurrencyDataList.add(data);
        }
        cryptocurrencyData.setCryptocurrencyDataList(cryptoCurrencyDataList);
        cryptocurrencyDataRepository.saveAll(cryptoCurrencyDataList);
        return cryptocurrencyData;
    }
    
    public double extractPrice(String symbol) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(this.responseObject.getBody());
        JsonNode priceNode = root.path("data").path(symbol).path("quote").path("USD").path("price");
        return priceNode.asDouble();
    }
}
