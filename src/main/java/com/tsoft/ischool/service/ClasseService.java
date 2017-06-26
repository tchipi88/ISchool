/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.Classe;
import com.tsoft.ischool.domain.Serie;
import com.tsoft.ischool.repository.ClasseRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipnangngansopa
 */
@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    public List<Classe> save(List<Serie> series) {
        List<Classe> result = new ArrayList();
        for (Serie serie : series) {
            for (int i = 1; i <= serie.getNombreClassesToAdd(); i++) {
                Classe c = new Classe();
                c.setSerie(serie);
                c.setId(serie.getId() + (serie.getNombreClasses() + i));
                result.add(c);
            }
        };

        return classeRepository.save(result);
    }
}
