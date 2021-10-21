package ta.simonitadepan.monitadepan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pertumbuhan_balita")
public class PertumbuhanBalitaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pertumbuhan;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "input_date",nullable = false)
    private Date input_date;

    @NotNull
    @Column(name = "input_age")
    private String input_age;

    @NotNull
    @Column(name = "berat_badan",nullable = false)
    private Float berat_badan;

    @NotNull
    @Column(name = "tinggi_badan",nullable = false)
    private Float tinggi_badan;

    @NotNull
    @Column(name = "diagnosis",nullable = false)
    private String diagnosis;

    @NotNull
    @Column(name = "deskripsi_diagnosis",nullable = false)
    private String deskripsi_diagnosis;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_balita",referencedColumnName = "id_balita",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private BalitaModel id_balita;

    public Long getId_pertumbuhan() {
        return id_pertumbuhan;
    }

    public void setId_pertumbuhan(Long id_pertumbuhan) {
        this.id_pertumbuhan = id_pertumbuhan;
    }

    public Date getInput_date() {
        return input_date;
    }

    public void setInput_date(Date input_date) {
        this.input_date = input_date;
    }

    public Float getBerat_badan() {
        return berat_badan;
    }

    public void setBerat_badan(Float berat_badan) {
        this.berat_badan = berat_badan;
    }

    public Float getTinggi_badan() {
        return tinggi_badan;
    }

    public void setTinggi_badan(Float tinggi_badan) {
        this.tinggi_badan = tinggi_badan;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDeskripsi_diagnosis() {
        return deskripsi_diagnosis;
    }

    public void setDeskripsi_diagnosis(String deskripsi_diagnosis) {
        this.deskripsi_diagnosis = deskripsi_diagnosis;
    }

    public BalitaModel getId_balita() {
        return id_balita;
    }

    public void setId_balita(BalitaModel id_balita) {
        this.id_balita = id_balita;
    }

    public String getInput_age() {
        return input_age;
    }

    public void setInput_age(String input_age) {
        this.input_age = input_age;
    }
}
