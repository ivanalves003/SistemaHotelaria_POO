import java.io.Serializable;

public class TipoQuarto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private int capacidadeMaxima;

    public TipoQuarto(String nome, int capacidadeMaxima) {
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public String getNome() { return nome; }
    public int getCapacidadeMaxima() { return capacidadeMaxima; }
}