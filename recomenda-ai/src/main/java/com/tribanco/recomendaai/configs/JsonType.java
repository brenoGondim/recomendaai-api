package com.tribanco.recomendaai.configs;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.type.SimpleType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class JsonType implements UserType {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    private int[] sqlTypes;
    private com.fasterxml.jackson.databind.ObjectWriter writer;
    private JavaType type;
    private boolean isBinary;
    private ObjectReader reader;

    public JsonType() {
        init(SimpleType.constructUnsafe(Object.class), false);
    }

    public JsonType(Class clazz, boolean isBinary) {
        this(SimpleType.construct(clazz), isBinary);
    }

    public JsonType(JavaType type, boolean isBinary) {
        init(type, isBinary);
    }

    protected void init(JavaType type, boolean isBinary) {
        this.type = type;
        this.isBinary = isBinary;
        this.reader = MAPPER.readerFor(type);
        this.writer = MAPPER.writerFor(type);
        this.sqlTypes = new int[]{Types.JAVA_OBJECT};
    }


    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        } else if (x == null || y == null) {
            return false;
        } else {
            return x.equals(y);
        }
    }

    public int hashCode(Object x) throws HibernateException {
        return null == x ? 0 : x.hashCode();
    }

    public boolean isMutable() {
        return true;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        final Object result = rs.getObject(names[0]);
        if (!rs.wasNull()) {
            String content;

            if (result instanceof String) {
                content = (String) result;
            } else if (result instanceof PGobject) {
                // If we get directly the PGobject for some reason (more exactly, if a DB like H2 does the serialization directly)
                content = ((PGobject) result).getValue();
            } else {
                throw new IllegalArgumentException("Unknown object type (excepted pgobject or json string)");
            }
            if (content != null) {
                return convertJsonToObject(content);
            }
        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setObject(index, null);
            return;
        }
        PGobject pg = new PGobject();
        pg.setType(isBinary ? "jsonb" : "json");
        pg.setValue(convertObjectToJson(value));
        st.setObject(index, pg);
    }


    Object convertJsonToObject(String content) {
        try {
            return reader.readValue(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String convertObjectToJson(Object object) {
        try {
            return writer.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object deepCopy(Object value) throws HibernateException {
        String json = convertObjectToJson(value);
        return convertJsonToObject(json);
    }


    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return deepCopy(original);
    }


    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) deepCopy(value);
    }


    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return deepCopy(cached);
    }


    public int[] sqlTypes() {
        return sqlTypes;
    }


    public Class returnedClass() {
        return type.getRawClass();
    }
}
