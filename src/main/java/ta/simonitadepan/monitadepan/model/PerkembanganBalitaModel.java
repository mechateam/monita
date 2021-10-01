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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_periode",referencedColumnName = "id_periode",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PeriodePerkembanganModel id_periode;

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

    public BalitaModel getId_balita() {
        return id_balita;
    }

    public void setId_balita(BalitaModel id_balita) {
        this.id_balita = id_balita;
    }

    public PeriodePerkembanganModel getId_periode() {
        return id_periode;
    }

    public void setId_periode(PeriodePerkembanganModel id_periode) {
        this.id_periode = id_periode;
    }
}
