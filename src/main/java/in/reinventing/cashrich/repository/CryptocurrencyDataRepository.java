package in.reinventing.cashrich.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.reinventing.cashrich.entities.CryptocurrencyData;
import in.reinventing.cashrich.entities.User;

@Repository
public interface CryptocurrencyDataRepository extends JpaRepository<CryptocurrencyData, Long> {
    List<CryptocurrencyData> findByUser(User user);
}
