package com.example.clientsDB.resource;

import com.example.clientsDB.dto.MarkChangeRequest;
import com.example.clientsDB.model.Mark;
import com.example.clientsDB.service.MarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/marks")
@Api(tags = "mark", description = "Марки машин")
public class MarkResource {
    private MarkService markService;

    public MarkResource(MarkService markService) {
        this.markService = markService;
    }

    @GetMapping
    @ApiOperation("Получить все марки")
    public List<MarkChangeRequest> getAll() {
        return markService.getAll();
    }

    @GetMapping("{id}")
    @ApiOperation("Получить марку по id")
    public MarkChangeRequest getMarkById(@PathVariable(name = "id") Long id) {
        return markService.getMarkById(id);
    }

    @GetMapping("/search")
    @ApiOperation("Получить марку по названию")
    public MarkChangeRequest getMarkByName(@RequestParam (name = "name")String name) {
        return markService.getMarkByName(name);
    }

    @PostMapping
    @ApiOperation("Добавить марку")
    public MarkChangeRequest create(@Valid @RequestBody MarkChangeRequest request) {
        return markService.createMark(request);
    }

    @PutMapping("{markId}")
    @ApiOperation("Внести изменения в существующую марку")
    public MarkChangeRequest update(@PathVariable ("markId") Long id,
                       @RequestBody MarkChangeRequest request) {
       return markService.updateMark(id, request);
    }

    @DeleteMapping("{markId}")
    @ApiOperation("Удалить марку")
    public void delete(@PathVariable("markId") Long id) {
        markService.deleteMark(id);
    }
}
