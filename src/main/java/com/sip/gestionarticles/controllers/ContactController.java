package com.sip.gestionarticles.controllers;

import com.sip.gestionarticles.entities.Contact;
import com.sip.gestionarticles.repositoties.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/contact/")
public class ContactController {

    static String trouve = null;

    private final ContactRepository contactRepository;

    @Autowired
    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    @GetMapping("list")
    public String listContact(Model model) {
        List<Contact> c = (List<Contact>) contactRepository.findAll();
        if (c.size() == 0) {
            c = null;
        }
        model.addAttribute("contacts", c);
        return "contact/listContacts";
    }


    @GetMapping("add")
    public String showContact(Model model) {

        model.addAttribute("contact", new Contact());
        return "contact/addContact";
    }

    @PostMapping("add")
    //@ResponseBody
    public String addContact(@Valid Contact contact, BindingResult result
    ) {
        contactRepository.save(contact);
        return "redirect:list";
    }

    @GetMapping("delete/{id}")
    public String deleteProduit(@PathVariable("id") long id, Model model) {

        Optional<Contact> contact = contactRepository.findById(id);

        if (contact.isPresent()) {
            contactRepository.delete(contact.get());
            trouve = "existe";
        } else {  // le problème
            trouve = "inexiste";

        }
        model.addAttribute("trouve", trouve);

        List<Contact> lp = (List<Contact>) contactRepository.findAll();
        if (lp.size() == 0)
            lp = null;
        model.addAttribute("contacts", lp);
        return "contact/listContacts";
//      return "redirect:../list";
    }


}
