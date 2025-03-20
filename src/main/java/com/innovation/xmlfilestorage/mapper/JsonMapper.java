package com.innovation.xmlfilestorage.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovation.xmlfilestorage.exception.XmlParsingException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static com.innovation.xmlfilestorage.common.Constants.INVALID_XML_FORMAT;

@Component
@RequiredArgsConstructor
public class JsonMapper {

    @Value("${json.indent-factor}")
    private int jsonIndentFactor;

    private final ObjectMapper objectMapper;

    public String xmlToJson(MultipartFile file) {
        try {
            return XML.toJSONObject(new String(file.getBytes())).toString(jsonIndentFactor);
        } catch (IOException | JSONException e) {
            throw new XmlParsingException(INVALID_XML_FORMAT);
        }
    }

    public Map<String, Object> parseJson(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
