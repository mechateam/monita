package ta.simonitadepan.monitadepan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "pengguna")
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id_pengguna;

    @NotNull
    @Size(max=50)
    @Column(name = "username",nullable = false,unique = true)
    private String username;

    @NotNull
    @Lob
    @Column(name = "password", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String password;

    @NotNull
    @Column(name="name",nullable = false)
    private String name;

    @NotNull
    @Column(name="email",nullable = false)
    private String email;

    @NotNull
    @Column(name="phone",nullable = false)
    private String phone;

    @NotNull
    @Column(name="gender",nullable = false)
    private int gender;

    @NotNull
    @Column(name="address",nullable = false)
    private String address;

    @NotNull
    @Column(name="rt",nullable = false)
    private Integer rt;

    @NotNull
    @Column(name="rw",nullable = false)
    private Integer rw;

    @NotNull
    @Column(name="kelurahan",nullable = false)
    private String kelurahan;

    @NotNull
    @Column(name="nik",nullable = false)
    private String nik;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_faskes",referencedColumnName = "id_faskes", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private FaskesModel id_faskes;

    @OneToMany(mappedBy = "id_pengguna",fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<BalitaModel> listBalita;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    public Integer getRt() {
        return rt;
    }

    public void setRt(Integer rt) {
        this.rt = rt;
    }

    public Integer getRw() {
        return rw;
    }

    public void setRw(Integer rw) {
        this.rw = rw;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getId_pengguna() {
        return id_pengguna;
    }

    public void setId_pengguna(String id_pengguna) {
        this.id_pengguna = id_pengguna;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FaskesModel getId_faskes() {
        return id_faskes;
    }

    public void setId_faskes(FaskesModel id_faskes) {
        this.id_faskes = id_faskes;
    }

    public List<BalitaModel> getListBalita() {
        return listBalita;
    }

    public void setListBalita(List<BalitaModel> listBalita) {
        this.listBalita = listBalita;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }
}
