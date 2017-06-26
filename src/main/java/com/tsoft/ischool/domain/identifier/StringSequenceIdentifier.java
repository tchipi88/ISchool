/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain.identifier;

import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 *
 * @author tchipnangngansopa
 */
public class StringSequenceIdentifier
        implements IdentifierGenerator, Configurable {

    public static final String SEQUENCE_PREFIX = "sequence_prefix";

    private String sequencePrefix;

    private String sequenceCallSyntax;

    @Override
    public void configure(
            Type type, Properties params, ServiceRegistry serviceRegistry)
            throws MappingException {
        final JdbcEnvironment jdbcEnvironment
                = serviceRegistry.getService(JdbcEnvironment.class);
        final Dialect dialect = jdbcEnvironment.getDialect();

        final ConfigurationService configurationService
                = serviceRegistry.getService(ConfigurationService.class);
        String globalEntityIdentifierPrefix
                = configurationService.getSetting("entity.identifier.prefix", String.class, "SEQ_");

        sequencePrefix = ConfigurationHelper.getString(
                SEQUENCE_PREFIX,
                params,
                globalEntityIdentifierPrefix);

        final String sequencePerEntitySuffix = ConfigurationHelper.getString(
                SequenceStyleGenerator.CONFIG_SEQUENCE_PER_ENTITY_SUFFIX,
                params,
                SequenceStyleGenerator.DEF_SEQUENCE_SUFFIX);

        final String defaultSequenceName = ConfigurationHelper.getBoolean(
                SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY,
                params,
                false)
                        ? params.getProperty(JPA_ENTITY_NAME) + sequencePerEntitySuffix
                        : SequenceStyleGenerator.DEF_SEQUENCE_NAME;

        sequenceCallSyntax = dialect.getSequenceNextValString(
                ConfigurationHelper.getString(
                        SequenceStyleGenerator.SEQUENCE_PARAM,
                        params,
                        defaultSequenceName));
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
//        if (obj instanceof Identifiable) {
//            Identifiable identifiable = (Identifiable) obj;
//            Serializable id = identifiable.getId();
//            if (id != null) {
//                return id;
//            }
//        }
        long seqValue = ((Number) Session.class.cast(session)
                .createSQLQuery(sequenceCallSyntax)
                .uniqueResult()).longValue();

        return sequencePrefix + String.format("%011d%s", 0, seqValue);
    }
}
