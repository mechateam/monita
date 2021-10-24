package ta.simonitadepan.monitadepan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "imunisasi")
public class ImunisasiModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_imunisasi;

    @NotNull
    @Column(name="periode",nullable = false)
    private String periode;

    @NotNull
    @Column(name="name",nullable = false)
    private String name;

    @NotNull
    @Column(name="deskripsi",nullable = false)
    private String deskripsi;

    @Column(name="status",nullable = true)
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_balita",referencedColumnName = "id_balita",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private BalitaModel balita;

    public Long getId_imunisasi() {
        return id_imunisasi;
    }

    public void setId_imunisasi(Long id_imunisasi) {
        this.id_imunisasi = id_imunisasi;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BalitaModel getBalita() {
        return balita;
    }

    public void setBalita(BalitaModel balita) {
        this.balita = balita;
    }
}
