/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository.search;

import com.tsoft.ischool.domain.CompteAnalytique;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Client entity.
 */
public interface CompteAnalytiqueSearchRepository extends ElasticsearchRepository<CompteAnalytique, Long> {

    

}




