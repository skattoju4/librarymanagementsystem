package com.knf.dev.librarymanagementsystem.service.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.opencsv.ICSVWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.knf.dev.librarymanagementsystem.constant.Item;
import com.knf.dev.librarymanagementsystem.service.AuthorService;
import com.knf.dev.librarymanagementsystem.service.BookService;
import com.knf.dev.librarymanagementsystem.service.CategoryService;
import com.knf.dev.librarymanagementsystem.service.FileService;
import com.knf.dev.librarymanagementsystem.service.PublisherService;
import com.knf.dev.librarymanagementsystem.util.Mapper;
import com.knf.dev.librarymanagementsystem.vo.AuthorRecord;
import com.knf.dev.librarymanagementsystem.vo.BookRecord;
import com.knf.dev.librarymanagementsystem.vo.CategoryRecord;
import com.knf.dev.librarymanagementsystem.vo.PublisherRecord;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@Service
public class FileServiceImpl implements FileService {

	final BookService bookService;

	final AuthorService authorService;

	final PublisherService publisherService;

	final CategoryService categoryService;

	static final String FILE_NOT_PRESENT = "FILE_IS_NOT_PRESENT";

	public FileServiceImpl(BookService bookService, AuthorService authorService, PublisherService publisherService,
			CategoryService categoryService) {
		this.authorService = authorService;
		this.categoryService = categoryService;
		this.publisherService = publisherService;
		this.bookService = bookService;
	}

	@Override
	public void exportCSV(String fileName, HttpServletResponse response)
			throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		var item = Item.getItemByValue(fileName);
		response.setContentType("text/csv");
		if (item.isPresent()) {
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + item.get().getFileName() + "\"");
			switch (item.get()) {
			case BOOK:
				StatefulBeanToCsv<BookRecord> writer1 = getWriter(response.getWriter());
				writer1.write(Mapper.bookModelToVo(bookService.findAllBooks()));
				break;
			case AUTHOR:
				StatefulBeanToCsv<AuthorRecord> writer2 = getWriter(response.getWriter());
				writer2.write(Mapper.authorModelToVo(authorService.findAllAuthors()));
				break;
			case CATEGORY:
				StatefulBeanToCsv<CategoryRecord> writer3 = getWriter(response.getWriter());
				writer3.write(Mapper.categoryModelToVo(categoryService.findAllCategories()));
				break;
			case PUBLISHER:
				StatefulBeanToCsv<PublisherRecord> writer4 = getWriter(response.getWriter());
				writer4.write(Mapper.publisherModelToVo(publisherService.findAllPublishers()));
				break;
			}
		} else {
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + FILE_NOT_PRESENT + "\"");
		}

	}

	private static <T> StatefulBeanToCsv<T> getWriter(PrintWriter printWriter) {
		return new StatefulBeanToCsvBuilder<T>(printWriter).withQuotechar(ICSVWriter.NO_QUOTE_CHARACTER)
				.withSeparator(ICSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false).build();
	}
}
