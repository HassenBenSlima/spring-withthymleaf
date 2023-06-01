package com.sip.gestionarticles.controllers;

import com.sip.gestionarticles.entities.Produit;
import com.sip.gestionarticles.entities.Provider;
import com.sip.gestionarticles.repositoties.ProduitRepository;
import com.sip.gestionarticles.repositoties.ProviderRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/produit/")
public class ProduitController {

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
    static String trouve = null;

    private final ProduitRepository produitRepository;
    private final ProviderRepository providerRepository;

    @Autowired
    public ProduitController(ProduitRepository produitRepository, ProviderRepository providerRepository) {
        this.produitRepository = produitRepository;
        this.providerRepository = providerRepository;
    }


    @GetMapping("list")
    public String listProviders(Model model) {
        List<Produit> lp = (List<Produit>) produitRepository.findAll();
        if (lp.size() == 0) {
            lp = null;
        }
        model.addAttribute("produits", lp);
        return "produit/listProduits";
    }

    @GetMapping("count")
    public String numbreOfProduct(Model model) {
        Long numbreOfProduct =produitRepository.count();
        model.addAttribute("countProduct", numbreOfProduct);
        return "produit/listProduits";
    }

    @GetMapping("index")
    public String search(Model model,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        List<Produit> lp = produitRepository.findProduitByLabelContains(keyword);
        if (lp.size() == 0) {
            lp = new ArrayList<>();
        }
        model.addAttribute("produits", lp);
        return "produit/listProduits";
    }

    @GetMapping("add")
    public String showAddProduitForm(Model model) {

        model.addAttribute("providers", providerRepository.findAll());
        model.addAttribute("produit", new Produit());
        return "produit/addProduit";
    }

    @PostMapping("add")
    //@ResponseBody
    public String addProduit(@Valid Produit produit, BindingResult result, @RequestParam(name = "providerId", required = true) Long p,
                             @RequestParam("files") MultipartFile[] files
    ) {
        System.out.println(files);
        Provider provider = providerRepository.findById(p)
                .orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + p));
        produit.setProvider(provider);
        /// part upload
        if (files.length >= 0) {
            // upload du ficher filePhotoFace 1
            MultipartFile filePhotoFace = files[0];
            Path fileNameAndPath = Paths.get(uploadDirectory, filePhotoFace.getOriginalFilename());

            try {
                Files.write(fileNameAndPath, filePhotoFace.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Stockage du name du ficher dans la base
            StringBuilder nameFilePhotoFace = new StringBuilder();
            nameFilePhotoFace.append(filePhotoFace.getOriginalFilename());

            produit.setPhotoFace(nameFilePhotoFace.toString());
        }

        if (files.length >= 1) {
            // upload du ficher filePhotoFace 2
            MultipartFile filePhotoProfil = files[1];
            Path fileNameAndPath2 = Paths.get(uploadDirectory, filePhotoProfil.getOriginalFilename());

            try {
                Files.write(fileNameAndPath2, filePhotoProfil.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Stockage du name du ficher dans la base
            StringBuilder fileNamePhotoProfil = new StringBuilder();
            fileNamePhotoProfil.append(filePhotoProfil.getOriginalFilename());

            produit.setPhotoProfil(fileNamePhotoProfil.toString());
        }

        System.out.println(produit);


        produitRepository.save(produit);
        return "redirect:list";
    }

    @GetMapping("delete/{id}")
    public String deleteProduit(@PathVariable("id") long id, Model model) {

        Optional<Produit> produit = produitRepository.findById(id);

        if (produit.isPresent()) {
            produitRepository.delete(produit.get());
            trouve = "existe";
        } else {  // le problème
            trouve = "inexiste";

        }
        model.addAttribute("trouve", trouve);

        List<Produit> lp = (List<Produit>) produitRepository.findAll();
        if (lp.size() == 0)
            lp = null;
        model.addAttribute("produits", lp);
        return "produit/listProduits";
//      return "redirect:../list";
    }

    @GetMapping("edit/{id}")
    public String showArticleFormToUpdate(@PathVariable("id") long id, Model model) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));

        model.addAttribute("produit", produit);
        model.addAttribute("providers", providerRepository.findAll());
        model.addAttribute("idProvider", produit.getProvider().getId());

        return "produit/updateProduit";
    }

    @PostMapping("edit")
    public String updateProduit(@Valid Produit produit, BindingResult result,
                                Model model, @RequestParam(name = "providerId", required = false) Long p,
                                @RequestParam("files") MultipartFile[] files) {
        if (result.hasErrors()) {

            return "produit/updateProduit";
        }

        /// part upload
        if (files.length >= 0 && !"".equals(files[0].getOriginalFilename())) {
            // upload du ficher filePhotoFace 1
            MultipartFile filePhotoFace = files[0];
            Path fileNameAndPath = Paths.get(uploadDirectory, filePhotoFace.getOriginalFilename());

            try {
                Files.write(fileNameAndPath, filePhotoFace.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Stockage du name du ficher dans la base
            StringBuilder nameFilePhotoFace = new StringBuilder();
            nameFilePhotoFace.append(filePhotoFace.getOriginalFilename());

            produit.setPhotoFace(nameFilePhotoFace.toString());
        }

        if (files.length >= 1 && !("".equals(files[1].getOriginalFilename()))) {
            // upload du ficher filePhotoFace 2
            MultipartFile filePhotoProfil = files[1];
            Path fileNameAndPath2 = Paths.get(uploadDirectory, filePhotoProfil.getOriginalFilename());

            try {
                Files.write(fileNameAndPath2, filePhotoProfil.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Stockage du name du ficher dans la base
            StringBuilder fileNamePhotoProfil = new StringBuilder();
            fileNamePhotoProfil.append(filePhotoProfil.getOriginalFilename());

            produit.setPhotoProfil(fileNamePhotoProfil.toString());
        }

        Provider provider = providerRepository.findById(p)
                .orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + p));
        produit.setProvider(provider);

        produitRepository.save(produit);
        return "redirect:list";
    }

    @GetMapping("show/{id}")
    public String showProduitDetails(@PathVariable("id") long id, Model model) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));

        model.addAttribute("produit", produit);

        return "produit/showProduit";
    }


}
