package com.gm.BankXApp.config;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.descriptor.jdbc.*;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect(DialectResolutionInfo info) {
        super(info);
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new SQLiteIdentityColumnSupport(); // custom class
    }

    @Override
    public boolean hasAlterTable() {
        return false;
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }

    @Override
    public String getDropForeignKeyString() {
        return "";
    }

    @Override
    public String getAddForeignKeyConstraintString(String cn, String[] fk, String t, String[] pk, boolean rpk) {
        return "";
    }

    @Override
    public String getAddPrimaryKeyConstraintString(String constraintName) {
        return "";
    }

    @Override
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    @Override
    public boolean supportsCascadeDelete() {
        return false;
    }

    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.contributeTypes(typeContributions, serviceRegistry);

        var registry = typeContributions.getTypeConfiguration().getJdbcTypeRegistry();
        registry.addDescriptor(new IntegerJdbcType());
        registry.addDescriptor(new VarcharJdbcType());
        registry.addDescriptor(new FloatJdbcType());
        registry.addDescriptor(new DoubleJdbcType());
        registry.addDescriptor(new BooleanJdbcType());
        registry.addDescriptor(BlobJdbcType.BLOB_BINDING);
    }
}
