package com.todotic.contactlistapi.Controller;

import com.todotic.contactlistapi.entity.Contact;
import com.todotic.contactlistapi.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("/api/contacts")
@RestController
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;
    @GetMapping
    public Iterable<Contact> list() {
        return contactRepository.findAll();
    }

   @GetMapping("{id}")
    public Contact get(@PathVariable Integer id){
        return contactRepository
                .findById(id)
                .orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Contact create(@RequestBody Contact contact) {
        contact.setCreatedAt(LocalDateTime.now());
        return contactRepository
                .save(contact);
    }

    @PutMapping("{id}")
    public Contact update(@PathVariable Integer id,
                          @RequestBody Contact form ) {

        Contact contactFromDb = this.get(id);
        contactFromDb.setName(form.getName());
        contactFromDb.setEmail(form.getEmail());

        return contactRepository
                .save(contactFromDb);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id ) {

        Contact contactFromDb = this.get(id);
        contactRepository
                .delete(contactFromDb);

    }

}
