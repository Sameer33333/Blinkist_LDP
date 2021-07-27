package com.training.blinkist;



import com.training.blinkist.exceptions.BookAlreadyPresentException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "classpath: test_data.sql")
class BookControllerTest {


    @Autowired
    private MockMvc mockMvc;



    @Test
    void addNewBookTest() throws Exception {
        String bookJson = "{\"TestBookTitle\":\"TestAuthor\"TestLanguage,\"genreId\":2,\"TestGenreName\":\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/blinkist/books")
                .accept(MediaType.APPLICATION_JSON).content(bookJson)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andExpect(
                MockMvcResultMatchers.jsonPath("$.bookTitle", Matchers.equalTo("TestBookTitle")));

    }

    @Test
    void addNewBookFailAlreadyExistsTest() throws Exception {
        String bookJson = "{}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/blinkist/books")
                .accept(MediaType.APPLICATION_JSON).content(bookJson)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookAlreadyPresentException));
    }


}