package com.tsoft.ischool.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 *
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Ecole ecole = new Ecole();

    public Ecole getEcole() {
        return ecole;
    }

    public static class Ecole {

        private String nom;

        private String email;

        private String telephonePortable;

        private String telephoneFixe;

        private String siteWeb;

        private String boitePostale;

        private String adresse;
        
        private String principal;
        
        private String slogan;
        
        private String logo;
        
      

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelephonePortable() {
            return telephonePortable;
        }

        public void setTelephonePortable(String telephonePortable) {
            this.telephonePortable = telephonePortable;
        }

        public String getTelephoneFixe() {
            return telephoneFixe;
        }

        public void setTelephoneFixe(String telephoneFixe) {
            this.telephoneFixe = telephoneFixe;
        }

        public String getSiteWeb() {
            return siteWeb;
        }

        public void setSiteWeb(String siteWeb) {
            this.siteWeb = siteWeb;
        }

        public String getBoitePostale() {
            return boitePostale;
        }

        public void setBoitePostale(String boitePostale) {
            this.boitePostale = boitePostale;
        }

        public String getAdresse() {
            return adresse;
        }

        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

       
        
        

    }
}
