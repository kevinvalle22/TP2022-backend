package com.itconsulting.mentalHealth.core.service;

import com.itconsulting.mentalHealth.core.entity.TestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TestResultService {
    Page<TestResult> getAllTestResults(Pageable pageable);
    Page<TestResult> getAllTestResultsByUserId(Long userId, Pageable pageable);


    TestResult getTestResultByIdAndUserId(Long testResultId,Long userId);
    TestResult updateTestResultById(TestResult testResult,Long testResultId, Long userId);
    TestResult saveTestResult(TestResult testResult, Long userId);

    ResponseEntity<?> deleteTestResult(Long testResultId, Long userId);
}
