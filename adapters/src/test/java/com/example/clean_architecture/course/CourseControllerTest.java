package com.example.clean_architecture.course;

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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class CourseControllerTest extends AbstractContainerBaseTest {

    @MockBean
    private CourseFacade courseFacade;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldSaveGivenCourse() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                              .post("/courses")
                              .content(prepareJSONData())
                              .contentType(MediaType.APPLICATION_JSON)
                              .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.jsonPath("name['value']", CoreMatchers.is("Java course")));
    }

    @Test
    void shouldReturnCourseWithGivenId() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                              .get("/courses/2")).andDo(print());

        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("description['value']", CoreMatchers.is("Advanced Course")));
    }

    @Test
    void shouldDeleteCourseWithGivenId() throws Exception {

        var response = mockMvc.perform(MockMvcRequestBuilders
                              .delete("/courses/2")).andDo(print());

        response.andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    void shouldReturnAllCoursesFromDatabase() throws Exception {

        var response = mockMvc.perform(MockMvcRequestBuilders
                              .get("/courses")).andDo(print());

        response.andExpect(MockMvcResultMatchers.status().is(200));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(5)));
    }

    @Test
    void studentShouldBeAbleToEnrollCourse() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                              .post("/courses/1/student/1"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String prepareJSONData() throws JSONException {
        return new JSONObject()
                .put("name", "Java course")
                .put("description", "Course for beginners")
                .put("participantLimit", 100)
                .put("participantNumber", 0)
                .put("status", "ACTIVE")
                .toString();
    }
}