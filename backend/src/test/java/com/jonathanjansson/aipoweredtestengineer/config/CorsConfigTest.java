package com.jonathanjansson.aipoweredtestengineer.config;

import com.jonathanjansson.aipoweredtestengineer.project.ProjectController;
import com.jonathanjansson.aipoweredtestengineer.project.ProjectService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = ProjectController.class,
        properties = "app.cors.allowed-origin=http://frontend.test"
)
@Import(CorsConfig.class)
class CorsConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST", "PUT", "DELETE", "OPTIONS"})
    void allowsConfiguredOriginAndApiMethods(String method) throws Exception {
        mockMvc.perform(options("/api/projects")
                        .header(HttpHeaders.ORIGIN, "http://frontend.test")
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, method))
                .andExpect(status().isOk())
                .andExpect(header().string(
                        HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                        "http://frontend.test"
                ))
                .andExpect(header().string(
                        HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
                        containsString(method)
                ));
    }
}
