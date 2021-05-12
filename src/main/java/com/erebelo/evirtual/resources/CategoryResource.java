package com.erebelo.evirtual.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.erebelo.evirtual.domain.Category;

@RestController
@RequestMapping(value = "category")
public class CategoryResource {

	@RequestMapping(method = RequestMethod.GET)
	public List<Category> list() {
		Category c1 = new Category(1, "Computing");
		Category c2 = new Category(2, "Office");

		List<Category> categoryList = Arrays.asList(c1, c2);

		return categoryList;
	}
}
