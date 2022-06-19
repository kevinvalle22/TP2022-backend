package com.itconsulting.mentalHealth.controller;

import com.itconsulting.mentalHealth.core.entity.Affirmation;
import com.itconsulting.mentalHealth.core.service.AffirmationService;
import com.itconsulting.mentalHealth.core.service.AffirmationService;
import com.itconsulting.mentalHealth.resource.AffirmationResource;
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
public class AffirmationController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AffirmationService affirmationService;
    @GetMapping(value = "/affirmations")
    public Page<AffirmationResource> getAllAffirmations(Pageable pageable) {
        Page<Affirmation> affirmations = affirmationService.getAllAffirmations(pageable);
        List<AffirmationResource> resources = affirmations.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}/affirmations")
    public Page<AffirmationResource> getAllAffirmationsByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        List<AffirmationResource> resources = affirmationService.getAllAffirmationsByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int count = resources.size();
        return new PageImpl<>(resources, pageable, count);
    }

    @GetMapping("/users/{userId}/affirmations/{affirmationId}")
    public AffirmationResource getAffirmationByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                             @PathVariable(name = "affirmationId") Long affirmationId) {
        return convertToResource(affirmationService.getAffirmationByIdAndUserId(affirmationId, userId));
    }

    @PostMapping("/users/{userId}/affirmations")
    public AffirmationResource createAffirmation(@PathVariable(name = "userId") Long userId,
                                   @Valid @RequestBody AffirmationResource resource) {
        Long affirmationId = convertToResource(affirmationService.saveAffirmation(convertToEntity(resource),userId)).getId();
        return convertToResource( affirmationService.getTheDayOfWeek(affirmationId,userId));

    }
    @PutMapping("/users/{userId}/affirmations/{affirmationId}")
    public AffirmationResource updateAffirmation(@PathVariable(name = "userId") Long userId,
                                   @PathVariable(name = "affirmationId") Long affirmationId,
                                   @Valid @RequestBody AffirmationResource resource) {
        return convertToResource(affirmationService.updateAffirmationById(convertToEntity(resource),affirmationId, userId));
    }
    @DeleteMapping("/users/{userId}/affirmations/{affirmationId}")
    public ResponseEntity<?> deleteAffirmation(@PathVariable(name = "userId") Long userId,
                                        @PathVariable(name = "affirmationId") Long affirmationId) {
        return affirmationService.deleteAffirmation(affirmationId, userId);
    }
    private Affirmation convertToEntity(AffirmationResource resource) {
        return mapper.map(resource, Affirmation.class);
    }

    private AffirmationResource convertToResource(Affirmation entity) {
        return mapper.map(entity, AffirmationResource.class);
    }
}
