package com.example.project.controller;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ExtendWith(value = RestDocumentationExtension.class)
public abstract class AbstractControllerTest {
    @Autowired
    protected ObjectMapper objectMapper;

    protected RequestSpecification documentationSpec;

    @LocalServerPort
    private int port;

    @BeforeEach
    protected void setUp(RestDocumentationContextProvider restDocumentationContextProvider) {

        RestAssured.port = port;

        this.documentationSpec = new RequestSpecBuilder()
            .addFilter(documentationConfiguration(restDocumentationContextProvider))
            .addFilter(
                documentationConfiguration(restDocumentationContextProvider)
                    .operationPreprocessors()
                    .withRequestDefaults(
                        prettyPrint(),
                        modifyHeaders()
                            .remove(HttpHeaders.AUTHORIZATION)
                    )
                    .withResponseDefaults(
                        prettyPrint(),
                        modifyHeaders()
                            .remove(HttpHeaders.VARY)
                            .remove(HttpHeaders.CACHE_CONTROL)
                            .remove(HttpHeaders.PRAGMA)
                            .remove(HttpHeaders.EXPIRES)
                            .remove(HttpHeaders.TRANSFER_ENCODING)
                            .remove(HttpHeaders.DATE)
                            .remove(HttpHeaders.CONNECTION)
                    )
            )
            .build();
    }

    protected RequestSpecification given(Snippet... snippets) {
        return RestAssured.given(this.documentationSpec).log().all()
            .filter(
                document(
                    apiIdentifier(),
                    snippets
                )
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    protected Snippet pageableQueryParameterSnippet() {
        return queryParameters(
            parameterWithName("page").description("page").optional(),
            parameterWithName("size").description("size").optional()
        );
    }

    private String apiIdentifier() {
        return identifier() + "/{method-name}";
    }

    abstract protected String identifier();
}
