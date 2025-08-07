package com.bintics.adminscholls.service;

import com.bintics.adminscholls.dto.GroupDTO;
import com.bintics.adminscholls.model.Group;
import com.bintics.adminscholls.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;

    public List<GroupDTO> getAllGroups() {
        return groupRepository.findAll()
                .stream()
                .map(GroupDTO::new)
                .toList();
    }

    public List<GroupDTO> getActiveGroups() {
        return groupRepository.findByIsActiveTrue()
                .stream()
                .map(GroupDTO::new)
                .toList();
    }

    public Optional<GroupDTO> getGroupById(Long id) {
        return groupRepository.findById(id)
                .map(GroupDTO::new);
    }

    public GroupDTO createGroup(GroupDTO groupDTO) {
        Group group = Group.builder()
                .academicLevel(groupDTO.getAcademicLevel())
                .grade(groupDTO.getGrade())
                .name(groupDTO.getName())
                .maxStudents(groupDTO.getMaxStudents())
                .isActive(true)
                .build();

        Group savedGroup = groupRepository.save(group);
        return new GroupDTO(savedGroup);
    }

    public Optional<GroupDTO> updateGroup(Long id, GroupDTO groupDTO) {
        return groupRepository.findById(id)
                .map(existingGroup -> {
                    existingGroup.setAcademicLevel(groupDTO.getAcademicLevel());
                    existingGroup.setGrade(groupDTO.getGrade());
                    existingGroup.setName(groupDTO.getName());
                    existingGroup.setMaxStudents(groupDTO.getMaxStudents());

                    Group savedGroup = groupRepository.save(existingGroup);
                    return new GroupDTO(savedGroup);
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

    public List<GroupDTO> getAvailableGroups() {
        return groupRepository.findByIsActiveTrue()
                .stream()
                .filter(Group::hasAvailableSlots)
                .map(GroupDTO::new)
                .toList();
    }
}
