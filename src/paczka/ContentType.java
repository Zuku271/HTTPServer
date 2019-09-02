package paczka;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"html",
"xml",
"jpg"
})
public class ContentType {

@JsonProperty("html")
private String html;
@JsonProperty("xml")
private String xml;
@JsonProperty("jpg")
private String jpg;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("html")
public String getHtml() {
return html;
}


@JsonProperty("xml")
public String getXml() {
return xml;
}

@JsonProperty("jpg")
public String getJpg() {
return jpg;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}


}

