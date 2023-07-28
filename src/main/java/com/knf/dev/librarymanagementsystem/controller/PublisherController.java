package com.knf.dev.librarymanagementsystem.controller;

// Import statements...

@Controller
public class PublisherController {
    final PublisherService publisherService;
    private static final String REDIRECT_PUBLISHERS = "redirect:/publishers";

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    // Existing methods...

    @PostMapping("/add-publisher")
    public String createPublisher(Publisher publisher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-publisher";
        }
        publisherService.createPublisher(publisher);
        model.addAttribute("publisher", publisherService.findAllPublishers());
        return REDIRECT_PUBLISHERS;
    }

    // Existing methods...

    @PostMapping("/update-publisher/{id}")
    public String updatePublisher(@PathVariable("id") Long id, Publisher publisher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            publisher.setId(id);
            return "update-publishers";
        }
        publisherService.updatePublisher(publisher);
        model.addAttribute("publisher", publisherService.findAllPublishers());
        return REDIRECT_PUBLISHERS;
    }

    // Existing methods...

    @GetMapping("/remove-publisher/{id}")
    public String deletePublisher(@PathVariable("id") Long id, Model model) {
        publisherService.deletePublisher(id);
        model.addAttribute("publisher", publisherService.findAllPublishers());
        return REDIRECT_PUBLISHERS;
    }
}
