package io.porko.config.base.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.porko.config.base.TestBase;
import io.porko.config.security.TestSecurityConfig;
import jakarta.annotation.Resource;
import java.io.UnsupportedEncodingException;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Import(TestSecurityConfig.class)
public abstract class WebMvcTestBase extends TestBase {
    @Resource
    protected MockMvc mockMvc;
    @Resource
    protected ObjectMapper objectMapper;

    protected RequestBuilder.EndPoint get() {
        return RequestBuilder.get(mockMvc, objectMapper);
    }

    protected RequestBuilder.EndPoint post() {
        return RequestBuilder.post(mockMvc, objectMapper);
    }

    protected RequestBuilder.EndPoint put() {
        return RequestBuilder.put(mockMvc, objectMapper);
    }

    protected RequestBuilder.EndPoint delete() {
        return RequestBuilder.delete(mockMvc, objectMapper);
    }

    protected <T> T andReturn(ResultActions resultActions, Class<T> clazz)
        throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(extractResponseBody(resultActions), clazz);
    }

    private String extractResponseBody(ResultActions resultActions) throws UnsupportedEncodingException {
        return resultActions.andReturn().getResponse().getContentAsString();
    }

    protected String extractResponseHeader(ResultActions resultActions, String key) {
        return resultActions.andReturn().getResponse().getHeader(key);
    }
}
