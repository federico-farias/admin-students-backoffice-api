package com.bintics.adminscholls.service;

import com.bintics.adminscholls.dto.DashboardStatsDTO;
import com.bintics.adminscholls.model.PaymentStatus;
import com.bintics.adminscholls.repository.StudentRepository;
import com.bintics.adminscholls.repository.GroupRepository;
import com.bintics.adminscholls.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final PaymentRepository paymentRepository;

    public DashboardStatsDTO getDashboardStats() {
        // Estadísticas de estudiantes
        Long totalStudents = studentRepository.count();
        Long activeStudents = studentRepository.countActiveStudents();

        // Estadísticas de pagos
        Long totalPayments = paymentRepository.count();
        Long pendingPayments = paymentRepository.countByStatus(PaymentStatus.PENDIENTE);
        BigDecimal monthlyRevenue = paymentRepository.getMonthlyRevenue();
        BigDecimal unpaidAmount = paymentRepository.getUnpaidAmount();

        // Estadísticas de grupos
        Long totalGroups = groupRepository.count();
        Long fullGroups = groupRepository.countFullGroups();
        Double averageOccupancy = groupRepository.getAverageOccupancyPercentage();

        // Manejar valores nulos
        if (monthlyRevenue == null) monthlyRevenue = BigDecimal.ZERO;
        if (unpaidAmount == null) unpaidAmount = BigDecimal.ZERO;
        if (averageOccupancy == null) averageOccupancy = 0.0;

        return DashboardStatsDTO.builder()
                .totalStudents(totalStudents)
                .activeStudents(activeStudents)
                .totalPayments(totalPayments)
                .pendingPayments(pendingPayments)
                .monthlyRevenue(monthlyRevenue)
                .unpaidAmount(unpaidAmount)
                .totalGroups(totalGroups)
                .fullGroups(fullGroups)
                .averageOccupancy(averageOccupancy)
                .build();
    }

    public Double getStudentOccupancyRate() {
        DashboardStatsDTO stats = getDashboardStats();
        return stats.getStudentActivationRate();
    }

    public Double getPaymentSuccessRate() {
        DashboardStatsDTO stats = getDashboardStats();
        return stats.getPaymentCompletionRate();
    }

    public Double getGroupUtilizationRate() {
        DashboardStatsDTO stats = getDashboardStats();
        return stats.getGroupOccupancyRate();
    }
}
