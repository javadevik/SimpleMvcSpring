package com.ua.controller;

import com.ua.dao.PersonDao;
import com.ua.dao.PersonDaoException;
import com.ua.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDao personDao;

    @Autowired
    public PeopleController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping
    public String allPeople(Model model) {
        model.addAttribute("people", personDao.findAll());
        return "people/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Person person = null;
        try {
            person = personDao.findById(id);
        } catch (PersonDaoException e) {
            model.addAttribute("message", e.getMessage());
            return "exceptions";
        }
        model.addAttribute("person", person);
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";
        personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") int id, Model model) {
        Person personToUpdate;
        try {
            personToUpdate = personDao.findById(id);
            model.addAttribute(personToUpdate);
        } catch (PersonDaoException e) {
            model.addAttribute("message", e.getMessage());
            return "exceptions";
        }
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/edit";
        personDao.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        try {
            personDao.delete(id);
        } catch (PersonDaoException e) {
            model.addAttribute("message", e.getMessage());
            return "exceptions";
        }
        return "redirect:/people";
    }
}
