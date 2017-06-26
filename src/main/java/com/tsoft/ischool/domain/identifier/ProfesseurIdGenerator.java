/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain.identifier;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.joda.time.LocalDate;

/**
 *
 * @author tchipnangngansopa
 */
public class ProfesseurIdGenerator implements IdentifierGenerator {

    private final String defaultPrefix = "P";
    private final int defaultNumber = 1;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o)
            throws HibernateException {
        if (o instanceof Identifiable) {
            Identifiable identifiable = (Identifiable) o;
            Serializable id = identifiable.getId();
            if (id != null) {
                return id;
            }
        }
        String Id = "";
        String digits = "";
        Connection con = session.connection();
        try {
            java.sql.PreparedStatement pst = con
                    .prepareStatement("select  max(substr(id,1,3)) as id from professeur ");
            ResultSet rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                Id = rs.getString("id");
                try {
                    digits = String.format("%03d", Integer.parseInt(Id) + 1);
                } catch (Exception e) {
                    digits = String.format("%03d", defaultNumber);
                }
                Id = digits + defaultPrefix + LocalDate.now().getYear();
            } else {
                digits = String.format("%03d", defaultNumber);
                Id = digits + defaultPrefix + LocalDate.now().getYear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Id;
    }
}
