package br.bandtec.com.projetoimove.domains;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Table(name = "tb_cartao")
@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cartao", nullable = false)
    private Integer id;
    @Column(name = "numero")
    private String numero;
    @Column(name = "nome")
    private String nome;
    @Column(name = "validade")
    private String validade;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "cvv")
    private String cvv;
    @Column(name = "preferencial")
    private Boolean preferencial;

    public Cartao(Integer id, String numero,String validade, String cpf, String nome, Boolean preferencial) {
        this.id = id;
        this.numero = numero;
        this.validade = validade;
        this.cpf = cpf;
        this.nome = nome;
        this.preferencial = preferencial;
    }

    public Cartao(){

    }


    public Boolean getPreferencial() {
        return preferencial;
    }

    public void setPreferencial(Boolean preferencial) {
        this.preferencial = preferencial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
