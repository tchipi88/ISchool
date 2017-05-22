-- request to fill data in detail
select tm.libelle as typematiere,m.libelle as discipline,coef.valeur as coef,n1.note as note1,n2.note as note2,@notefinal:=(ifnull(n1.note,0)+ifnull(n2.note,0))/2 as notefinal,@notefinal*coef.valeur as total,rangmatiere1(m.libelle,e.matricule,1,'TleC1') as rang,minnotematiere1(m.libelle,'TleC1',1) as min,maxnotematiere1(m.libelle,'TleC1',1) as max,appreceate(@notefinal) as appreciations,e.nom,e.prenom,e.date_naissance,e.lieu_naissance
from Coefficient coef join Matiere m on (m.code=coef.code_matiere) join TypeMatiere tm on (tm.code=m.type) join Classe c on (c.code_serie=coef.code_serie) join EleveInscrit ei on (ei.code_classe=c.code) join Eleve e on (ei.code_eleve=e.code)  left join Notes n1 on (n1.code_eleveinscrit=ei.code and n1.code_coefficient=coef.code  and n1.numerosequence=1)  left join Notes n2 on (n2.code_eleveinscrit=ei.code and n2.code_coefficient=coef.code  and n2.numerosequence=2) 
where e.matricule like 'Enl4925'  and ei.isenabled like 'OUI'  ;


-- requete permettant de connaitre les resultats dun eleveinscrit à un TRIMESTRE
 select * from (
  select result2.*,@row_number:=@row_number+1 AS rang from  (
 select matricule,sum(coefvaleur) as totalcoefs,sum(note*coefvaleur) as totalpts,sum(note*coefvaleur)/sum(coefvaleur) as moyseq,appreceate(sum(note*coefvaleur)/sum(coefvaleur) ) as mention 
 from (
select e.matricule,m.libelle,s.code as seqcode,coef.code as coefcode,ei.code as eicode,coef.valeur as coefvaleur
from Coefficient coef join Matiere m on (m.code=coef.code_matiere) join Classe c on (c.code_serie=coef.code_serie) join EleveInscrit ei on (ei.code_classe=c.code) join Eleve e on (ei.code_eleve=e.code),sequence s
where c.libelle like 'TleC1'  and ei.isenabled like 'OUI'  and s.code in (1,2) ) as result1 
 left join Notes n on (n.code_eleveinscrit=eicode and n.code_coefficient=coefcode  and (n.numerosequence=seqcode))
 group by matricule  order by moyseq  desc
  ) as result2,(SELECT @row_number:=0) AS t ) as result3  where matricule like 'Enl4925';



-- requete permettant de connaitre les resultats dune classe à une TRIMESTRE


select max(moyseq),min(moyseq),avg(moyseq),count(moyseq),sum(moyennesup) from
(
select matricule,sum(coefvaleur) as totalcoefs,sum(note*coefvaleur) as totalpts,sum(note*coefvaleur)/sum(coefvaleur) as moyseq,if (sum(note*coefvaleur)/sum(coefvaleur)>10,1,0) as moyennesup 
 from (
select e.matricule,m.libelle,s.code as seqcode,coef.code as coefcode,ei.code as eicode,coef.valeur as coefvaleur
from Coefficient coef join Matiere m on (m.code=coef.code_matiere) join Classe c on (c.code_serie=coef.code_serie) join EleveInscrit ei on (ei.code_classe=c.code) join Eleve e on (ei.code_eleve=e.code),sequence s
where c.libelle like 'TleC1'  and ei.isenabled like 'OUI'  and s.code in (1,2) ) as result1 
 left join Notes n on (n.code_eleveinscrit=eicode and n.code_coefficient=coefcode  and (n.numerosequence=seqcode))
 group by matricule  order by moyseq  desc
) as result;

select rang from (
 select result.*,@row_number:=@row_number+1 AS rang from  (
select e.matricule,m.libelle as discipline,coef.valeur as coef,n1.note as note1,n2.note as note2,@notefinal:=(ifnull(n1.note,0)+ifnull(n2.note,0))/2 as notefinal
from Coefficient coef join Matiere m on (m.code=coef.code_matiere) join Classe c on (c.code_serie=coef.code_serie) join EleveInscrit ei on (ei.code_classe=c.code) join Eleve e on (ei.code_eleve=e.code)  left join Notes n1 on (n1.code_eleveinscrit=ei.code and n1.code_coefficient=coef.code  and n1.numerosequence=1)  left join Notes n2 on (n2.code_eleveinscrit=ei.code and n2.code_coefficient=coef.code  and n2.numerosequence=2) 
where c.libelle like 'TleC1'  and ei.isenabled like 'OUI'  and m.libelle like 'Mathematique'  order by notefinal desc
) as result,(SELECT @row_number:=0) AS t ) as result1  where matricule like 'Enl4925';

