/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository.search;

import com.tsoft.ischool.domain.Eleve;
import com.tsoft.ischool.domain.PersonEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author tchipi
 */

/**
 * Spring Data JPA repository for the Employe entity.
 */
public interface PersonSearchRepository extends ElasticsearchRepository<PersonEntity, Long> {

    

}




