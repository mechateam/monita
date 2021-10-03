package ta.simonitadepan.monitadepan.service;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "antopometri")
public class ServerProperties {
    private Map<Integer, Map<Integer,Float>> beratusialaki;
    private Map<Integer, Map<Integer,Float>> tbusialaki;
    private Map<Integer, Map<Integer,Float>> imtlaki;

    public Map<Integer, Map<Integer, Float>> getImtlaki() {
        return imtlaki;
    }

    public void setImtlaki(Map<Integer, Map<Integer, Float>> imtlaki) {
        this.imtlaki = imtlaki;
    }

    public Map<Integer, Map<Integer, Float>> getTbusialaki() {
        return tbusialaki;
    }

    public void setTbusialaki(Map<Integer, Map<Integer, Float>> tbusialaki) {
        this.tbusialaki = tbusialaki;
    }

    public Map<Integer, Map<Integer, Float>> getBeratusialaki() {
        return beratusialaki;
    }

    public void setBeratusialaki(Map<Integer, Map<Integer, Float>> beratusialaki) {
        this.beratusialaki = beratusialaki;
    }
}
