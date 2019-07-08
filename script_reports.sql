-- Script

create or replace view vuemouvementcaisse as select c.id, if(c.type='C', c.montant, 0) as entree,  if(c.type='D', c.montant, 0) as sortie, c.date_versement as date_operation, c.created_date as date_enregistrement, c.mode_paiement, c.motif, c.commentaires, p.id as person_id, p.nom_prenom, p.type_personne,
c.caisse_id, cs.etat, concat(em.nom, if(em.prenom=null, '', concat (' ', em.prenom))) as nom_gerant, cs.gerant_id
from caisse_mouvement c 
left join person p on c.id_person=p.id 
left join caisse cs on c.caisse_id=cs.id 
left join employe em on cs.gerant_id=em.id
	order by  c.created_date, c.date_versement, p.nom_prenom ;
