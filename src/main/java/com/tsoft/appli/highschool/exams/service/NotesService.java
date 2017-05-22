/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.exams.service;

import com.tsoft.appli.highschool.repository.NotesPeriodeRepository;
import com.tsoft.exception.BusinessException;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class NotesService {

    @Autowired
    NotesPeriodeRepository notesPeriodeRepository;

    public void checkPeriodeSaisieNote() throws Exception {

        if (notesPeriodeRepository.findAll().isEmpty()) {
            throw new BusinessException("Periode de saisie des notes non specifiée");
        }
        if (notesPeriodeRepository.checkPeriodeSaisieNote(LocalDate.now()).isEmpty()) {
            throw new BusinessException("Periode de saisie des notes depassée");
        }

    }
}
