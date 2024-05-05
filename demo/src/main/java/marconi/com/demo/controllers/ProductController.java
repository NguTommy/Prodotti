package marconi.com.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import marconi.com.demo.domains.RegistrationForm;
import marconi.com.demo.services.ProductService;


@Controller
@RequestMapping("/")
public class ProductController {

      // non era necessario vista la mancanza di db, ma è comunque buona pratica
      @Autowired
      ProductService productService;

      
    @GetMapping
    public ModelAndView viewHomePage() {

        return new ModelAndView("homepage");
    }

    

    //localhost:8090/home/user?type=
    @GetMapping("/prodotto")
    public ModelAndView handlerUserAction(@RequestParam("type") String type) {

        // in base al parametro, mostro la pagina relativa
        if(type.equals("nuovo"))
            return new ModelAndView("product-registration").addObject("productForm", new RegistrationForm());
        
     
        // se il parametro è errato, pagina non trovata
        else 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagina non trovata!");
    }

    @GetMapping("/catalogo")
    public String catalogo(@ModelAttribute RegistrationForm productForm) {
        List<RegistrationForm> productList = productService.getProducts();
       // productForm.addProduct("productList", productList);
        return "catalogo";
    }

    @PostMapping("/catalogo/svuota")
    public String svuotaCatalogo(RedirectAttributes redirectAttributes) {
        productService.clearProducts();
        redirectAttributes.addFlashAttribute("Messaggio", "Il catalogo è stato svuotato con successo.");
        return "redirect:/catalogo";
    }


    @PostMapping("/prodotto/nuovo")
    public ModelAndView handlerNewUser(@ModelAttribute RegistrationForm productForm) {

        // salvo prodotto nel "database"
        productService.addProduct(productForm); 
      
        //pattern prg per reindirizzare la pagina ad una request get
        String nome = productForm.getNome();         
        return new ModelAndView("redirect:/prodotto/" + nome);
    }

    @GetMapping("/prodotto/{nome}")
    public  ModelAndView productDetail(@PathVariable("nome") String nome) {

        Optional<RegistrationForm> prodotto = productService.getProductByName(nome);

        // se l'utente esiste, mostro la pagina di recap
        if (prodotto.isPresent())
            return new ModelAndView("product-detail").addObject("prodotto", prodotto.get());
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prodotto non trovato!");
    }

   
    



  
    
}
