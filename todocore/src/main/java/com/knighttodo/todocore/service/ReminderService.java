package com.knighttodo.todocore.service;

import com.knighttodo.todocore.domain.ReminderVO;
import com.knighttodo.todocore.exception.InvalidReminderDateException;
import com.knighttodo.todocore.exception.ReminderNotFoundException;
import com.knighttodo.todocore.service.privatedb.mapper.ReminderMapper;
import com.knighttodo.todocore.service.privatedb.repository.ReminderRepository;
import com.knighttodo.todocore.service.privatedb.representation.Reminder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;

    @Transactional
    public ReminderVO save(ReminderVO reminderVO) {
        validateReminderDate(reminderVO.getReminderDate());

        Reminder reminder = reminderRepository.save(reminderMapper.toReminder(reminderVO));
        return reminderMapper.toReminderVO(reminder);
    }

    public ReminderVO findById(UUID id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException(id));
        return reminderMapper.toReminderVO(reminder);
    }

    @Transactional
    public void deleteById(UUID id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException(id));

        reminderRepository.delete(reminder);
    }

    @Transactional
    public ReminderVO update(UUID id, ReminderVO reminderVO) {
        validateReminderDate(reminderVO.getReminderDate());
        reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException(id));

        reminderVO.setId(id);
        Reminder reminderToUpdate = reminderMapper.toReminder(reminderVO);

        return reminderMapper.toReminderVO(reminderRepository.save(reminderToUpdate));
    }

    private void validateReminderDate(LocalDateTime reminderDate) {
        if (reminderDate.isBefore(LocalDateTime.now())) {
            log.warn("Invalid reminder date: {}", reminderDate);
            throw new InvalidReminderDateException();
        }
    }
}
