package com.medilabo.frontend.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NoteServiceImplTest {

    private MockWebServer mockWebServer;
    private NoteServiceImpl noteService;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient.Builder webClientBuilder = WebClient.builder();
        noteService = new NoteServiceImpl(webClientBuilder);

        ReflectionTestUtils.setField(noteService, "gatewayUrl", mockWebServer.url("/").toString());
        ReflectionTestUtils.setField(noteService, "username", "user");
        ReflectionTestUtils.setField(noteService, "password", "pass");
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testGetNotesByPatientId() {
        mockWebServer.enqueue(new MockResponse()
                .setBody("[{\"id\": \"1\", \"content\": \"Test Note\"}]")
                .addHeader("Content-Type", "application/json"));

        List<Map> notes = noteService.getNotesByPatientId(1L);

        assertNotNull(notes);
        assertEquals(1, notes.size());
        assertEquals("Test Note", notes.get(0).get("content"));
    }

    @Test
    void testAddNote() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        noteService.addNote(1L, "New Note");

        assertEquals(1, mockWebServer.getRequestCount());
    }

    @Test
    void testUpdateNote() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        noteService.updateNote("1", "Updated Note");

        assertEquals(1, mockWebServer.getRequestCount());
    }

    @Test
    void testDeleteNote() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        noteService.deleteNote("1");

        assertEquals(1, mockWebServer.getRequestCount());
    }
}
