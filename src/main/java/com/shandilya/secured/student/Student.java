package com.shandilya.secured.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Student {
    private final Integer studentId;
    private final String studentName;
}