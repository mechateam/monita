package ta.simonitadepan.monitadepan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "periode_perkembangan")
public class PeriodePerkembanganModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_periode;

    @NotNull
    @Column(name = "periode",nullable = false)
    private Integer periode;

    @OneToMany(mappedBy = "id_periode",fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<PertanyaanPerkembanganModel> listPertanyaan;

    @OneToMany(mappedBy = "id_periode",fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<PerkembanganBalitaModel> listPerkembangan;

    public Long getId_periode() {
        return id_periode;
    }

    public void setId_periode(Long id_periode) {
        this.id_periode = id_periode;
    }

    public Integer getPeriode() {
        return periode;
    }

    public void setPeriode(Integer periode) {
        this.periode = periode;
    }

    public List<PertanyaanPerkembanganModel> getListPertanyaan() {
        return listPertanyaan;
    }

    public void setListPertanyaan(List<PertanyaanPerkembanganModel> listPertanyaan) {
        this.listPertanyaan = listPertanyaan;
    }

    public List<PerkembanganBalitaModel> getListPerkembangan() {
        return listPerkembangan;
    }

    public void setListPerkembangan(List<PerkembanganBalitaModel> listPerkembangan) {
        this.listPerkembangan = listPerkembangan;
    }
}
