package com.example.TestIntegration;

import com.example.TestIntegration.dto.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String BASE_URL = "/api/users";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldFindAllUsers() throws Exception{
        List<User> users = List.of( new User("23445322","C","Juan","Camilo","López","Cortes","6012345678","kra 100 no 50-70","Bogotá D.C"),
                new User("1039454860","P","Santiago","Esteban","Amaya","Torres","6019475738","Calle 11 no 7-70","Bogotá D.C"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL)
                        .content(objectMapper.writeValueAsString(users))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindUserByIdAndType() throws Exception{
        User user = new User("23445322","C","Juan","Camilo","López","Cortes","6012345678","kra 100 no 50-70","Bogotá D.C");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL+"/"+user.getId()+"/"+user.getIdType())
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void getUserErrorEmptyId() throws Exception{

        var request = new User("","C","Juan","Camilo","López","Cortes","6012345678","kra 100 no 50-70","Bogotá D.C");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL+"/"+request.getId()+"/"+request.getIdType())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is4xxClientError());
    }

    @Test
    public void getUserErrorEmptyIdType() throws Exception{

        var request = new User("23445322","","Juan","Camilo","López","Cortes","6012345678","kra 100 no 50-70","Bogotá D.C");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL+"/"+request.getId()+"/"+request.getIdType())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getUserErrorWhenIdIsNotInURL() throws Exception{

        var request = new User("23445322","C","Juan","Camilo","López","Cortes","6012345678","kra 100 no 50-70","Bogotá D.C");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL+"/"+"/"+request.getIdType())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void getUserErrorWhenIdTypeIsNotInURL() throws Exception{

        var request = new User("23445322","C","Juan","Camilo","López","Cortes","6012345678","kra 100 no 50-70","Bogotá D.C");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL+"/"+request.getId())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

}
