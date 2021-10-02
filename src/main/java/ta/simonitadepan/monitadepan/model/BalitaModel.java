package ta.simonitadepan.monitadepan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "balita")
public class BalitaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_balita;

    @NotNull
    @Column(name = "name",nullable = false)
    private String name;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date",nullable = false)
    private Date birth_date;

    @NotNull
    @Column(name = "gender",nullable = false)
    private int gender;

    @Column(name = "nik",nullable = true)
    private Long nik;

    @NotNull
    @Column(name = "status",nullable = false)
    private int status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pegguna",referencedColumnName = "id_pengguna",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel id_pengguna;

    @OneToMany(mappedBy = "id_balita",fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<PertumbuhanBalitaModel> listPertumbuhan;

    @OneToMany(mappedBy = "id_balita",fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<PerkembanganBalitaModel> listPerkembangan;

    public Long getId_balita() {
        return id_balita;
    }

    public void setId_balita(Long id_balita) {
        this.id_balita = id_balita;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Long getNik() { return nik; }

    public void setNik(Long nik) { this.nik = nik; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public UserModel getId_pengguna() {
        return id_pengguna;
    }

    public void setId_pengguna(UserModel id_pengguna) {
        this.id_pengguna = id_pengguna;
    }

    public List<PertumbuhanBalitaModel> getListPertumbuhan() { return listPertumbuhan; }

    public void setListPertumbuhan(List<PertumbuhanBalitaModel> listPertumbuhan) { this.listPertumbuhan = listPertumbuhan; }

    public List<PerkembanganBalitaModel> getListPerkembangan() { return listPerkembangan; }

    public void setListPerkembangan(List<PerkembanganBalitaModel> listPerkembangan) { this.listPerkembangan = listPerkembangan; }
}
