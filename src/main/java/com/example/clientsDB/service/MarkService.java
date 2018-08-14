package com.example.clientsDB.service;

import com.example.clientsDB.dto.MarkChangeRequest;
import com.example.clientsDB.exception.EntityNotFoundException;
import com.example.clientsDB.mapper.MarkMapper;
import com.example.clientsDB.model.Mark;
import com.example.clientsDB.repositories.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkService {
    private MarkRepository markRepository;
    private MarkMapper markMapper;
    @Autowired
    private MarkService markService;

    public MarkService(MarkRepository markRepository, MarkMapper markMapper) {
        this.markRepository = markRepository;
        this.markMapper = markMapper;
    }

    public List<MarkChangeRequest> getAll() {
//        return markRepository.findAll();
        return markMapper.mapEntitiesToModel(markRepository.findAll());
    }

    public MarkChangeRequest getMarkByName(String name) {
        Mark mark = markRepository.findMarkByNameContainingIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Mark: %s not found", name)));
        return markMapper.mapEntity(mark);
//        return markRepository.findMarkByNameContainingIgnoreCase(name)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("Mark: %s not found", name)));
    }

    public MarkChangeRequest getMarkById(Long id) {
        Mark mark = markRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Mark with id: %d not found", id)));
        return markMapper.mapEntity(mark);
//        return markRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("Mark with id: %d not found", id)));
    }

    public MarkChangeRequest createMark(MarkChangeRequest markChangeRequest) {
//        Mark newMark = new Mark();
//        newMark.setName(markChangeRequest.getName());
        Mark newMark = markMapper.dtoToEntity(markChangeRequest);
        markRepository.save(newMark);

        return markService.getMarkById(newMark.getId());
    }

    public MarkChangeRequest updateMark(Long id, MarkChangeRequest request) {
//        Mark currentMark = getMarkById(id);
//        currentMark.setName(request.getName());
        Mark markOld = markRepository.findMarkById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Mark with id: %d not found", id)));
        Mark markNew = markMapper.dtoToEntity(request);
        markNew.setId(markOld.getId());
        markRepository.save(markNew);

        return markService.getMarkById(id);
    }

    public void deleteMark(Long id) {
        markRepository.deleteById(id);
    }
}
