package com.itconsulting.mentalHealth.service;

import com.itconsulting.mentalHealth.core.entity.DAOUser;
import com.itconsulting.mentalHealth.core.entity.TestResult;
import com.itconsulting.mentalHealth.core.repository.TestResultRepository;
import com.itconsulting.mentalHealth.core.repository.UserRepository;
import com.itconsulting.mentalHealth.core.service.TestResultService;
import com.itconsulting.mentalHealth.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TestResultServiceImpl implements TestResultService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestResultRepository testResultRepository;
    @Override
    public Page<TestResult> getAllTestResults(Pageable pageable) {
        return testResultRepository.findAll(pageable);
    }

    @Override
    public Page<TestResult> getAllTestResultsByUserId(Long userId, Pageable pageable) {
        return testResultRepository.findByUserId(userId, pageable);
    }

    @Override
    public TestResult getTestResultByIdAndUserId(Long testResultId, Long userId) {
        return testResultRepository.findByIdAndUserId(testResultId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Test not found with Id " + testResultId +
                                " and UserId " + userId));
    }


    @Override
    public TestResult updateTestResultById(TestResult testResult, Long testResultId, Long userId) {
        TestResult result = testResultRepository.findById(testResultId)
                .orElseThrow(() -> new ResourceNotFoundException("Test", "Id", testResult));
        result.setType(testResult.getType());
        result.setScore(testResult.getScore());
        return testResultRepository.save(result);
    }

    @Override
    public TestResult saveTestResult(TestResult testResult, Long userId) {
        DAOUser user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        testResult.setUser(user);
        return testResultRepository.save(testResult);
    }

    @Override
    public ResponseEntity<?> deleteTestResult(Long testResultId, Long userId) {
        TestResult testResult = testResultRepository.findById(testResultId)
                .orElseThrow(() -> new ResourceNotFoundException("Test", "Id", testResultId));
        testResultRepository.delete(testResult);
        return ResponseEntity.ok().build();
    }
}
