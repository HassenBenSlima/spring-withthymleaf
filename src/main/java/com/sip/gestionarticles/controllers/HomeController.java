package com.sip.gestionarticles.controllers;

import com.sip.gestionarticles.entities.Produit;
import com.sip.gestionarticles.repositoties.ProduitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    private final ProduitRepository produitRepository;

    public HomeController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @RequestMapping(value = {"/", "h**"})
    //@ResponseBody
    public String home() {
        return "admin";
    }

//    public String login() {
//        return "";
//    }
//
//    public String registration() {
//        return "";
//    }
//
//    public String forgotpassword() {
//        return "";
//    }


}
