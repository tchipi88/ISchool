///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tsoft.ischool.service;
//
//import com.tsoft.ischool.domain.Caisse;
//import com.tsoft.ischool.domain.CaisseMouvement;
//import com.tsoft.ischool.domain.CaisseOuverture;
//import com.tsoft.ischool.domain.Decaissement;
//import com.tsoft.ischool.domain.Encaissement;
//import com.tsoft.ischool.domain.User;
//import com.tsoft.ischool.domain.enumeration.EtatCaisse;
//import java.math.BigDecimal;
//import java.util.List;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
///**
// *
// * @author tchipnangngansopa
// */
//@Service
//public class CaisseMouvementService {
//
//    public MouvementCaisse create(MouvementCaisse mc) throws Exception {
//        isClotureCaisse(mc);
//        isGerantCaisse(mc);
//
//        if (mc instanceof Encaissement) {
//            mc.setSolde(mc.getMontant().add(mc.getCaisse().getSolde_theorique() == null ? BigDecimal.ZERO : mc.getCaisse().getSolde_theorique()));
//        }
//        if (mc instanceof Decaissement) {
//            mc.setSolde(mc.getMontant().subtract(mc.getCaisse().getSolde_theorique() == null ? BigDecimal.ZERO : mc.getCaisse().getSolde_theorique()).negate());
//        }
//
//        return mouvementCaisseRepository.save(mc);
//
//    }
//
//    public boolean isClotureCaisse(CaisseMouvement mc) throws Exception {
//
////        List<CaisseOuverture> c = clotureCaisseRepository.findByCaisse(mc.getCaisse(), mc.getDate_paiement());
////        if (!c.isEmpty()) {
////            throw new BusinessException("Periode de saisie cloturée");
////        }
//        if (mc.getCaisse() == null || EtatCaisse.FERME.equals(mc.getCaisse().getEtat())) {
//            throw new Exception("Caisse Fermée  ou Aucune Caisse associée pour ce mouvement");
//        }
//        return true;
//    }
//
//    public boolean isGerantCaisse(CaisseMouvement mc) throws Exception {
//        User u = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        List<Caisse> caisses = caisseRepository.findByCodeAndGerant(u, mc.getCaisse().getCode());
//        if (CollectionUtils.isEmpty(caisses)) {
//            throw new Exception("Mouvement impossible car gerant non identifié");
//        }
//        return true;
//    }
//
//}
