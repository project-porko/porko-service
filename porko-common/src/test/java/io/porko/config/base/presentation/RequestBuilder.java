package io.porko.config.base.presentation;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class RequestBuilder {
    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private String url;

    private List<Object> urlVariables;

    private HttpMethod method;

    private final Map<String, Object> headers = new HashMap<>() {{
        put("Content-Type", MediaType.APPLICATION_JSON);
        put("Accept", MediaType.APPLICATION_JSON);
    }};

    private Object content;

    private HttpStatus status;

    private RequestBuilder(final MockMvc mockMvc, final ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = mapper;
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public static EndPoint get(final MockMvc mockMvc, final ObjectMapper objectMapper) {
        return new RequestBuilder(mockMvc, objectMapper)
            .request()
            .get();
    }

    public static EndPoint post(final MockMvc mockMvc, final ObjectMapper objectMapper) {
        return new RequestBuilder(mockMvc, objectMapper)
            .request()
            .post();
    }

    public static EndPoint put(final MockMvc mockMvc, final ObjectMapper objectMapper) {
        return new RequestBuilder(mockMvc, objectMapper)
            .request()
            .put();
    }

    public static EndPoint delete(final MockMvc mockMvc, final ObjectMapper objectMapper) {
        return new RequestBuilder(mockMvc, objectMapper)
            .request()
            .delete();
    }

    private Method request() {
        return new Method();
    }

    public class Method {
        public EndPoint method(final HttpMethod methodInput) {
            method = methodInput;
            return new EndPoint();
        }

        public EndPoint get() {
            method = GET;
            return new EndPoint();
        }

        public EndPoint post() {
            method = POST;
            return new EndPoint();
        }

        public EndPoint put() {
            method = PUT;
            return new EndPoint();
        }

        public EndPoint delete() {
            method = DELETE;
            return new EndPoint();
        }
    }

    public class EndPoint {
        public Header url(final String urlInput, final Object... urlVariablesInput) {
            url = urlInput;
            urlVariables = Arrays.asList(urlVariablesInput);
            return new Header();
        }
    }

    public class Header {
        public Content authentication(final String token) {
            headers.put(AUTHORIZATION, "Bearer ".concat(token));
            return new Content();
        }

        public Content noAuthentication() {
            return new Content();
        }
    }

    public class Content {
        public Expect jsonContent(final Object object) {
            content = object;
            return new Expect();
        }

        public Expect expect() {
            return new Expect();
        }
    }

    public class Expect {
        public ResultActions expectStatus(HttpStatus expectStatus) throws Exception {
            status = expectStatus;
            return makeRequest();
        }

        public ResultActions ok() throws Exception {
            status = HttpStatus.OK;
            return makeRequest();
        }

        public ResultActions noContent() throws Exception {
            status = HttpStatus.NO_CONTENT;
            return makeRequest();
        }

        public ResultActions created() throws Exception {
            status = HttpStatus.CREATED;
            return makeRequest();
        }

        public ResultActions unAuthorized() throws Exception {
            status = HttpStatus.UNAUTHORIZED;
            return makeRequest();
        }

        public ResultActions forbidden() throws Exception {
            status = HttpStatus.FORBIDDEN;
            return makeRequest();
        }

        public ResultActions badRequest() throws Exception {
            status = HttpStatus.BAD_REQUEST;
            return makeRequest();
        }

        public ResultActions notFound() throws Exception {
            status = HttpStatus.NOT_FOUND;
            return makeRequest();
        }

        public ResultActions conflict() throws Exception {
            status = HttpStatus.CONFLICT;
            return makeRequest();
        }
    }

    /**
     * TODO : TC 기반 API docs 추출 시 RequestBuilder 변경
     * - as-is :MockMvcRequestBuilders
     * - to-be : RestDocumentationRequestBuilders
     */
    private ResultActions makeRequest() throws Exception {
        MockHttpServletRequestBuilder request =
            MockMvcRequestBuilders.request(
                method,
                url,
                urlVariables.toArray()
            );

        for (String key : headers.keySet()) {
            request.header(key, headers.get(key));
        }

        if (content != null) {
            request.content(objectMapper.writeValueAsString(content));
        }

        return mockMvc.perform(request)
            .andExpect(status().is(status.value()));
    }
}
