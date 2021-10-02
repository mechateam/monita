package ta.simonitadepan.monitadepan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "pertanyaan_perkembangan")
public class PertanyaanPerkembanganModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pertanyaan;

    @NotNull
    @Column(name = "pertanyaan",nullable = false)
    private String pertanyaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_periode",referencedColumnName = "id_periode",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PeriodePerkembanganModel id_periode;

    public Long getId_pertanyaan() { return id_pertanyaan; }

    public void setId_pertanyaan(Long id_pertanyaan) { this.id_pertanyaan = id_pertanyaan;  }

    public String getPertanyaan() { return pertanyaan; }

    public void setPertanyaan(String pertanyaan) { this.pertanyaan = pertanyaan; }

    public PeriodePerkembanganModel getId_periode() { return id_periode;  }

    public void setId_periode(PeriodePerkembanganModel id_periode) { this.id_periode = id_periode;  }
}
