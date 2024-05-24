package in.reinventing.cashrich.dtos;
import java.util.List;

import in.reinventing.cashrich.entities.CryptocurrencyData;

public class CryptocurrencyDataDTO {
    private List<CryptocurrencyData> cryptocurrencyDataList;

    public List<CryptocurrencyData> getCryptocurrencyDataList() {
        return cryptocurrencyDataList;
    }

    public void setCryptocurrencyDataList(List<CryptocurrencyData> cryptocurrencyDataList) {
        this.cryptocurrencyDataList = cryptocurrencyDataList;
    }
}
