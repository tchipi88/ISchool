/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service.reports;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import com.tsoft.ischool.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

/**
 *
 * @author Tedongmo
 */
@Service
public class JournalCaisseReport {
    
//    @Column
//    @Temporal(javax.persistence.TemporalType.DATE)
//    private String date_jour;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ApplicationProperties app;
    
//    @Autowired
//    DefaultParams dafaultparams;
//
//    @Override
//    public String description() {
//        return ("Journal de Caisse"); //To change body of generated methods, choose Tools | Templates.
//    }
//

    public String process(String dateJour) throws Exception {

        Map params = new HashMap();

        params.put("date_jour", dateJour);
        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "JournalCaisse"
                + System.currentTimeMillis() + ".pdf";

        // Implementer le corps de l'etat
        buildReport(params, destfile, jdbcTemplate.getDataSource().getConnection());

        return destfile;
    }

//    @Override
//    public String run(HttpSession session, HttpServletRequest request, Model m) throws Exception {
//
//        Map params = new HashMap();
//
//        params.put("date_jour", request.getParameter("date_jour"));
//        File uploadedfile = new File("." + File.separator + "reports");
//        if (!uploadedfile.exists()) {
//            uploadedfile.mkdirs();
//        }
//        String destfile = uploadedfile.getAbsolutePath() + File.separator + "JournalCaisse"
//                + System.currentTimeMillis() + ".pdf";
//
//        // Implementer le corps de l'etat
//        buildReport(params, destfile, jdbcTemplate.getDataSource().getConnection());
//
//        return "download?file=" + destfile;
//    }

    public void buildReport(Map params, String destfile, Connection con) throws Exception {
        
        String sep = File.separator;
        com.lowagie.text.Document document = new com.lowagie.text.Document(com.lowagie.text.PageSize.A4);//instanciation du document pdf format paysage.
        String chemin = destfile;
        String date_debut = (String) params.get("date_jour");

        if(date_debut==null || StringUtils.isEmpty(date_debut)) date_debut = LocalDate.now().toString();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(chemin));
        Phrase phrase = new Phrase(new Chunk("Imprime le : "+ LocalDate.now().toString()+"\t\t", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, null)));
        document.setFooter(new HeaderFooter(phrase, true));
        document.open();

        //information about school
        String imgEntete = resourceLoader.getResource("classpath:ischool/reports/logo-ecole.png").getFile().getAbsolutePath();
        Table entete = new Table(1);
        entete.setBorderWidth(0);
        entete.setBorder(0);
        entete.setWidth(80);
        entete.setPadding(0);
        entete.setSpacing(0);

        if (!StringUtils.isEmpty(imgEntete)) {
            Image image3 = Image.getInstance(imgEntete);
            Cell img = new Cell();
            img.add(image3);
            invisible(img);
            entete.addCell(img);
        }

        //information about school
        ApplicationProperties.Ecole ecole = app.getEcole();
        Cell cell=null;
        if(!StringUtils.isEmpty(ecole.getNom())) {
            cell = new Cell(new Chunk(ecole.getNom(), FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, null)));
            invisible(cell);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            entete.addCell(cell);
        }

        if(!StringUtils.isEmpty(ecole.getSlogan())) {
            cell = new Cell(new Chunk(ecole.getSlogan(), FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, null)));
            invisible(cell);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            entete.addCell(cell);
        }

        if(!StringUtils.isEmpty(ecole.getTelephonePortable())) {
            cell = new Cell(new Chunk(ecole.getBoitePostale() + " Tel:" + ecole.getTelephonePortable(), FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, null)));
            invisible(cell);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            entete.addCell(cell);
        }

        document.add(entete);

        Paragraph prg = new Paragraph(new Chunk("\nJournal de caisse du "+date_debut, FontFactory.getFont(FontFactory.TIMES, 13, Font.BOLD, null)));
        prg.setAlignment(Element.ALIGN_CENTER);
        document.add(prg);

        Table tabdet = new Table(5);
        tabdet.setWidth(80);
        tabdet.setBorder(0);
        tabdet.setPadding(2);
        tabdet.setWidths(new int[]{8, 40, 25, 15, 15, 15, 15});
        
//        String[] champs = new String[]{"num", , ,"date_operation", "code_puce_destination", "montant", "balance"};
        String[] capts = new String[]{"Num","Name", "Method", "Reason", "Op Date","Payment", "Disburse"};
        for(String st: capts){
            cell = new Cell(new Chunk(st, FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, null)));
            invisible(cell);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabdet.addCell(cell);
        }
        tabdet.endHeaders();
        
//        String[] aggs = new String[]{"montant"};
        double[] aggs = new double[2];
        for (int i = 0; i < aggs.length; i++) {
            aggs[i] = 0;
        }
        
        DateFormat datf_show = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat datf = new SimpleDateFormat("yyyy/MM/dd");
        
        Date date_deb = datf.parse(formatDate(date_debut));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date_deb);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date date_f = cal.getTime();
        
        PreparedStatement stat = con.prepareStatement("select * from vuemouvementcaisse where date_enregistrement >= ? and date_enregistrement < ?");
        stat.setDate(1, new java.sql.Date(date_deb.getTime()));
        stat.setDate(2, new java.sql.Date(date_f.getTime()));
//        stat.setDate(2, date_s);
        DecimalFormat decf = new DecimalFormat("#,##0");
        Map<Integer, List<String>> maps = new HashMap();
        double cumul_versement = 0, cumul_sortie = 0, cumul_nbre=0;
        String st = "", date_old = null;
        System.out.println("Requetes journal paiement : "+stat.toString());
        int num_lign=0;
        Color color=null;
        ResultSet res = stat.executeQuery();
        while(res.next()){
//            Date date_op = res.getDate("datepaiement");
//            String dt="";
            color = (num_lign%2)==0 ? Color.LIGHT_GRAY : null;
//            if(date_op!=null) dt = datf_show.format(date_op);
//            st = dt;
//            if (date_old != null && !st.equals(date_old)) {
//                //Inserer les sous totaux
//                tabdet.addCell(getCell("Sous Total", 2, Element.ALIGN_RIGHT, Font.BOLD, color));
//                tabdet.addCell(getCell(decf.format(cumul_nbre), 1, Element.ALIGN_RIGHT, Font.BOLD, color));
//                tabdet.addCell(getCell(decf.format(cumul_montant), 2, Element.ALIGN_CENTER, Font.BOLD, color));
//                aggs[0] += cumul_nbre;
//                aggs[1] += cumul_montant;
//                cumul_nbre=0; cumul_nbre=0;
//            }
//            date_old=st;
//            cumul_nbre++;
            num_lign++;
//            tabdet.addCell(getCell(st, 1, Element.ALIGN_LEFT));

            tabdet.addCell(getCell(num_lign+"",1, Element.ALIGN_RIGHT, Font.NORMAL, color));
            tabdet.addCell(getCell(res.getString("nom_prenom"),1, Element.ALIGN_CENTER, Font.NORMAL, color));
            tabdet.addCell(getCell(res.getString("mode_paiement"),1, Element.ALIGN_CENTER, Font.NORMAL, color));
            tabdet.addCell(getCell(res.getString("motif"),1, Element.ALIGN_CENTER, Font.NORMAL, color));
            tabdet.addCell(getCell(datf_show.format(res.getDate("date_operation")),1, Element.ALIGN_CENTER, Font.NORMAL, color));

//            list.add(dt);
            double entree = res.getDouble("entree");
            tabdet.addCell(getCell(entree!= 0 ? decf.format(entree) : "",1, Element.ALIGN_RIGHT, Font.NORMAL, color));
            double sortie = res.getDouble("sortie");
            cumul_versement += entree;
            cumul_sortie += sortie;
            tabdet.addCell(getCell(sortie!= 0 ? decf.format(sortie) : "",1, Element.ALIGN_RIGHT, Font.NORMAL, color));
           
        }
        
//        if (!st.equals("")) {
//                //Inserer les sous totaux
//            tabdet.addCell(getCell("Sous Total", 2, Element.ALIGN_RIGHT, Font.BOLD, Color.LIGHT_GRAY));
//            tabdet.addCell(getCell(decf.format(cumul_nbre), 1, Element.ALIGN_RIGHT, Font.BOLD, Color.LIGHT_GRAY));
//            tabdet.addCell(getCell(decf.format(cumul_montant), 2, Element.ALIGN_CENTER, Font.BOLD, Color.LIGHT_GRAY));
//            aggs[0] += cumul_nbre;
//            aggs[1] += cumul_montant;
//        }
        tabdet.addCell(getCell("Total", 4, Element.ALIGN_CENTER, Font.BOLD));
        tabdet.addCell(getCell(decf.format(cumul_versement), 1, Element.ALIGN_RIGHT, Font.BOLD));
        tabdet.addCell(getCell(decf.format(cumul_sortie), 1, Element.ALIGN_RIGHT, Font.BOLD));
                
        document.add(tabdet);
        
        document.close();
        
    }
    
    public Cell getCell(String st, int colspan, int align) throws Exception {
        return getCell(st, colspan, align, Font.NORMAL);
    }

    public Cell getCell(String st, int colspan, int align, int font) throws Exception {
        return getCell(st, colspan, align, font, (Color)null);
    }

    public Cell getCell(String st, int colspan, int align, int font, Color color) throws Exception {
        Cell cell = new Cell(new Chunk(st, FontFactory.getFont(FontFactory.TIMES, 11, font, null)));
        cell.setBorder(1);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(align);
        cell.setBorderColor(Color.WHITE);
        if(color!=null) cell.setBackgroundColor(color);
        cell.setBorder(1);
        cell.setBorderColor(Color.WHITE);
        return cell;
    }
    
    public static void invisible(Cell cell) {
        cell.setBorder(1);
        cell.setBorderColor(Color.WHITE);
    }
    
    public String formatDate(String date){
        String[] tab = date.split("/");
        if(tab.length==1) tab = date.split("-");
        if(tab[0].length()==4) return date;
        String st = tab[2]+"/"+tab[1]+"/"+tab[0];
        return st;
    }

}
