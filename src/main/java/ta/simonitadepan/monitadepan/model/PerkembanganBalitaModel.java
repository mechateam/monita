package ta.simonitadepan.monitadepan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "perkembangan_balita")
public class PerkembanganBalitaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_perkembangan;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "input_date",nullable = false)
    private Date input_date;

    @NotNull
    @Column(name = "input_age")
    private String input_age;

    @NotNull
    @Column(name = "diagnosis",nullable = false)
    private String diagnosis;

    @NotNull
    @Column(name = "deskripsi_diagnosis",nullable = false)
    private String deskripsi_diagnosis;

    @NotNull
    @Column(name = "diagnosis_gerak_halus", nullable = false)
    private String diagnosis_gerak_halus;

    @NotNull
    @Column(name = "diagnosis_gerak_kasar", nullable = false)
    private String diagnosis_gerak_kasar;

    @NotNull
    @Column(name = "diagnosis_bicara_bahasa", nullable = false)
    private String diagnosis_bicara_bahasa;

    @NotNull
    @Column(name = "diagnosis_sosialisasi", nullable = false)
    private String diagnosis_sosialisasi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_balita",referencedColumnName = "id_balita",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private BalitaModel idBalita;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_periode",referencedColumnName = "idPeriode",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PeriodePerkembanganModel idPeriode;

    public Long getId_perkembangan() {
        return id_perkembangan;
    }

    public void setId_perkembangan(Long id_perkembangan) {
        this.id_perkembangan = id_perkembangan;
    }

    public Date getInput_date() {
        return input_date;
    }

    public void setInput_date(Date input_date) {
        this.input_date = input_date;
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

    public BalitaModel getIdBalita() {
        return idBalita;
    }

    public void setIdBalita(BalitaModel idBalita) {
        this.idBalita = idBalita;
    }

    public PeriodePerkembanganModel getIdPeriode() {
        return idPeriode;
    }

    public void setIdPeriode(PeriodePerkembanganModel idPeriode) {
        this.idPeriode = idPeriode;
    }

    public String getDiagnosis_gerak_halus() {
        return diagnosis_gerak_halus;
    }

    public void setDiagnosis_gerak_halus(String diagnosis_gerak_halus) {
        this.diagnosis_gerak_halus = diagnosis_gerak_halus;
    }

    public String getDiagnosis_gerak_kasar() {
        return diagnosis_gerak_kasar;
    }

    public void setDiagnosis_gerak_kasar(String diagnosis_gerak_kasar) {
        this.diagnosis_gerak_kasar = diagnosis_gerak_kasar;
    }

    public String getDiagnosis_bicara_bahasa() {
        return diagnosis_bicara_bahasa;
    }

    public void setDiagnosis_bicara_bahasa(String diagnosis_bicara_bahasa) {
        this.diagnosis_bicara_bahasa = diagnosis_bicara_bahasa;
    }

    public String getDiagnosis_sosialisasi() {
        return diagnosis_sosialisasi;
    }

    public void setDiagnosis_sosialisasi(String diagnosis_sosialisasi) {
        this.diagnosis_sosialisasi = diagnosis_sosialisasi;
    }

    public String getInput_age() {
        return input_age;
    }

    public void setInput_age(String input_age) {
        this.input_age = input_age;
    }
}
