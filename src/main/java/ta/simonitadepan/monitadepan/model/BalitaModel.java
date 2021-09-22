package ta.simonitadepan.monitadepan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "balita")
public class BalitaModel {

    @Id
    @GeneratedValue
    private Integer id_balita;

    @NotNull
    @Column(name = "name",nullable = false)
    private String name;

    @NotNull
    @Column(name = "birth_date",nullable = false)
    private Date birth_date;

    @NotNull
    @Column(name = "gender",nullable = false)
    private int gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pengguna",referencedColumnName = "id_pengguna",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel id_pengguna;

    public Integer getId_balita() {
        return id_balita;
    }

    public void setId_balita(Integer id_balita) {
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

    public UserModel getId_pengguna() {
        return id_pengguna;
    }

    public void setId_pengguna(UserModel id_pengguna) {
        this.id_pengguna = id_pengguna;
    }
}
