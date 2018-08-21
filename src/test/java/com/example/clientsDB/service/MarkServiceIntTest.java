package com.example.clientsDB.service;

import com.example.clientsDB.dto.MarkChangeRequest;
import com.example.clientsDB.exception.EntityNotFoundException;
import com.example.clientsDB.model.Mark;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarkServiceIntTest {

    @Autowired
    private MarkService markService;

    @Test
    public void testGetAll() {
        MarkChangeRequest request = new MarkChangeRequest();
        request.setName("Audi");
        Long id1 = markService.createMark(request).getId();
        request.setName("Bmw");
        Long id2 = markService.createMark(request).getId();

        assertThat(markService.getAll()).isNotEmpty();

        Mark mark1 = markService.getAll().stream()
                .filter(mark -> mark.getId().equals(id1))
                .findFirst()
                .orElseThrow(AssertionError::new);
        assertThat(mark1.getName()).isEqualTo("Audi");

        Mark mark2 = markService.getAll().stream()
                .filter(mark -> mark.getId().equals(id2))
                .findFirst()
                .orElseThrow(AssertionError::new);
        assertThat(mark2.getName()).isEqualTo("Bmw");
    }

    @Test
    public void testCreateAndGet() {
        MarkChangeRequest request = new MarkChangeRequest();
        request.setName("Tesla");
        Long id = markService.createMark(request).getId();
        assertThat(markService.getMarkById(id).getName()).isEqualTo("Tesla");
    }

    @Test
    public void testUpdateMark() {
        MarkChangeRequest request = new MarkChangeRequest();
        request.setName("Infinity");
        Long id = markService.createMark(request).getId();
        request.setName("opel");
        markService.updateMark(id, request);
        assertThat(markService.getMarkById(id).getName()).isEqualTo("opel");
    }

    @Test
    public void testDeleteMark() {
        MarkChangeRequest markChangeRequest = MarkChangeRequest.builder()
                .name("bmw")
                .build();
        Long id = markService.createMark(markChangeRequest).getId();

        markService.deleteMark(id);

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> markService.getMarkById(id))
                .withMessage(String.format("MarkEntity with id: %d not found", id));
    }

    @Test
    public void testGetMarkByName() {
        MarkChangeRequest request = MarkChangeRequest.builder()
                .name("geely")
                .build();
        String name = markService.createMark(request).getName();

        assertThat(markService.getMarkByName(name).stream()
                .filter(mark -> mark.getName().equals(name))
                .findFirst()
                .orElseThrow(AssertionError::new).getName())
                .isEqualTo("geely");
    }

    @Test
    public void testCreateExistingMark() {
        MarkChangeRequest markChangeRequestBefore = new MarkChangeRequest();
        markChangeRequestBefore.setName("Lifan");
        markService.createMark(markChangeRequestBefore);
        MarkChangeRequest markRequest = new MarkChangeRequest();
        markRequest.setName("LIFan");

        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> markService.createMark(markRequest));
    }

    @Test
    public void testGetNonExistingMark() {
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> markService.deleteMark(20000L));
    }

    @Test
    public void testUpdateNonExistingMark() {
        MarkChangeRequest request = MarkChangeRequest.builder()
                .name("Haval")
                .build();
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> markService.updateMark(20000L, request));
    }
}
