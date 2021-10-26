package ta.simonitadepan.monitadepan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "faskes")
public class FaskesModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_faskes;

    @NotNull
    @Column(name="name",nullable = false)
    private String name;

    @NotNull
    @Column(name="type",nullable = false)
    private int type;

    @NotNull
    @Size(max=50)
    @Column(name = "username",nullable = false,unique = true)
    private String username;

    @NotNull
    @Lob
    @Column(name = "password", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String password;

    @Column(name = "sip",nullable = true)
    private String sip;

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

    @OneToMany(mappedBy = "id_faskes",fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<UserModel> listUser;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @NotNull
    @Column(name="email",nullable = false)
    private String email;

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public Long getId_faskes() {
        return id_faskes;
    }

    public void setId_faskes(Long id_faskes) {
        this.id_faskes = id_faskes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public List<UserModel> getListUser() {
        return listUser;
    }

    public void setListUser(List<UserModel> listUser) {
        this.listUser = listUser;
    }
}
