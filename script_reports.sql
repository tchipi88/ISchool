-- Script

create or replace view vuemouvementcaisse as select c.id, if(c.type='C', c.montant, 0) as entree,  if(c.type='D', c.montant, 0) as sortie, c.date_versement as date_operation, c.created_date as date_enregistrement, c.mode_paiement, c.motif, c.commentaires, p.id as person_id, p.nom_prenom, p.type_personne,
c.caisse_id, cs.etat, concat(em.nom, if(em.prenom is null, '', concat (' ', em.prenom))) as nom_gerant, cs.gerant_id
from caisse_mouvement c 
left join person p on c.person_id=p.id 
left join caisse cs on c.caisse_id=cs.id 
left join employe em on cs.gerant_id=em.id
	order by  c.created_date, c.date_versement, p.nom_prenom ;
	
update person p join eleve e on p.id=e.person_id set p.nom_prenom=concat(e.nom, if(e.prenom is null, '', concat (' ', e.prenom)));

select e.nom, e.prenom, concat(e.nom, if(e.prenom is null, '', concat (' ', e.prenom))) as nom_prenom from eleve e where e.id>1700;

 
update caisse set sortie = (select sum(montant) from caisse_mouvement where type='D' );

update person p join professeur pf on p.id=pf.person_id set p.type_personne='TEACHER';
update reglement set classe_id='CLASS 11' where classe_id is null;

select count(*) from classe_eleve;
 
update reglement r join classe_eleve ce on r.eleve_id=ce.eleve_id set r.classe_id=ce..classe_id;

delete from classe where id!='CLASS 11';
select distinct classe_id from reglement;

update reglement r join classe c on r.classe_id=c.id left join classe_eleve ce on r.eleve_id=ce.eleve_id and c.id=ce.classe_id set r.classe_id=null where ce.id is null;

