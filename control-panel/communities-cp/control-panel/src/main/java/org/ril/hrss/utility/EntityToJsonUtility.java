package org.ril.hrss.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class EntityToJsonUtility {
    public String getEntityJson(Object o) throws JsonProcessingException {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String entityJson = ow.writeValueAsString(o);
        return entityJson;
    }


}
