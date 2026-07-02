import java.io.Serializable;

public class Dependente extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idade;

    public Dependente(String nome, String documento, String telefone, int idade) {
        super(nome, documento, telefone);
        this.idade = idade;
    }

    public int getIdade() { return idade; }
}