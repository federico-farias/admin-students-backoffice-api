package com.bintics.adminscholls.domains.assignment.dto;

import lombok.Data;

@Data
public class StudentTransferGroupDTO {

    private String studentPublicId;
    private String academicYear;
    private String academicLevel;
    private String grade;
    private String groupName;

}
