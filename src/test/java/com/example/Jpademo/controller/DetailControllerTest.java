package com.example.Jpademo.controller;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.Jpademo.com.controller.DetailController;
import com.example.Jpademo.com.model.Detail;
import com.example.Jpademo.com.service.DetailService;
import com.example.Jpademo.com.zipformat.ProjectGenerationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class DetailControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private DetailController detailController;

//    the services annotated with @Mock will be mocked into detailController
    @Mock
    private DetailService detailService;

    @Mock
    private ProjectGenerationService projectGenerationService;

    @Mock
    private MongoTemplate mongoTemplate;



    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(detailController)
                .build();
    }

    private Detail createSampleDetail() {
        Detail detail = new Detail();
        detail.setGroupId("com.example");
        detail.setArtifactId("myapp");
        detail.setName("myapp");
        detail.setDescription("My springBoot Project");
        detail.setPackageName("com.example.myapp");
        detail.setBuildType("maven");
        detail.setLanguage("java");
        detail.setSpringBootVersion("3.1.3");
        detail.setPackaging("jar");
        detail.setJavaVersion("17");

        List<String> dependencies = new ArrayList<>();
        dependencies.add("<dependency>\n<groupId>org.springframework.boot</groupId>\n <artifactId>spring-boot-starter-web</artifactId>\n </dependency>");
        detail.setDependencies(dependencies);

        detail.setCustomDependencies("<dependency>\n" +
                "      <groupId>org.springframework.boot</groupId>\n" +
                "      <artifactId>spring-boot-devtools</artifactId>\n" +
                "      <scope>runtime</scope>\n" +
                "      <optional>true</optional>\n" +
                "    </dependency>");

        return detail;
    }

    private List<Detail> createSampleDetailsList() {
        List<Detail> detailsList = new ArrayList<>();

        // Create and add multiple sample Detail objects to the list
        Detail detail1 = new Detail();
        // Set properties for detail1
        detail1.setGroupId("com.example");
        detail1.setArtifactId("demo");
        detail1.setName("demo");
        detail1.setDescription("My springBoot Project");
        detail1.setPackageName("com.example.demo");
        detail1.setBuildType("gradle");
        detail1.setLanguage("java");
        detail1.setSpringBootVersion("3.1.2");
        detail1.setPackaging("war");
        detail1.setJavaVersion("20");

        List<String> dependencies1 = new ArrayList<>();
        dependencies1.add("implementation 'org.springframework.boot:spring-boot-starter-web'");
        detail1.setDependencies(dependencies1);

        detail1.setCustomDependencies(" developmentOnly 'org.springframework.boot:spring-boot-devtools'");

        detailsList.add(detail1);

        Detail detail2 = new Detail();
        // Set properties for detail2
        detail2.setGroupId("com.example");
        detail2.setArtifactId("demo");
        detail2.setName("demo");
        detail2.setDescription("My springBoot Project");
        detail2.setPackageName("com.example.demo");
        detail2.setBuildType("maven");
        detail2.setLanguage("java");
        detail2.setSpringBootVersion("3.1.2");
        detail2.setPackaging("war");
        detail2.setJavaVersion("17");

        List<String> dependencies2 = new ArrayList<>();
        dependencies2.add("<dependency>\n" +
                "      <groupId>org.projectlombok</groupId>\n" +
                "      <artifactId>lombok</artifactId>\n" +
                "      <optional>true</optional>\n" +
                "    </dependency>");
        detail2.setDependencies(dependencies2);

        detail2.setCustomDependencies("<dependency>\n" +
                "      <groupId>org.springframework.boot</groupId>\n" +
                "      <artifactId>spring-boot-starter-test</artifactId>\n" +
                "      <scope>test</scope>\n" +
                "    </dependency>");

        detailsList.add(detail2);

        return detailsList;
    }



    @Test
    public void testSubmitDetail() throws Exception {
        // Create a sample Detail object
        Detail detail = createSampleDetail();



        // Mock the behavior of the detailService.createDetail method
        when(detailService.createDetail(any(Detail.class))).thenReturn(detail);

//        to convert java object to JSON object
        String JsonFormat = objectMapper.writeValueAsString(detail);

        // Perform a POST request to /api/generate
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonFormat)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Data saved successfully."))
                .andDo(print());

        // Verify that detailService.createDetail was called with the correct argument
        verify(detailService, times(1)).createDetail(any(Detail.class));

        // Verify that projectGenerationService.generateProject was called
        verify(projectGenerationService, times(1)).generateProject(eq(detail), any(MockHttpServletResponse.class));

        // Verify that mongoTemplate.dropCollection and mongoTemplate.insert were called
        verify(mongoTemplate, times(1)).dropCollection(Detail.class);
        verify(mongoTemplate, times(1)).insert(detail);
    }

    @Test
    public void testSubmitDetailServiceException() throws Exception {
        // Create a sample Detail object
        Detail detail = createSampleDetail();

        // Mock detailService.createDetail to throw an exception
        when(detailService.createDetail(any(Detail.class))).thenThrow(new RuntimeException("Service exception"));

        // Convert the detail to JSON
        String json = objectMapper.writeValueAsString(detail);

        // Perform a POST request to /api/generate
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andDo(print());

        // Verify that detailService.createDetail was called
        verify(detailService, times(1)).createDetail(any(Detail.class));
    }



    @Test
    public void testgetAllDetails() throws Exception {

//        Detail detail = new Detail()
        // Mock the behavior of the detailService.createDetail method
//        when(detailService.getAllDetails()).thenReturn((List<Detail>) detail);

        List<Detail> sampleDetails = createSampleDetailsList();
        when(detailService.getAllDetails()).thenReturn(sampleDetails);


        // Perform a POST request to /api/generate
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/details")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //Json to JavaObject
        String responseContent = result.getResponse().getContentAsString();

        // Validate that the response content contains the expected data
        for (Detail detail : sampleDetails) {
            String detailJson = objectMapper.writeValueAsString(detail);
//            Assert.assertTrue to check if the response content contains the JSON representation of each detail.
//            This ensures that the response content matches the expected data.
            Assert.assertTrue(responseContent.contains(detailJson));
        }

        verify(detailService, times(1)).getAllDetails();

    }

}
