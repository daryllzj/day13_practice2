package ssf.workshop13_attempt2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ssf.workshop13_attempt2.model.Contact;
import ssf.workshop13_attempt2.repo.ContactRepo;

@Controller
@RequestMapping(path = "form")
public class ContactController {

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    ApplicationArguments applicationArguments;

    @Value("data")
    private String dataDir;

    @GetMapping
    public String showForm(Model model) {

        Contact contact = new Contact();

        model.addAttribute("contact", contact);

        return "form";
        
    }

    @PostMapping(path = "/display")
    public String showContactSummary(@Valid Contact contact, BindingResult binding, Model model) {

        if (binding.hasErrors()) {
            return "form";
        }

        contactRepo.saveContact(contact, model, applicationArguments, dataDir);

        return "contactSummary";
    }

    @GetMapping(path = "{contactID}")
    public String findContactViaID(Model model, @PathVariable String contactID) {
        
        contactRepo.findContactViaID(model, contactID, applicationArguments, dataDir);

        return "contactSummary";
    }


    
}
