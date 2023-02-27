package ssf.workshop13_attempt2.repo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import ssf.workshop13_attempt2.model.Contact;

@Repository
public class ContactRepo {

    public void saveContact(Contact contact, Model model, ApplicationArguments appArgs, String defaultDataDir) {
        String dataFilename = contact.getId();
        PrintWriter prntWriter = null;
        try {
            FileWriter fileWriter = new FileWriter(checkOrCreateDir(appArgs, defaultDataDir) + "/" + dataFilename);
            prntWriter = new PrintWriter(fileWriter);
            prntWriter.println(contact.getName());
            prntWriter.println(contact.getEmail());
            prntWriter.println(contact.getPhoneNumber());
            prntWriter.println(contact.getDateOfBirth().toString());
            prntWriter.println(contact.getId());
            prntWriter.close();
        } catch (IOException e) {
            System.err.println(e);
        }

        model.addAttribute("contact", 
        new Contact(contact.getName(), 
        contact.getEmail(), 
        contact.getPhoneNumber(),
        contact.getDateOfBirth(),
        contact.getId()
        ));
    }

    private String checkOrCreateDir(ApplicationArguments appArgs, String defaultDataDir) {
        String directoryName = "";

        Set<String> opsNames = appArgs.getOptionNames();
        String[] optNamesArr = opsNames.toArray(new String[opsNames.size()]);

        List<String> optValues = null;
        String[] optValuesArr = null;

        if (optNamesArr.length > 0) {
            optValues = appArgs.getOptionValues(optNamesArr[0]);
            optValuesArr = optValues.toArray(new String[optValues.size()]);
            directoryName = optValuesArr[0];
            
        } else{
            directoryName = defaultDataDir;
        }

        System.out.println(directoryName);

        Path newDirPath = Paths.get(directoryName);
            
        File newDir = newDirPath.toFile();
        System.out.println(newDir);

        if (newDir.exists()) {
            System.out.println("Directory already exists. Information stored in default directory.");
        } else {
            System.out.println("Directory does not exists. New directory created.");
            newDir.mkdir();
        }

        return directoryName;

    }

    public void findContactViaID(Model model, String contactID, ApplicationArguments applicationArguments, String defaultDir) {
        Contact contact = new Contact();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            // get the file path of the directory
            Path filePath = new File(checkOrCreateDir(applicationArguments, defaultDir) + "/" + contactID).toPath();

            // set the charset format when reading the file
            Charset charset = Charset.forName("UTF-8");
            List<String> stringList = Files.readAllLines(filePath, charset);
            contact.setId(contactID);
            contact.setName(stringList.get(0));
            contact.setEmail(stringList.get(1));
            contact.setPhoneNumber(stringList.get(2));
            LocalDate dob = LocalDate.parse(stringList.get(3), formatter);
            contact.setDateOfBirth(dob);
            contact.setId(contactID);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Contact info not found");
        }

        model.addAttribute("contact", contact);
    }

    
    
}
