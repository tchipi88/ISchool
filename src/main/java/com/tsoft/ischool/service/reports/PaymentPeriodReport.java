/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service.reports;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
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
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tsoft.ischool.config.ApplicationProperties;
import com.tsoft.ischool.domain.enumeration.CaisseMouvementMotif;
import com.tsoft.ischool.domain.enumeration.ModePaiement;
import com.tsoft.ischool.service.util.FileUtils;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tedongmo
 */
@RestController
@RequestMapping("/api")
public class PaymentPeriodReport {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ApplicationProperties app;

    DateFormat datf = new SimpleDateFormat("yyyy/MM/dd");



    @GetMapping(value = "/printPaymentPeriod",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> process(@RequestParam LocalDate dateDebut,@RequestParam LocalDate dateFin,
                           @RequestParam(required = false) ModePaiement modePaiement,@RequestParam(required = false) CaisseMouvementMotif motif) throws Exception {

        Map params = new HashMap();
        params.put("date_debut", dateDebut.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        params.put("date_fin", dateFin.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        if(modePaiement!=null) params.put("modePaiement", modePaiement.name());
        if(motif!=null) params.put("motifMouvement", motif.name());


        String destfile = FileUtils.getUploadedfile().getAbsolutePath() + File.separator  + "PaymentPeriod"
                + dateDebut.toString()+"To" +dateFin.toString()+ ".pdf";

        // Implementer le corps de l'etat
        buildReport(params, destfile, jdbcTemplate.getDataSource().getConnection());


        Resource resource = resourceLoader.getResource("file:" + destfile);
        InputStream in = resource.getInputStream();
        try {
            return new ResponseEntity<>(IOUtils.toByteArray(in), HeaderUtil.downloadAlert(resource), HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }


    public String generateReport(LocalDate dateDebut, LocalDate dateFin) throws Exception {

        Map params = new HashMap();
        params.put("date_debut", dateDebut.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        params.put("date_fin", dateFin.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));

//        if (modePaiement != null) params.put("modePaiement", modePaiement.name());
//        if (motif != null) params.put("motifMouvement", motif.name());


        String destfile = FileUtils.getUploadedfile().getAbsolutePath() + File.separator + "PaymentPeriod"
                + dateDebut.toString() + "To" + dateFin.toString() + ".pdf";

        // Implementer le corps de l'etat
        buildReport(params, destfile, jdbcTemplate.getDataSource().getConnection());
        return destfile;
    }

    public void buildReport(Map params, String destfile, Connection con) throws Exception {

        String sep = File.separator;
        com.lowagie.text.Document document = new com.lowagie.text.Document(com.lowagie.text.PageSize.A4);//instanciation du document pdf format paysage.
        String chemin = destfile;
        String date_debut = (String) params.get("date_debut");
        String date_fin = (String) params.get("date_fin");
        if (StringUtils.isEmpty(date_fin)) {
            date_fin = date_debut;
        }


        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(chemin));
        Phrase phrase = new Phrase(new Chunk("Print at : " + LocalDate.now().toString() + "\t\t", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, null)));
        document.setFooter(new HeaderFooter(phrase, true));
        document.open();

        Table entete = new Table(7);
        entete.setBorderWidth(0);
        entete.setBorder(0);
        entete.setWidth(100);
        entete.setPadding(2);
        entete.setSpacing(0);

        //information about school
        ApplicationProperties.Ecole ecole = app.getEcole();
        Cell cell=null;
        if(!StringUtils.isEmpty(ecole.getNom())) {
            cell = new Cell(new Chunk(ecole.getNom(), FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, null)));
            invisible(cell);
            cell.setColspan(3);
            cell.setRowspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            entete.addCell(cell);
        }

        String imgEntete = resourceLoader.getResource("classpath:ischool/reports/logo-ecole.png").getFile().getAbsolutePath();
        if (!StringUtils.isEmpty(imgEntete)) {
            Image image3 = Image.getInstance(imgEntete);
            image3.scaleAbsolute(150f, 150f);
            image3.setAbsolutePosition(400f, 550f);
            Cell img = new Cell();
            img.add(image3);
            img.setRowspan(2);
            invisible(img);
            entete.addCell(img);
        }

        if(!StringUtils.isEmpty(ecole.getSlogan())) {
            cell = new Cell(new Chunk(ecole.getSlogan(), FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, null)));
            invisible(cell);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(3);
            cell.setRowspan(2);
            entete.addCell(cell);
        }

        if(!StringUtils.isEmpty(ecole.getTelephonePortable())) {
            cell = new Cell(new Chunk(ecole.getBoitePostale() + " Tel:" + ecole.getTelephonePortable(), FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, null)));
            invisible(cell);
            cell.setColspan(7);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            entete.addCell(cell);
        }
        document.add(entete);

        Paragraph prg = new Paragraph(new Chunk("\nList of Payments of " + date_debut + " to " + date_fin, FontFactory.getFont(FontFactory.TIMES, 13, Font.BOLD, null)));
        prg.setAlignment(Element.ALIGN_CENTER);
        document.add(prg);

        Table tabdet = new Table(8);
        tabdet.setWidth(100);
        tabdet.setBorder(0);
        tabdet.setPadding(2);
        tabdet.setWidths(new int[]{7, 30, 15, 15, 15, 15, 12, 12});

//        String[] champs = new String[]{"reference", "date_operation", "code_puce_destination", "montant", "balance"};
        String[] capts = new String[]{"Num", "Name", "Record Date", "Method", "Reason", "Op Date", "Payment", "Disburse"};
        for (String st : capts) {
            cell = new Cell(new Chunk(st, FontFactory.getFont(FontFactory.TIMES, 11, Font.BOLD, null)));
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


        Date date_deb = datf.parse(formatDate(date_debut));
        Date date_f = datf.parse(formatDate(date_fin));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date_f);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        date_f = cal.getTime();

        String modePaie = (String) params.get("modePaiement");
        String motif = (String) params.get("motifMouvement");

        String query = "select * from vuemouvementcaisse where date_enregistrement >= ? and date_enregistrement < ?";
        if(!StringUtils.isEmpty(modePaie))
            query+= " and mode_paiement=? ";
        if(!StringUtils.isEmpty(motif))
            query+= " and motif=? ";

        query += " order by date_operation, date_enregistrement, nom_prenom ";
        PreparedStatement stat = con.prepareStatement(query);
        stat.setDate(1, new java.sql.Date(date_deb.getTime()));
        stat.setDate(2, new java.sql.Date(date_f.getTime()));
        int num_params = 3;
        if(!StringUtils.isEmpty(modePaie)) {
            stat.setString(num_params, modePaie);
            num_params++;
        }
        if(!StringUtils.isEmpty(motif)) {
            stat.setString(num_params, motif);
            num_params++;
        }

//        stat.setDate(2, date_s);
        DecimalFormat decf = new DecimalFormat("#,##0");
        DateFormat datf_show = new SimpleDateFormat("dd/MM/yyyy");
        Map<Integer, List<String>> maps = new HashMap();
        double cumul_entree = 0, cumul_sortie = 0, cumul_nbre = 0, cumul_total_entree=0, cumul_total_sortie=0;
        String st = "", date_old = null;
        System.out.println("Requetes paiement de periode : " + stat.toString());
        int num_lign = 0;
//        int compte_old=0;
        String dateOperationOld=null;
        Color color = null;
        ResultSet res = stat.executeQuery();
        while (res.next()) {
            color = (num_lign % 2) == 0 ? Color.LIGHT_GRAY : null;
            Date date_op = res.getDate("date_operation");
            String dt="";
            dt="";
            if(date_op!=null)
                dt = datf_show.format(date_op);
            if(!StringUtils.isEmpty(dateOperationOld) && !dt.equalsIgnoreCase(dateOperationOld)){
//                //Inserer les sous totaux
                tabdet.addCell(getCell("Sub Total", 6, Element.ALIGN_RIGHT, Font.BOLD));
//                tabdet.addCell(getCell(decf.format(cumul_nbre), 1, Element.ALIGN_RIGHT, Font.BOLD));
                tabdet.addCell(getCell(decf.format(cumul_entree), 1, Element.ALIGN_RIGHT, Font.BOLD));
                tabdet.addCell(getCell(decf.format(cumul_sortie), 1, Element.ALIGN_RIGHT, Font.BOLD));
                cumul_total_sortie += cumul_sortie;
                cumul_total_entree += cumul_entree;
                cumul_entree=0; cumul_nbre=0; cumul_sortie=0;
            }
            dateOperationOld = dt;
            num_lign++;

            tabdet.addCell(getCell(num_lign + "", 1, Element.ALIGN_RIGHT, Font.NORMAL, color));
            tabdet.addCell(getCell(res.getString("nom_prenom"), 1, Element.ALIGN_LEFT, Font.NORMAL, color));

            Date date_paie = res.getDate("date_enregistrement");
            dt="";
            if(date_paie!=null)
                dt = datf_show.format(date_paie);
            tabdet.addCell(getCell(dt, 1, Element.ALIGN_CENTER, Font.NORMAL, color));

            tabdet.addCell(getCell(res.getString("mode_paiement"), 1, Element.ALIGN_LEFT, Font.NORMAL, color));
            tabdet.addCell(getCell(res.getString("motif"), 1, Element.ALIGN_LEFT, Font.NORMAL, color));


            tabdet.addCell(getCell(dt, 1, Element.ALIGN_LEFT, Font.NORMAL, color));

//            list.add(dt);
            double entree = res.getDouble("entree");
            tabdet.addCell(getCell(entree != 0 ? decf.format(entree) : "", 1, Element.ALIGN_RIGHT, Font.NORMAL, color));
            double sortie = res.getDouble("sortie");
            cumul_entree += entree;
            cumul_sortie += sortie;
            tabdet.addCell(getCell(sortie != 0 ? decf.format(sortie) : "", 1, Element.ALIGN_RIGHT, Font.NORMAL, color));

        }

        if (!StringUtils.isEmpty(dateOperationOld)) {
                //Inserer les sous totaux
            tabdet.addCell(getCell("Sub Total", 6, Element.ALIGN_RIGHT, Font.BOLD));
//                tabdet.addCell(getCell(decf.format(cumul_nbre), 1, Element.ALIGN_RIGHT, Font.BOLD));
            tabdet.addCell(getCell(decf.format(cumul_entree), 1, Element.ALIGN_RIGHT, Font.BOLD));
            tabdet.addCell(getCell(decf.format(cumul_sortie), 1, Element.ALIGN_RIGHT, Font.BOLD));
            cumul_total_sortie += cumul_sortie;
            cumul_total_entree += cumul_entree;
        }
        tabdet.addCell(getCell("Total", 6, Element.ALIGN_CENTER, Font.BOLD));
//        tabdet.addCell(getCell(decf.format(num_lign), 1, Element.ALIGN_RIGHT, Font.BOLD));
        tabdet.addCell(getCell(decf.format(cumul_total_entree), 1, Element.ALIGN_RIGHT, Font.BOLD));
        tabdet.addCell(getCell(decf.format(cumul_total_sortie), 1, Element.ALIGN_RIGHT, Font.BOLD));

        document.add(tabdet);

        document.close();

    }

    public Cell getCell(String st, int colspan, int align) throws Exception {
        return getCell(st, colspan, align, Font.NORMAL);
    }

    public Cell getCell(String st, int colspan, int align, int font) throws Exception {
        return getCell(st, colspan, align, font, (Color) null);
    }

    public Cell getCell(String st, int colspan, int align, int font, Color color) throws Exception {
        Cell cell = new Cell(new Chunk(st==null? "": st, FontFactory.getFont(FontFactory.TIMES, 10, font, null)));
        cell.setBorder(1);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(align);
        cell.setBorderColor(Color.WHITE);
        if (color != null) {
            cell.setBackgroundColor(color);
        }
        cell.setBorder(1);
        cell.setBorderColor(Color.WHITE);
        return cell;
    }

    public static void invisible(Cell cell) {
        cell.setBorder(1);
        cell.setBorderColor(Color.WHITE);
    }

    public String formatDate(String date) {
        String[] tab = date.split("/");
        if (tab.length == 1) {
            tab = date.split("-");
        }
        if (tab[0].length() == 4) {
            return date;
        }
        String st = tab[2] + "/" + tab[1] + "/" + tab[0];
        return st;
    }


}
