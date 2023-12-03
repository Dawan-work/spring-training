package fr.dawan.training.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dawan.training.dto.CategoryDto;
import fr.dawan.training.services.ICategoryService;

@SpringBootTest
public class CategoryControllerTest {

	@Autowired
	private CategoryController categoryController;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void contextLoads() {
		assertThat(categoryController).isNotNull();
	}
	
	private static List<CategoryDto> categories;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// remplir categories
		categories = new ArrayList<>();
		categories.add(new CategoryDto(1L, 0, "cat1"));
		categories.add(new CategoryDto(2L, 0, "cat2"));
	}
	
	@Test
	void testGetAllBy() throws Exception {
		
		when(categoryService.getAllBy(1, 2, null)).thenReturn(categories);
		
		//ICI, ON TESTE LA METHODE EN PASSANT PAR LE CONTROLEUR
		//List<CategoryDto> result = categoryController.getAllBy(1, 2, null);
		//assertEquals("cat1", result.get(0).getName());
		
		//On test via un GET URL
		mockMvc.perform(get("/api/categories/1/2").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(2)))
			.andExpect(jsonPath("$[0].name",is("cat1")));
		
	}
	
	@Test
	void testGetById() throws Exception {
		
		//créer un bouchon
		when(categoryService.getById(1L)).thenReturn(categories.get(0));
		
		//ICI, ON TESTE LA METHODE EN PASSANT PAR LE CONTROLEUR
		//List<CategoryDto> result = categoryController.getAllBy(1, 2, null);
		//assertEquals("cat1", result.get(0).getName());
		
		//On test via un GET URL et on récupère le JSON résultat
		String jsonResult = mockMvc.perform(get("/api/categories/1").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		
		//objectMapper de Jackson pour convertir json <> Object
		CategoryDto cDto = objectMapper.readValue(jsonResult, CategoryDto.class);
		//les assertions
		assertEquals("cat1", cDto.getName());
		
	}
	
}
