package org.reservation.system.util;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

class DateHandler extends StdDeserializer<Date>
{ public DateHandler() { this(null); }
    public DateHandler(Class<?> clazz) { super(clazz); }
    @Override public Date deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException
    { String date = jsonparser.getText(); try { SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date); } catch (Exception e) { return null;
    }
    }
}


