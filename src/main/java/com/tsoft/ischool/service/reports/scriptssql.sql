-- request to fill data in detail
select tm.libelle as typematiere,m.libelle as discipline,note,coef.valeur as coef,note*coef.valeur as total,rangmatiere(m.libelle,e.matricule,1,'TleC1') as rang,minnotematiere(m.libelle,'TleC1',1) as min,maxnotematiere(m.libelle,'TleC1',1) as max,appreceate(note) as appreciations,e.nom,e.prenom,e.date_naissance,e.lieu_naissance
from Coefficient coef join Matiere m on (m.code=coef.code_matiere) join TypeMatiere tm on (tm.code=m.type) join Classe c on (c.code_serie=coef.code_serie) join EleveInscrit ei on (ei.code_classe=c.code) join Eleve e on (ei.code_eleve=e.code)  left join Notes n on (n.code_eleveinscrit=ei.code and n.code_coefficient=coef.code  and (n.numerosequence=1))  
where e.matricule like 'Enl4925'  and ei.isenabled like 'OUI'  ;


-- requete permettant de connaitre les resultats dun eleveinscrit à une sequence
  select * from (
  select result.*,@row_number:=@row_number+1 AS rang from  (
select e.matricule,sum(coef.valeur) as totalcoefs,sum(note*coef.valeur) as totalpts,sum(note*coef.valeur)/sum(coef.valeur) as moyseq,appreceate(sum(note*coef.valeur)/sum(coef.valeur) ) as mention 
from Coefficient coef join Matiere m on (m.code=coef.code_matiere) join Classe c on (c.code_serie=coef.code_serie) join EleveInscrit ei on (ei.code_classe=c.code) join Eleve e on (ei.code_eleve=e.code)  left join Notes n on (n.code_eleveinscrit=ei.code and n.code_coefficient=coef.code  and (n.numerosequence = 1 ))  
 where c.libelle like 'TleC1'  and ei.isenabled like 'OUI'   group by e.matricule  order by moyseq  desc
 ) as result,(SELECT @row_number:=0) AS t ) as result1  where matricule like 'Enl4925';



-- requete permettant de connaitre les resultats dune classe à une sequence


select max(moyseq),min(moyseq),avg(moyseq),count(moyseq),sum(moyennesup) from
(
select e.matricule,sum(coef.valeur) as totalcoefs,sum(note*coef.valeur) as totalpts,sum(note*coef.valeur)/sum(coef.valeur) as moyseq,if (sum(note*coef.valeur)/sum(coef.valeur)>10,1,0) as moyennesup 

from Coefficient coef join Matiere m on (m.code=coef.code_matiere) join Classe c on (c.code_serie=coef.code_serie) join EleveInscrit ei on (ei.code_classe=c.code and ei.isenabled like 'OUI') join Eleve e on (ei.code_eleve=e.code)  left join Notes n on (n.code_eleveinscrit=ei.code and n.code_coefficient=coef.code  and (n.numerosequence = 1))   where c.libelle like '6M1'    
group by e.matricule  order by moyseq  desc
) as result;


select ei.code,n.code as notecode,coef.code as coefcode,e.matricule,e.nom,e.prenom,n.note,n.isenabled from EleveInscrit ei  join Classe c on (c.code=ei.code_classe and c.code=205  and ei.isenabled like 'OUI')join Coefficient coef
on (coef.code_serie =c.code_serie) join Matiere m on (coef.code_matiere=m.code and m.code=1) join Eleve e on(ei.code_eleve=e.code) left join Notes n on (n.code_coefficient=coef.code and n.code_eleveinscrit=ei.code and n.numerosequence=1);

-- statitiques sequentiels

select classecode,max(moyseq),min(moyseq),avg(moyseq),count(elevecode),sum(moyennesup) from
(
select e.code as elevecode,c.code as classecode,sum(coef.valeur) as totalcoefs,sum(note*coef.valeur) as totalpts,sum(note*coef.valeur)/sum(coef.valeur) as moyseq,if (sum(note*coef.valeur)/sum(coef.valeur)>10,1,0) as moyennesup 
from Coefficient coef join Matiere m on (m.code=coef.code_matiere) join Classe c on (c.code_serie=coef.code_serie) join EleveInscrit ei on (ei.code_classe=c.code and ei.isenabled like 'OUI') join Eleve e on (ei.code_eleve=e.code)  left join Notes n on (n.code_eleveinscrit=ei.code and n.code_coefficient=coef.code  and (n.numerosequence = 1))       
group by e.code  
) as result  group by  classecode ;

-- statitiques trimestriels
select classecode,max(moyseq),min(moyseq),avg(moyseq),count(elevecode),sum(moyennesup) from
(
select elevecode,classecode,sum(coefvaleur) as totalcoefs,sum(note*coefvaleur) as totalpts,sum(note*coefvaleur)/sum(coefvaleur) as moyseq,if (sum(note*coefvaleur)/sum(coefvaleur)>10,1,0) as moyennesup 
 from (
select e.code as elevecode,c.code as classecode,coef.code as coefcode,s.code as seqcode,ei.code as eicode,coef.valeur as coefvaleur
from Coefficient coef join Matiere m on (m.code=coef.code_matiere) join Classe c on (c.code_serie=coef.code_serie) join EleveInscrit ei on (ei.code_classe=c.code and ei.isenabled like 'OUI') join Eleve e on (ei.code_eleve=e.code),sequence s
where  s.code in (1,2) ) as result1 
 left join Notes n on (n.code_eleveinscrit=eicode and n.code_coefficient=coefcode  and (n.numerosequence=seqcode))
 group by elevecode
) as result group by classecode;

select *,
if  (restetranche2=0,
if(cumulverse-tranche1-tranche2-tranche3<0,cumulverse-tranche1-tranche2-tranche3,0),-tranche3) as restetranche3
from(
select *,
if (restetranche1=0,
if(cumulverse-tranche1-tranche2<0,cumulverse-tranche1-tranche2,
0),-tranche2) as restetranche2
from(
select   *,
if (cumulverse<tranche1,cumulverse-tranche1,0) as restetranche1
from (
select e.matricule,e.nom,e.prenom,s.tranche1,s.tranche2,s.tranche3,ifnull(sum(r.montant),0) as cumulverse
from EleveInscrit ei join Eleve e on (ei.code_eleve=e.code and ei.code_classe=1 and ei.isenabled like 'OUI')  join Classe c on(c.code =ei.code_classe) join Serie s on (s.code=c.code_serie)
 left join Reglement r on (ei.code=r.code_eleveinscrit) group by ei.code
) as result
)as result1
)as result2;



select ei.code from EleveInscrit ei join Eleve e on (ei.code_eleve=e.code and ei.code_classe=1 and ei.isenabled like 'OUI')  