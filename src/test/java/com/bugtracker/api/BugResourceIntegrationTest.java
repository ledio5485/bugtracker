package com.bugtracker.api;

import com.bugtracker.core.BugCommandService;
import com.bugtracker.core.BugQueryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BugResourceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BugCommandService bugCommandService;
    @Autowired
    private BugQueryService bugQueryService;

    @Nested
    @DisplayName("Tests for the method findAll")
    class FindBugs {
        @Test
        void shouldGetEmptyCollectionWhenNoBugsAreCreatedYet() throws Exception {
            MvcResult mvcResult = mockMvc.perform(get("/bugs")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            List<BugDTO> actual = objectMapper.readValue(actualResponseBody, new TypeReference<>() {
            });

            List<BugDTO> expected = List.of();
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void shouldGetBugs() throws Exception {
            BugDTO bug1 = new BugDTO(null, "title1", "description1", Status.NEW, UUID.randomUUID(), UUID.randomUUID(), null, null);
            BugDTO bug2 = new BugDTO(null, "title2", "description2", Status.NEW, UUID.randomUUID(), UUID.randomUUID(), null, null);
            BugDTO createdBug1 = bugCommandService.create(bug1);
            BugDTO createdBug2 = bugCommandService.create(bug2);

            MvcResult mvcResult = mockMvc.perform(get("/bugs")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            List<BugDTO> actual = objectMapper.readValue(actualResponseBody, new TypeReference<>() {
            });

            List<BugDTO> expected = List.of(createdBug1, createdBug2);
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Tests for the method findBugById")
    class FindBugById {
        @Test
        @DisplayName("should return NOT_FOUND when bug does not exist")
        void shouldReturnNotFoundWhenBugDoesNotExist() throws Exception {
            mockMvc.perform(get("/bugs/{id}", UUID.randomUUID())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("should find bug when it exists")
        void shouldFindBugWhenExist() throws Exception {
            BugDTO bug = new BugDTO(null, "title", "description", Status.NEW, UUID.randomUUID(), UUID.randomUUID(), null, null);
            BugDTO expected = bugCommandService.create(bug);

            MvcResult mvcResult = mockMvc.perform(get("/bugs/{id}", expected.id())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            BugDTO actual = objectMapper.readValue(actualResponseBody, BugDTO.class);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Tests for the method create")
    class CreateBug {

        @Test
        void shouldCreateBug() throws Exception {
            assertThat(bugQueryService.findAll().size()).isEqualTo(0);
            BugDTO bug = new BugDTO(null, "title", "description", Status.NEW, UUID.randomUUID(), UUID.randomUUID(), null, null);

            MvcResult mvcResult = mockMvc.perform(post("/bugs")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(bug)))
                    .andExpect(status().isCreated())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            BugDTO actual = objectMapper.readValue(actualResponseBody, BugDTO.class);

            assertThat(bugQueryService.findAll().size()).isEqualTo(1);
            BugDTO expected = bugQueryService.findAll().stream().findFirst().orElseThrow();
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Tests for the method deleteBugById")
    class DeleteBugById {
        @Test
        @DisplayName("should return NOT_FOUND when bug does not exist")
        void shouldReturnNotFoundWhenBugDoesNotExist() throws Exception {
            mockMvc.perform(delete("/bugs/{id}", UUID.randomUUID())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("should delete bug when it exists")
        void shouldDeleteBugWhenExist() throws Exception {
            assertThat(bugQueryService.findAll().size()).isEqualTo(0);
            BugDTO bug = new BugDTO(null, "title", "description", Status.NEW, UUID.randomUUID(), UUID.randomUUID(), null, null);
            BugDTO createdBug = bugCommandService.create(bug);
            assertThat(bugQueryService.findAll().size()).isEqualTo(1);

            mockMvc.perform(delete("/bugs/{id}", createdBug.id())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andReturn();

            assertThat(bugQueryService.findAll().size()).isEqualTo(0);
        }
    }
}