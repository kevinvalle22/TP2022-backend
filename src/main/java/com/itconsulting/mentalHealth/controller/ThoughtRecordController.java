package com.itconsulting.mentalHealth.controller;

import com.itconsulting.mentalHealth.core.entity.ThoughtRecord;
import com.itconsulting.mentalHealth.core.service.ThoughtRecordService;
import com.itconsulting.mentalHealth.resource.ThoughtRecordResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ThoughtRecordController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ThoughtRecordService thoughtRecordService;


    @GetMapping(value = "/thoughtRecords")
    public Page<ThoughtRecordResource> getAllThoughtRecords(Pageable pageable) {
        Page<ThoughtRecord> thoughtRecords = thoughtRecordService.getAllThoughtRecords(pageable);
        List<ThoughtRecordResource> resources = thoughtRecords.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}/thoughtRecords")
    public Page<ThoughtRecordResource> getAllThoughtRecordsByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        List<ThoughtRecordResource> resources = thoughtRecordService.getAllThoughtRecordsByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int count = resources.size();
        return new PageImpl<>(resources, pageable, count);
    }

    @GetMapping("/users/{userId}/thoughtRecords/{thoughtRecordId}")
    public ThoughtRecordResource getThoughtRecordByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                                    @PathVariable(name = "thoughtRecordId") Long thoughtRecordId) {
        return convertToResource(thoughtRecordService.getThoughtRecordByIdAndUserId(thoughtRecordId, userId));
    }

    @PostMapping("/users/{userId}/thoughtRecords")
    public ThoughtRecordResource createThoughtRecord(@PathVariable(name = "userId") Long userId,
                                          @Valid @RequestBody ThoughtRecordResource resource) {
        return convertToResource(thoughtRecordService.saveThoughtRecord(convertToEntity(resource),userId));

    }
    @PutMapping("/users/{userId}/thoughtRecords/{thoughtRecordId}")
    public ThoughtRecordResource updateThoughtRecord(@PathVariable(name = "userId") Long userId,
                                          @PathVariable(name = "thoughtRecordId") Long thoughtRecordId,
                                          @Valid @RequestBody ThoughtRecordResource resource) {
        return convertToResource(thoughtRecordService.updateThoughtRecordById(convertToEntity(resource),thoughtRecordId, userId));
    }
    @DeleteMapping("/users/{userId}/thoughtRecords/{thoughtRecordId}")
    public ResponseEntity<?> deleteThoughtRecord(@PathVariable(name = "userId") Long userId,
                                               @PathVariable(name = "thoughtRecordId") Long thoughtRecordId) {
        return thoughtRecordService.deleteThoughtRecord(thoughtRecordId, userId);
    }
    private ThoughtRecord convertToEntity(ThoughtRecordResource resource) {
        return mapper.map(resource, ThoughtRecord.class);
    }

    private ThoughtRecordResource convertToResource(ThoughtRecord entity) {
        return mapper.map(entity, ThoughtRecordResource.class);
    }
}
