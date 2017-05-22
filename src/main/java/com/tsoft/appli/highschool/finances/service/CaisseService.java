package com.tsoft.appli.highschool.finances.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.tsoft.appli.highschool.model.Caisse;
import com.tsoft.appli.highschool.model.ClotureCaisse;
import com.tsoft.appli.highschool.model.Decaissement;
import com.tsoft.appli.highschool.model.Encaissement;
import com.tsoft.appli.highschool.model.EtatCaisse;
import com.tsoft.appli.highschool.model.MouvementCaisse;
import com.tsoft.appli.highschool.model.Reglement;
import com.tsoft.appli.highschool.repository.CaisseRepository;
import com.tsoft.appli.highschool.repository.ClotureCaisseRepository;
import com.tsoft.appli.highschool.repository.MouvementCaisseRepository;
import com.tsoft.appli.highschool.repository.ReglementRepository;
import com.tsoft.domain.User;
import com.tsoft.exception.BusinessException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author eisti
 */
@Service
public class CaisseService {

    @Autowired
    ClotureCaisseRepository clotureCaisseRepository;

    @Autowired
    CaisseRepository caisseRepository;

    @Autowired
    MouvementCaisseRepository mouvementCaisseRepository;
    @Autowired
    ReglementRepository  reglementRepository;

    @Transactional
    public Reglement create(Reglement r) throws Exception {
        Encaissement e=new Encaissement();
        e.setCaisse(r.getCaisse());
        e.setMontant(r.getMontant());
        e.setDate_paiement(r.getDateReglement());
        create(e);
        return  reglementRepository.save(r);

    }
    public MouvementCaisse create(MouvementCaisse mc) throws Exception {
        isClotureCaisse(mc);
        isGerantCaisse(mc);

        if (mc instanceof Encaissement) {
            mc.setSolde(mc.getMontant().add(mc.getCaisse().getSolde_theorique() == null ? BigDecimal.ZERO : mc.getCaisse().getSolde_theorique()));
        }
        if (mc instanceof Decaissement) {
            mc.setSolde(mc.getMontant().subtract(mc.getCaisse().getSolde_theorique() == null ? BigDecimal.ZERO : mc.getCaisse().getSolde_theorique()).negate());
        }

        return mouvementCaisseRepository.save(mc);

    }

    public boolean isClotureCaisse(MouvementCaisse mc) throws Exception {

        if (mc.getCaisse() == null) {
            throw new BusinessException("Aucune Caisse associée pour ce mouvement");
        }
        List<ClotureCaisse> c = clotureCaisseRepository.findByCaisse(mc.getCaisse(), mc.getDate_paiement());
        if (!c.isEmpty()) {
            throw new BusinessException("Periode de saisie cloturée");
        }

        if (EtatCaisse.FERME.equals(mc.getCaisse().getEtat_caisse())) {
            throw new BusinessException("Caisse Fermée");
        }
        return true;
    }

    public boolean isGerantCaisse(MouvementCaisse mc) throws Exception {
        User u = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<Caisse> caisses = caisseRepository.findByGerantAndCode(u,mc.getCaisse().getCode());
        if (CollectionUtils.isEmpty(caisses)) {
            throw new BusinessException("Mouvement impossible car gerant non identifié");
        }
        return true;
    }

//    public Caisse getUserCaisse() throws Exception {
//        User u = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        List<Caisse> caisses = dao.getAll(Caisse.class, "select c from Caisse c  where c.gerant.code= " + u.getCode());
//        if (CollectionUtils.isEmpty(caisses)) {
//            throw new BusinessException("Mouvement impossible car gerant non identifié");
//        }
//        return caisses.get(0);
//    }

}
