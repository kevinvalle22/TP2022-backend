package com.itconsulting.mentalHealth.controller;

import com.itconsulting.mentalHealth.core.entity.Breathing;
import com.itconsulting.mentalHealth.core.service.BreathingService;
import com.itconsulting.mentalHealth.resource.BreathingResource;
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
public class BreathingController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BreathingService breathingService;
    @GetMapping(value = "/breathings")
    public Page<BreathingResource> getAllBreathings(Pageable pageable) {
        Page<Breathing> breathings = breathingService.getAllBreathings(pageable);
        List<BreathingResource> resources = breathings.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}/breathings")
    public Page<BreathingResource> getAllBreathingsByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        List<BreathingResource> resources = breathingService.getAllBreathingsByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int count = resources.size();
        return new PageImpl<>(resources, pageable, count);
    }

    @GetMapping("/users/{userId}/breathings/{breathingId}")
    public BreathingResource getBreathingByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                                    @PathVariable(name = "breathingId") Long breathingId) {
        return convertToResource(breathingService.getBreathingByIdAndUserId(breathingId, userId));
    }

    @PostMapping("/users/{userId}/breathings")
    public BreathingResource createBreathing(@PathVariable(name = "userId") Long userId,
                                          @Valid @RequestBody BreathingResource resource) {
        return convertToResource(breathingService.saveBreathing(convertToEntity(resource),userId));

    }
    @PutMapping("/users/{userId}/breathings/{breathingId}")
    public BreathingResource updateBreathing(@PathVariable(name = "userId") Long userId,
                                          @PathVariable(name = "breathingId") Long breathingId,
                                          @Valid @RequestBody BreathingResource resource) {
        return convertToResource(breathingService.updateBreathingById(convertToEntity(resource),breathingId, userId));
    }
    @DeleteMapping("/users/{userId}/breathings/{breathingId}")
    public ResponseEntity<?> deleteBreathing(@PathVariable(name = "userId") Long userId,
                                               @PathVariable(name = "breathingId") Long breathingId) {
        return breathingService.deleteBreathing(breathingId, userId);
    }
    private Breathing convertToEntity(BreathingResource resource) {
        return mapper.map(resource, Breathing.class);
    }

    private BreathingResource convertToResource(Breathing entity) {
        return mapper.map(entity, BreathingResource.class);
    }
}
