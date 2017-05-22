/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.exams.service;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author tchipi
 */
public class NotesDTOList  implements Serializable{
    
    private List<NotesDTO> notes;

    public List<NotesDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NotesDTO> notes) {
        this.notes = notes;
    }

    public NotesDTOList() {
    }
    
    
    
}
