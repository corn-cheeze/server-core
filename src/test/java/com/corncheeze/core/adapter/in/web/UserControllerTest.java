package com.corncheeze.core.adapter.in.web;

import com.corncheeze.core.application.port.in.UserService;
import com.corncheeze.core.domain.member.Member;
import com.corncheeze.core.domain.member.NewMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void Join_a_member() throws Exception {
        mockMvc.perform(post("/api/v1/members", defaultJoinContent()))
                .andExpect(status().isCreated());
    }

    @Test
    void Id_must_be_unique() throws Exception {
        Mockito.when(userService.join(defaultNewMember())).thenThrow(new IllegalArgumentException("Id already exists. current id is " + defaultId()));

        mockMvc.perform(post("/api/v1/members", defaultJoinContent()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Id already exists. current id is " + defaultId()));
    }

    @Test
    void Nickname_must_be_unique() throws Exception {
        Mockito.when(userService.join(defaultNewMember())).thenThrow(new IllegalArgumentException("Nickname already exists. current nickname is " + defaultNickname()));

        mockMvc.perform(post("/api/v1/members", defaultJoinContent()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Nickname already exists. current nickname is " + defaultNickname()));
    }

    private String defaultJoinContent() {
        return "{\"id\" : \"" + defaultId() + "\", \"password\" : \"" + defaultPassword() + "\", \"nickname\" : \"" + defaultNickname() + "\"}";
    }

    private MockHttpServletRequestBuilder post(String uri, String content) {
        return MockMvcRequestBuilders.post(uri)
                .contentType(defaultMediaType())
                .accept(defaultAccept())
                .characterEncoding(defaultCharset())
                .content(content);
    }

    private NewMember defaultNewMember() {
        return new NewMember(defaultBasicInfo(), defaultAdditionalInfo());
    }

    private Member.AdditionalInfo defaultAdditionalInfo() {
        return new Member.AdditionalInfo(defaultNickname());
    }

    private Member.BasicInfo defaultBasicInfo() {
        return new Member.BasicInfo(defaultId(), defaultPassword());
    }

    private String defaultId() {
        return "cc";
    }

    private String defaultPassword() {
        return "1q2w3e4r!@";
    }

    private String defaultNickname() {
        return "cheeze";
    }

    private MediaType defaultMediaType() {
        return MediaType.APPLICATION_JSON;
    }

    private MediaType defaultAccept() {
        return MediaType.APPLICATION_JSON;
    }

    private Charset defaultCharset() {
        return StandardCharsets.UTF_8;
    }
}