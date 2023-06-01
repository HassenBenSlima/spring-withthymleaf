package com.sip.gestionarticles.controllers;

import com.sip.gestionarticles.entities.Produit;
import com.sip.gestionarticles.entities.Provider;
import com.sip.gestionarticles.repositoties.ProduitRepository;
import com.sip.gestionarticles.repositoties.ProviderRepository;
import com.sip.gestionarticles.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderController {

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
    private final ProviderRepository providerRepository;
    private final ProviderService providerService;

    private final ProduitRepository produitRepository;

    @Autowired
    public ProviderController(ProviderRepository providerRepository, ProviderService providerService, ProduitRepository produitRepository) {
        this.providerRepository = providerRepository;
        this.providerService = providerService;
        this.produitRepository = produitRepository;
    }


    @GetMapping("indexp")
    //@ResponseBody
    public String listProvidersByName(Model model, @RequestParam(name = "name", defaultValue = "") String name) {
        List<Provider> lp = this.providerService.getAllProviderByName(name);

        model.addAttribute("providers", lp);

        return "provider/listProviders";

    }

    @GetMapping("list")
    //@ResponseBody
    public String listProviders(Model model) {
        List<Provider> lp = this.providerService.getAllProvider();

        model.addAttribute("providers", lp);

        return "provider/listProviders";

    }

    @GetMapping("add")
    public String showAddProviderForm(Model model) {
        Provider provider = new Provider();// object dont la valeur des attributs par defaut
        model.addAttribute("provider", provider);
        return "provider/addProvider";
    }

    @PostMapping("add")
    public String addProvider(@Valid Provider provider, BindingResult result, @RequestParam("files") MultipartFile[] files) {
        if (result.hasErrors()) {
            return "provider/addProvider";
        }
        if (provider.getName() == "")
            provider.setName(null);

        // upload du ficher
        MultipartFile file = files[0];
        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());

        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Stockage du name du ficher dans la base
        StringBuilder fileName = new StringBuilder();
        fileName.append(file.getOriginalFilename());

        provider.setLogo(fileName.toString());

        this.providerService.persistProvider(provider);
        return "redirect:list";
    }


    @GetMapping("delete/{id}")
    public String deleteProvider(@PathVariable("id") long id, Model model) {


        //long id2 = 100L;

        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));

        //System.out.println("suite du programme...");

        providerRepository.delete(provider);

        //model.addAttribute("providers", providerRepository.findAll());
        //return "provider/listProviders";
        return "redirect:../list";
    }


    @GetMapping("edit/{id}")
    public String showProviderFormToUpdate(@PathVariable("id") long id, Model model) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));

        model.addAttribute("provider", provider);

        return "provider/updateProvider";
    }


    @PostMapping("update")
    public String updateProvider(@Valid Provider provider, BindingResult result, Model model,
                                 @RequestParam("files") MultipartFile[] files) {

        if (result.hasErrors()) {
    		/* Provider providerToUpdate = providerRepository.findById(provider.getId())
    		            .orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + provider.getId()));
    		        
    		        model.addAttribute("provider", providerToUpdate);*/
            return "provider/updateProvider";
        }
        if (!"".equals(files[0].getOriginalFilename())) {
            // upload du ficher
            MultipartFile file = files[0];
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());

            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Stockage du name du ficher dans la base
            StringBuilder fileName = new StringBuilder();
            fileName.append(file.getOriginalFilename());

            provider.setLogo(fileName.toString());
        }
        providerRepository.save(provider);
        return "redirect:list";

    }

    @GetMapping("show/{id}")
    public String showProduitsByIdProvider(@PathVariable("id") long id, Model model) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));
        List<Produit> produits = produitRepository.findProduitByProvider_Id(id);
        for (Produit p : produits)
            System.out.println("Produit = " + p.getLabel());

        model.addAttribute("produits", produits);
        model.addAttribute("provider", provider);
        return "provider/showProvider";
    }


}
