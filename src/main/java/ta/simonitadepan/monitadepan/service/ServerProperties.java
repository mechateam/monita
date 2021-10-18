package ta.simonitadepan.monitadepan.service;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "antopometri")
public class ServerProperties {
    private Map<Integer, Map<Integer,Float>> beratusialaki;
    private Map<Integer, Map<Integer,Float>> tbusialaki;
    private Map<Integer, Map<Integer,Float>> imtlaki;
    private Map<Float, Map<Integer,Float>> bbpertblaki0;
    private Map<Float, Map<Integer,Float>> bbpertblaki24;

    private Map<Integer, Map<Integer,Float>> beratusiaperempuan;
    private Map<Integer, Map<Integer,Float>> tbusiaperempuan;
    private Map<Integer, Map<Integer,Float>> imtperempuan;
    private Map<Float, Map<Integer,Float>> bbpertbperempuan0;
    private Map<Float, Map<Integer,Float>> bbpertbperempuan24;

    public Map<Float, Map<Integer, Float>> getBbpertblaki0() {
        return bbpertblaki0;
    }

    public void setBbpertblaki0(Map<Float, Map<Integer, Float>> bbpertblaki0) {
        this.bbpertblaki0 = bbpertblaki0;
    }

    public Map<Float, Map<Integer, Float>> getBbpertblaki24() {
        return bbpertblaki24;
    }

    public void setBbpertblaki24(Map<Float, Map<Integer, Float>> bbpertblaki24) {
        this.bbpertblaki24 = bbpertblaki24;
    }

    public Map<Float, Map<Integer, Float>> getBbpertbperempuan0() {
        return bbpertbperempuan0;
    }

    public void setBbpertbperempuan0(Map<Float, Map<Integer, Float>> bbpertbperempuan0) {
        this.bbpertbperempuan0 = bbpertbperempuan0;
    }

    public Map<Float, Map<Integer, Float>> getBbpertbperempuan24() {
        return bbpertbperempuan24;
    }

    public void setBbpertbperempuan24(Map<Float, Map<Integer, Float>> bbpertbperempuan24) {
        this.bbpertbperempuan24 = bbpertbperempuan24;
    }

    public Map<Integer, Map<Integer, Float>> getBeratusiaperempuan() {
        return beratusiaperempuan;
    }

    public void setBeratusiaperempuan(Map<Integer, Map<Integer, Float>> beratusiaperempuan) {
        this.beratusiaperempuan = beratusiaperempuan;
    }

    public Map<Integer, Map<Integer, Float>> getTbusiaperempuan() {
        return tbusiaperempuan;
    }

    public void setTbusiaperempuan(Map<Integer, Map<Integer, Float>> tbusiaperempuan) {
        this.tbusiaperempuan = tbusiaperempuan;
    }

    public Map<Integer, Map<Integer, Float>> getImtperempuan() {
        return imtperempuan;
    }

    public void setImtperempuan(Map<Integer, Map<Integer, Float>> imtperempuan) {
        this.imtperempuan = imtperempuan;
    }

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
