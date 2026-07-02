import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private List<Dependente> dependentes;

    public Cliente(String nome, String documento, String telefone, String email) {
        super(nome, documento, telefone);
        this.email = email;
        this.dependentes = new ArrayList<>();
    }

    public void adicionarDependente(Dependente d) {
        this.dependentes.add(d);
    }

    public List<Dependente> getDependentes() { return dependentes; }
    public String getEmail() { return email; }

    // NÓS SÓ PRECISAMOS ADICIONAR ESSA LINHA AQUI:
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | CPF: " + documento + " | Dependentes: " + dependentes.size();
    }
}