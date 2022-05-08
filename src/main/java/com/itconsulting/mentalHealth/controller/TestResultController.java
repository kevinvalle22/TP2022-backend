package com.itconsulting.mentalHealth.controller;

import com.itconsulting.mentalHealth.core.entity.TestResult;
import com.itconsulting.mentalHealth.core.service.TestResultService;
import com.itconsulting.mentalHealth.resource.TestResultResource;
import org.hibernate.annotations.Parameter;
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
public class TestResultController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TestResultService testResultService;

    @GetMapping(value = "/tests")
    public Page<TestResultResource> getAllTestResults(Pageable pageable) {
        Page<TestResult> testResults = testResultService.getAllTestResults(pageable);
        List<TestResultResource> resources = testResults.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}/tests")
    public Page<TestResultResource> getAllTestResultsByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        List<TestResultResource> testResultResources = testResultService.getAllTestResultsByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int count = testResultResources.size();
        return new PageImpl<>(testResultResources, pageable, count);
    }

    @GetMapping("/users/{userId}/tests/{testId}")
    public TestResultResource getTestResultByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                             @PathVariable(name = "testId") Long testId) {
        return convertToResource(testResultService.getTestResultByIdAndUserId(testId, userId));
    }
    @PostMapping("/users/{userId}/tests")
    public TestResultResource createTest(@PathVariable(name = "userId") Long userId,
                                   @Valid @RequestBody TestResultResource resource) {
        return convertToResource(testResultService.saveTestResult(convertToEntity(resource),userId));

    }
    @PutMapping("/users/{userId}/tests/{testId}")
    public TestResultResource updateTestResult(@PathVariable(name = "userId") Long userId,
                                   @PathVariable(name = "testId") Long testId,
                                   @Valid @RequestBody TestResultResource resource) {
        return convertToResource(testResultService.updateTestResultById(convertToEntity(resource),testId, userId));
    }
    @DeleteMapping("/users/{userId}/tests/{testId}")
    public ResponseEntity<?> deleteTestResult(@PathVariable(name = "userId") Long userId,
                                        @PathVariable(name = "testId") Long testId) {
        return testResultService.deleteTestResult(testId, userId);
    }
    private TestResult convertToEntity(TestResultResource resource) {
        return mapper.map(resource, TestResult.class);
    }

    private TestResultResource convertToResource(TestResult entity) {
        return mapper.map(entity, TestResultResource.class);
    }

}
