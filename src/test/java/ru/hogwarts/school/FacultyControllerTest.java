package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getFacultyTest() throws Exception {

        Long id = 100L;
        String name = "testName";
        String color = "testColor";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setColor(color);
        faculty.setName(name);

        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void getFacultyByColorOrNameTest() throws Exception {

        Long id1 = 100L;
        String name1 = "Griffindor";
        String color1 = "Red";
        String color1IgnoreCase = "RED";

        Long id2 = 200L;
        String name2 = "Slizerin";
        String name2IgnoreCase = "SlizEriN";
        String color2 = "Green";

        Faculty facultyOne = new Faculty();
        facultyOne.setId(id1);
        facultyOne.setColor(color1);
        facultyOne.setName(name1);

        Faculty facultyTwo = new Faculty();
        facultyTwo.setId(id2);
        facultyTwo.setColor(color2);
        facultyTwo.setName(name2);

        when(facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name2IgnoreCase,color1IgnoreCase)).thenReturn(Set.of(facultyTwo,facultyOne));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .queryParam("color", color1IgnoreCase)
                        .queryParam("name", name2IgnoreCase)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(facultyOne, facultyTwo))));
    }

    @Test
    public void deleteFacultyTest() throws Exception {

        Long id = 1L;
        String name = "testName";
        String color = "testColor";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setColor(color);
        faculty.setName(name);

        when(facultyRepository.getById(id)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
        verify(facultyRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    public void postFacultyTest() throws Exception {

        Long id = 1L;
        String name = "testName";
        String color = "testColor";

        JSONObject facultyObj = new JSONObject();
        facultyObj.put("name", name);
        facultyObj.put("color", color);


        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setColor(color);
        faculty.setName(name);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .contentType(facultyObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void putFacultyTest() throws Exception {

        Long id = 1L;
        String oldName = "testName";
        String oldColor = "testColor";

        String newName = "testName2";
        String newColor = "testColor2";

        JSONObject facultyObj = new JSONObject();
        facultyObj.put("id", id);
        facultyObj.put("name", newName);
        facultyObj.put("color", newColor);


        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setColor(oldColor);
        faculty.setName(oldName);

        Faculty updatedFaculty = new Faculty();
        faculty.setId(id);
        faculty.setColor(newColor);
        faculty.setName(newName);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .contentType(facultyObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.color").value(newColor));
    }

}
