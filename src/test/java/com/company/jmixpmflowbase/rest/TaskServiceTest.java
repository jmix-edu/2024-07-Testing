package com.company.jmixpmflowbase.rest;

import com.company.jmixpmflowbase.JmixpmFlowBaseApplication;
import com.company.jmixpmflowbase.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = JmixpmFlowBaseApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class TaskServiceTest {

    @Autowired
    private MockMvc mockMvc;

    private final RestTemplateBuilder builder = new RestTemplateBuilder();

    //values from application.properties for auth server request
    @Value("${spring.security.oauth2.authorizationserver.client.myclient.registration.client-id}")
    private String client;
    @Value("${spring.security.oauth2.authorizationserver.client.myclient.registration.client-secret}")
    private String secret;

    private String getAccessToken() throws Exception {
        String bodyString = "grant_type=client_credentials";
        String encoding = Base64.getEncoder().encodeToString((client + ":" + secret.substring(6)).getBytes());
        String resultString = mockMvc.perform(post(URI.create("http://localhost:8080/oauth2/token"))
                        .header("Authorization", "Basic " + encoding)
                        .content(bodyString)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void getLeastBusyUserFromTaskService() throws Exception {
        User user = builder.defaultHeader("Authorization", "Bearer " + getAccessToken())
                .build()
                .getForEntity("http://localhost:8080/rest/services/jmixpmflowbase_TaskService/findLeastBusyUser", User.class)
                .getBody();

        Assertions.assertEquals("dev1", user.getUsername());
    }


}
