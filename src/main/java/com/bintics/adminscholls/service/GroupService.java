package com.bintics.adminscholls.service;

import com.bintics.adminscholls.dto.GroupDTO;
import com.bintics.adminscholls.exception.DuplicateGroupException;
import com.bintics.adminscholls.model.AcademicLevel;
import com.bintics.adminscholls.model.Group;
import com.bintics.adminscholls.repository.GroupRepository;
import com.bintics.adminscholls.repository.StudentGroupAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentGroupAssignmentRepository assignmentRepository;

    // Método unificado para todos los casos de búsqueda y filtrado
    public Page<GroupDTO> findGroups(AcademicLevel academicLevel,
                                   String grade,
                                   String name,
                                   String academicYear,
                                   Boolean isActive,
                                   boolean availableOnly,
                                   String searchText,
                                   Pageable pageable) {
        return groupRepository.findGroupsWithAllFilters(
                academicLevel, grade, name, academicYear, isActive, availableOnly, searchText, pageable)
                .map(this::convertToDTO);
    }

    public Optional<GroupDTO> getGroupById(Long id) {
        return groupRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Método helper para convertir Group a GroupDTO con studentsCount calculado
    private GroupDTO convertToDTO(Group group) {
        Long studentsCount = assignmentRepository.countActiveStudentsInGroup(group.getGroupCode());
        return new GroupDTO(group, studentsCount.intValue());
    }

    public GroupDTO createGroup(GroupDTO groupDTO) {
        // Validar que no existe un grupo duplicado
        if (groupRepository.existsDuplicateGroup(
                groupDTO.getAcademicLevel(),
                groupDTO.getGrade(),
                groupDTO.getName(),
                groupDTO.getAcademicYear(),
                null)) {
            throw new DuplicateGroupException(
                groupDTO.getAcademicLevel().toString(),
                groupDTO.getGrade(),
                groupDTO.getName(),
                groupDTO.getAcademicYear()
            );
        }

        Group group = Group.builder()
                .academicLevel(groupDTO.getAcademicLevel())
                .grade(groupDTO.getGrade())
                .name(groupDTO.getName())
                .academicYear(groupDTO.getAcademicYear())
                .maxStudents(groupDTO.getMaxStudents())
                .isActive(true)
                .build();

        Group savedGroup = groupRepository.save(group);
        return convertToDTO(savedGroup);
    }

    public Optional<GroupDTO> updateGroup(Long id, GroupDTO groupDTO) {
        return groupRepository.findById(id)
                .map(existingGroup -> {
                    // Validar que no existe un grupo duplicado (excluyendo el actual)
                    if (groupRepository.existsDuplicateGroup(
                            groupDTO.getAcademicLevel(),
                            groupDTO.getGrade(),
                            groupDTO.getName(),
                            groupDTO.getAcademicYear(),
                            id)) {
                        throw new DuplicateGroupException(
                            groupDTO.getAcademicLevel().toString(),
                            groupDTO.getGrade(),
                            groupDTO.getName(),
                            groupDTO.getAcademicYear()
                        );
                    }

                    existingGroup.setAcademicLevel(groupDTO.getAcademicLevel());
                    existingGroup.setGrade(groupDTO.getGrade());
                    existingGroup.setName(groupDTO.getName());
                    existingGroup.setAcademicYear(groupDTO.getAcademicYear());
                    existingGroup.setMaxStudents(groupDTO.getMaxStudents());

                    Group savedGroup = groupRepository.save(existingGroup);
                    return convertToDTO(savedGroup);
                });
    }

    public void deactivateGroup(Long id) {
        groupRepository.findById(id)
                .ifPresent(group -> {
                    group.setIsActive(false);
                    groupRepository.save(group);
                });
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    public Long getFullGroupsCount() {
        return groupRepository.countFullGroups();
    }

    public Double getAverageOccupancy() {
        return groupRepository.getAverageOccupancyPercentage();
    }
}
