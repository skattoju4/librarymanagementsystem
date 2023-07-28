package com.knf.dev.librarymanagementsystem.controller;

import java.net.http.HttpRequest;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.knf.dev.librarymanagementsystem.entity.Author;
import com.knf.dev.librarymanagementsystem.service.AuthorService;

@Controller
public class AuthorController {

	final AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@RequestMapping(path = "/authors", method = RequestMethod.GET)
	public String findAllAuthors(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		var currentPage = page.orElse(1);
		var pageSize = size.orElse(5);
		var bookPage = authorService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("authors", bookPage);

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			var pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "list-authors";
	}

	@RequestMapping(value = "/author/{id}", method = RequestMethod.GET)
	public String findAuthorById(@PathVariable("id") Long id, Model model) {

		model.addAttribute("author", authorService.findAuthorById(id));
		return "list-author";
	}

	@RequestMapping(value = "/addAuthor", method = RequestMethod.GET)
	public String showCreateForm(Author author) {
		return "add-author";
	}

	@RequestMapping(value = "/add-author",method = RequestMethod.POST)
	public String createAuthor(Author author, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-author";
		}

		authorService.createAuthor(author);
		model.addAttribute("author", authorService.findAllAuthors());
		return "redirect:/authors";
	}

	@RequestMapping(value = "/updateAuthor/{id}",method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute("author", authorService.findAuthorById(id));
		return "update-author";
	}

	@RequestMapping(value = "/update-author/{id}",method = RequestMethod.POST)
	public String updateAuthor(@PathVariable("id") Long id, Author author, BindingResult result, Model model) {
		if (result.hasErrors()) {
			author.setId(id);
			return "update-author";
		}

		authorService.updateAuthor(author);
		model.addAttribute("author", authorService.findAllAuthors());
		return "redirect:/authors";
	}

	@RequestMapping(value = "/remove-author/{id}",method = RequestMethod.GET)
	public String deleteAuthor(@PathVariable("id") Long id, Model model) {
		authorService.deleteAuthor(id);

		model.addAttribute("author", authorService.findAllAuthors());
		return "redirect:/authors";
	}

}
