package in.reinventing.cashrich.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.reinventing.cashrich.dtos.CryptocurrencyDataDTO;
import in.reinventing.cashrich.service.CryptocurrencyService;

@RestController
@RequestMapping("/api/cryptocurrency")
public class CryptocurrencyController {

    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    @GetMapping("/search")
    public ResponseEntity<CryptocurrencyDataDTO> searchCryptocurrencyData(@RequestParam("symbols") List<String> symbols) {
        CryptocurrencyDataDTO cryptocurrencyData = cryptocurrencyService.searchCryptocurrencyData(symbols);
        return ResponseEntity.ok(cryptocurrencyData);
    }
}
