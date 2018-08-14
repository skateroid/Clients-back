package com.example.clientsDB.service;

import com.example.clientsDB.dto.MarkChangeRequest;
import com.example.clientsDB.entity.MarkEntity;
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

    public List<Mark> getAll() {
//        return markRepository.findAll();
        return markMapper.mapEntitiesToModel(markRepository.findAll());
    }

    public Mark getMarkByName(String name) {
        MarkEntity markEntity = markRepository.findMarkByNameContainingIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("MarkEntity: %s not found", name)));
        return markMapper.mapEntityToModel(markEntity);
//        return markRepository.findMarkByNameContainingIgnoreCase(name)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("MarkEntity: %s not found", name)));
    }

    public Mark getMarkById(Long id) {
        MarkEntity markEntity = markRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("MarkEntity with id: %d not found", id)));
        return markMapper.mapEntityToModel(markEntity);
//        return markRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("MarkEntity with id: %d not found", id)));
    }

    public Mark createMark(MarkChangeRequest markChangeRequest) {
//        MarkEntity newMarkEntity = new MarkEntity();
//        newMarkEntity.setName(markChangeRequest.getName());
        MarkEntity newMarkEntity = markMapper.dtoToEntity(markChangeRequest);
        markRepository.save(newMarkEntity);

        return markService.getMarkById(newMarkEntity.getId());
    }

    public Mark updateMark(Long id, MarkChangeRequest request) {
//        MarkEntity currentMark = getMarkById(id);
//        currentMark.setName(request.getName());
        MarkEntity markEntityOld = markRepository.findMarkById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("MarkEntity with id: %d not found", id)));
        MarkEntity markEntityNew = markMapper.dtoToEntity(request);
        markEntityNew.setId(markEntityOld.getId());
        markRepository.save(markEntityNew);

        return markService.getMarkById(id);
    }

    public void deleteMark(Long id) {
        markRepository.deleteById(id);
    }
}
