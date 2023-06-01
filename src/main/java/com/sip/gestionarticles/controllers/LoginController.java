package com.sip.gestionarticles.controllers;

import com.sip.gestionarticles.entities.*;
import com.sip.gestionarticles.repositoties.ProduitRepository;
import com.sip.gestionarticles.repositoties.ProviderRepository;
import com.sip.gestionarticles.repositoties.UserRepository;
import com.sip.gestionarticles.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    private final ProduitRepository articleRepository;
    private MessagingRepository messagingRepository;
    private ProviderRepository providerRepository;
    private UserRepository userRepository;

    public LoginController(UserService userService, ProduitRepository articleRepository, MessagingRepository messagingRepository, ProviderRepository providerRepository, UserRepository userRepository) {
        this.userService = userService;
        this.articleRepository = articleRepository;
        this.messagingRepository = messagingRepository;
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public ModelAndView accueil() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard/admin");

        ///
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println(user.getEmail() + " " + user.getName() + " " + user.getLastName());

        ////

        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }
   /* @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }*/

    @GetMapping("/403")
    public String reddirectError403() {
        return "redirect:../axessDenied";
    }

    @GetMapping("/axessDenied")
    public String error403() {
        return "/error/403";
    }

    @GetMapping("/admin")
    public String dashbaordAdmin(Model model) {
        return "dashboard/admin";
    }

    @GetMapping("/agent")
    public String dashbaordAgent(Model model) {
        return "dashboard/agent";
    }

    @GetMapping("/client")
    public String dashbaordAClient(Model model) {
        return "dashboard/client";
    }

    @GetMapping("/dashboard")
    public String dashbaord(Model model) {

/*

        ModelAndView modelAndView = new ModelAndView();
        long nbrArticles =  articleRepository.count();
        long nbrProviders =  providerRepository.count();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        //System.out.println(user.getEmail()+" "+user.getName()+" "+user.getLastName());
//        List<Produit> articles = articleRepository.findProductSoldOut();
//        long nbrSoldOutProduct =  articles.size();
        model.addAttribute("nbrArticles", nbrArticles);
        model.addAttribute("nbrProviders", nbrProviders);
//        model.addAttribute("nbrSoldOutProduct", nbrSoldOutProduct);
        model.addAttribute("users", userRepository.findAll());
        long nbrMsgNonLus = (messagingRepository.findAllMessageByStatus("Non lu",user)).size();
        model.addAttribute("nbrMsgNonLus", nbrMsgNonLus );
        // System.out.println(nbrMsgNonLus);
        model.addAttribute("articles", articleRepository.findAll());

        List<Messaging> messagesUnread = messagingRepository.findAllMessageByStatus("Non lu",user);


        model.addAttribute("messagesUnread", messagesUnread);
        modelAndView.setViewName("dashboard/dashboard");
        return modelAndView;*/


        //1-Récuparation de la session du user Connecté <<Authentication>>
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //2-Récupéartion du User
        User user = userService.findUserByEmail(auth.getName());

        //System.out.println(user.toString());
        model.addAttribute("user", user);
        //3-Récupération des roles du user
        Set<Role> userRoles = user.getRoles();
        //4-Conversion du set vers tableau pour la récupération du premier role
        Object roles[] = userRoles.toArray();
        //System.out.println(roles[0].toString()); // On suppose qu'on a un seul role par user
        //5-Récupéation du rôle : userRole
        Role role = (Role) roles[0];
        String userRole = role.getRole();
        //System.out.println(userRole);


        switch (userRole) {
            case "SUPERADMIN":
                return "dashboard/admin";
            case "ADMIN":
                return "dashboard/agent";
            case "AGENT":
                return "dashboard/client";
            default:
                return "dashboard/index";
        }


    }
}



