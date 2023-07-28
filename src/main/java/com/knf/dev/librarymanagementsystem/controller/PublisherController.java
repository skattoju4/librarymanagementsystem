package com.knf.dev.librarymanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.knf.dev.librarymanagementsystem.dto.PublisherDTO;
import com.knf.dev.librarymanagementsystem.service.PublisherService;

@Controller
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/publishers")
    public String findAllPublishers(Model model) {
        model.addAttribute("publishers", publisherService.findAllPublishers());
        return "list-publishers";
    }

    @GetMapping("/publisher/{id}")
    public String findPublisherById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("publisher", publisherService.findPublisherById(id));
        return "list-publisher";
    }

    @GetMapping("/addPublisher")
    public String showCreateForm(PublisherDTO publisherDTO) {
        return "add-publisher";
    }

    @PostMapping("/add-publisher")
    public String createPublisher(@ModelAttribute("publisherDTO") PublisherDTO publisherDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-publisher";
        }
        publisherService.createPublisher(publisherDTO);
        model.addAttribute("publishers", publisherService.findAllPublishers());
        return "redirect:/publishers";
    }

    @GetMapping("/updatePublisher/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("publisherDTO", publisherService.findPublisherDTOById(id));
        return "update-publisher";
    }

    @PostMapping("/update-publisher/{id}")
    public String updatePublisher(@PathVariable("id") Long id, @ModelAttribute("publisherDTO") PublisherDTO publisherDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            publisherDTO.setId(id);
            return "update-publisher";
        }
        publisherService.updatePublisher(publisherDTO);
        model.addAttribute("publishers", publisherService.findAllPublishers());
        return "redirect:/publishers";
    }

    @GetMapping("/remove-publisher/{id}")
    public String deletePublisher(@PathVariable("id") Long id, Model model) {
        publisherService.deletePublisher(id);
        model.addAttribute("publishers", publisherService.findAllPublishers());
        return "redirect:/publishers";
    }
}
