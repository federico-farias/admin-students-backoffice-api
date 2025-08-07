package com.bintics.adminscholls.service;

import com.bintics.adminscholls.dto.PaymentDTO;
import com.bintics.adminscholls.model.Payment;
import com.bintics.adminscholls.model.PaymentStatus;
import com.bintics.adminscholls.model.Student;
import com.bintics.adminscholls.model.BreakfastPackage;
import com.bintics.adminscholls.repository.PaymentRepository;
import com.bintics.adminscholls.repository.StudentRepository;
import com.bintics.adminscholls.repository.BreakfastPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final BreakfastPackageRepository breakfastPackageRepository;

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentDTO::new)
                .toList();
    }

    public Page<PaymentDTO> getPendingPayments(Pageable pageable) {
        return paymentRepository.findByStatusOrderByDueDateAsc(PaymentStatus.PENDIENTE, pageable)
                .map(PaymentDTO::new);
    }

    public List<PaymentDTO> getOverduePayments() {
        return paymentRepository.findOverduePayments()
                .stream()
                .map(PaymentDTO::new)
                .toList();
    }

    public Optional<PaymentDTO> getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(PaymentDTO::new);
    }

    public List<PaymentDTO> getPaymentsByStudent(Long studentId) {
        return paymentRepository.findByStudentId(studentId)
                .stream()
                .map(PaymentDTO::new)
                .toList();
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Optional<Student> studentOpt = studentRepository.findById(paymentDTO.getStudentId());
        if (studentOpt.isEmpty()) {
            throw new IllegalArgumentException("Estudiante no encontrado");
        }

        Payment.PaymentBuilder paymentBuilder = Payment.builder()
                .student(studentOpt.get())
                .amount(paymentDTO.getAmount())
                .description(paymentDTO.getDescription())
                .paymentMethod(paymentDTO.getPaymentMethod())
                .status(paymentDTO.getStatus())
                .dueDate(paymentDTO.getDueDate())
                .period(paymentDTO.getPeriod())
                .periodType(paymentDTO.getPeriodType())
                .notes(paymentDTO.getNotes());

        // Asignar paquete de desayuno si se especifica
        if (paymentDTO.getBreakfastPackageId() != null) {
            Optional<BreakfastPackage> packageOpt = breakfastPackageRepository.findById(paymentDTO.getBreakfastPackageId());
            packageOpt.ifPresent(paymentBuilder::breakfastPackage);
        }

        // Si el pago se marca como pagado, establecer la fecha de pago
        if (paymentDTO.getStatus() == PaymentStatus.PAGADO && paymentDTO.getPaymentDate() == null) {
            paymentBuilder.paymentDate(LocalDate.now());
        } else if (paymentDTO.getPaymentDate() != null) {
            paymentBuilder.paymentDate(paymentDTO.getPaymentDate());
        }

        Payment payment = paymentBuilder.build();
        Payment savedPayment = paymentRepository.save(payment);
        return new PaymentDTO(savedPayment);
    }

    public Optional<PaymentDTO> updatePayment(Long id, PaymentDTO paymentDTO) {
        return paymentRepository.findById(id)
                .map(existingPayment -> {
                    existingPayment.setAmount(paymentDTO.getAmount());
                    existingPayment.setDescription(paymentDTO.getDescription());
                    existingPayment.setPaymentMethod(paymentDTO.getPaymentMethod());
                    existingPayment.setStatus(paymentDTO.getStatus());
                    existingPayment.setDueDate(paymentDTO.getDueDate());
                    existingPayment.setPeriod(paymentDTO.getPeriod());
                    existingPayment.setPeriodType(paymentDTO.getPeriodType());
                    existingPayment.setNotes(paymentDTO.getNotes());

                    // Actualizar fecha de pago cuando se marca como pagado
                    if (paymentDTO.getStatus() == PaymentStatus.PAGADO && existingPayment.getPaymentDate() == null) {
                        existingPayment.setPaymentDate(LocalDate.now());
                    } else if (paymentDTO.getPaymentDate() != null) {
                        existingPayment.setPaymentDate(paymentDTO.getPaymentDate());
                    }

                    // Actualizar paquete de desayuno
                    if (paymentDTO.getBreakfastPackageId() != null) {
                        Optional<BreakfastPackage> packageOpt = breakfastPackageRepository.findById(paymentDTO.getBreakfastPackageId());
                        packageOpt.ifPresent(existingPayment::setBreakfastPackage);
                    }

                    Payment savedPayment = paymentRepository.save(existingPayment);
                    return new PaymentDTO(savedPayment);
                });
    }

    public boolean markAsPaid(Long id) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    payment.setStatus(PaymentStatus.PAGADO);
                    payment.setPaymentDate(LocalDate.now());
                    paymentRepository.save(payment);
                    return true;
                })
                .orElse(false);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public void updateOverduePayments() {
        List<Payment> overduePayments = paymentRepository.findOverduePayments();
        for (Payment payment : overduePayments) {
            payment.setStatus(PaymentStatus.VENCIDO);
        }
        paymentRepository.saveAll(overduePayments);
    }

    public Long getPendingPaymentsCount() {
        return paymentRepository.countByStatus(PaymentStatus.PENDIENTE);
    }
}
