package marconi.com.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import marconi.com.demo.domains.RegistrationForm;

@Service
public class ProductService {
      
    // creo un finto "database" dove salvare gli utenti registrati
    private ArrayList<RegistrationForm> products = new ArrayList<>();

    public ArrayList<RegistrationForm> getProducts() {
        return products;
    }

    public ProductService() {
        // aggiungi i prodotti di partenza nell'array
        addProdottoIniziali();
    }

   
    private void addProdottoIniziali() {
        // aggiungi prodotti di partenza
        RegistrationForm p1 = new RegistrationForm();
        p1.setNome("Fragole");
        p1.setCodice("01");
        addProduct(p1);

        RegistrationForm p2 = new RegistrationForm();
        p2.setNome("Mele");
        p2.setCodice("02");  
        addProduct(p2);


    }
    public void addProduct(RegistrationForm newProduct) {
        products.add(newProduct);
    }

    public Optional<RegistrationForm> getProductByName(String product) {

        for(RegistrationForm p : products) {
            if(p.getNome().equals(product)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public Optional<RegistrationForm> getProductByCodice(String product) {

        for(RegistrationForm p : products) {
            if(p.getCodice().equals(product)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public void clearProducts() {
        products.clear();
    }
}
