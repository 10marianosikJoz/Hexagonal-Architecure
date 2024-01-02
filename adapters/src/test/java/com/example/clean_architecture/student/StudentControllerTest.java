package com.example.clean_architecture.student;

import com.example.clean_architecture.AbstractContainerBaseTest;
import org.hamcrest.CoreMatchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class StudentControllerTest extends AbstractContainerBaseTest {

    @MockBean
    private StudentFacade studentFacade;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldSaveGivenStudent() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                                        .post("/students")
                                        .content(prepareJSONData())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
        response.andExpect(MockMvcResultMatchers.jsonPath("firstName.['value']", CoreMatchers.is("John")));
    }

    @Test
    void shouldReturnStudentWithGivenId() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                              .get("/students/1")).andDo(print());

        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("firstName.['value']", CoreMatchers.is("Shane")));
    }

    @Test
    void shouldDeleteStudentWithGivenId() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                              .delete("/students/2")).andDo(print());

        response.andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    void updateTeacherWithGivenId() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                              .put("/students/2").with(csrf())
                              .content(prepareJSONData())
                              .contentType(MediaType.APPLICATION_JSON)
                              .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    void shouldReturnAllStudentsFromDatabase() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                              .get("/students")).andDo(print());

        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(5)));
    }

    private String prepareJSONData() throws JSONException {
        return new JSONObject()
                .put("firstName", "John")
                .put("lastName", "Murphy")
                .put("email", "john@gmail.com")
                .put("active", "ACTIVE")
                .toString();
    }
}