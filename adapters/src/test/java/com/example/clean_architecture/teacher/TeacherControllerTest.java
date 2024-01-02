package com.example.clean_architecture.teacher;

import com.example.clean_architecture.AbstractContainerBaseTest;
import org.hamcrest.CoreMatchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class TeacherControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private TeacherFacade teacherFacade;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TeacherQueryRepository teacherQueryRepository;

    @Test
    void shouldSaveGivenTeacher() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                              .post("/teachers")
                              .content(prepareJSONData())
                              .contentType(MediaType.APPLICATION_JSON)
                              .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
        response.andExpect(MockMvcResultMatchers.jsonPath("firstName['value']", CoreMatchers.is("John")));
    }

    @Test
    void shouldReturnTeacherWithGivenId() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                              .get("/teachers/4")).andDo(print());

        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("firstName['value']", CoreMatchers.is("Bud")));
    }

    @Test
    void shouldDeleteStudentWithGivenId() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                              .delete("/teachers/4")).andDo(print());

        response.andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    void updateTeacherWithGivenId() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                              .put("/teachers/2")
                              .content(prepareJSONData())
                              .contentType(MediaType.APPLICATION_JSON)
                              .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    void shouldReturnAllTeachersFromDatabase() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                              .get("/teachers")).andDo(print());

        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(5)));
    }

    private String prepareJSONData() throws JSONException {
        return new JSONObject()
                .put("teacherId","20")
                .put("firstName", "John")
                .put("lastName", "Murphy")
                .put("degree", "Master degree")
                .toString();
    }
}