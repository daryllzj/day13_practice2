package ssf.workshop13_attempt2.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Component
public class Contact {

    @NotNull(message = "mandatory")
    @Size(min=1, message = "more than 1")
    private String name;

    @NotNull(message = "mandatory")
    @Email(message = "invalid email")
    private String email;

    @NotNull(message = "mandatory")
    @Size(min=7, message = "more than 7")
    private String phoneNumber;

    @NotNull(message = "mandatory")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Min(value = 10, message = "Must be above 10 years old")
    @Max(value = 100, message = "Must be below 100 years old")
    private int age;

    private String id;

    public Contact() {
        this.id = this.generateId();
    }

    public Contact(@NotNull(message = "mandatory") @Size(min = 1, message = "more than 1") String name,
            @NotNull(message = "mandatory") @Email(message = "invalid email") String email,
            @NotNull(message = "mandatory") @Size(min = 7, message = "more than 7") String phoneNumber,
            @NotNull(message = "mandatory") @Past LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.id = this.generateId();

    }

    


    public Contact(@NotNull(message = "mandatory") @Size(min = 1, message = "more than 1") String name,
            @NotNull(message = "mandatory") @Email(message = "invalid email") String email,
            @NotNull(message = "mandatory") @Size(min = 7, message = "more than 7") String phoneNumber,
            @NotNull(message = "mandatory") @Past LocalDate dateOfBirth, String id) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.id = this.generateId();
    }

    private String generateId(){
        String generateID = "";

        Random rand = new Random();

        while (generateID.length()<8) {
            generateID += Integer.toHexString(rand.nextInt(17)).toString();
        }
        return generateID;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        int calculatedAge = 0;
        if ((dateOfBirth != null)) {
            calculatedAge = Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        this.age = calculatedAge;
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Contact [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", dateOfBirth="
                + dateOfBirth + ", age=" + age + ", id=" + id + "]";
    }

    

    
}
