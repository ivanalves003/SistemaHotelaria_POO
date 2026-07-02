import java.io.Serializable;

public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contadorId = 1;

    protected int id;
    protected String nome;
    protected String documento;
    protected String telefone;

    public Pessoa(String nome, String documento, String telefone) {
        this.id = contadorId++;
        this.nome = nome;
        this.documento = documento;
        this.telefone = telefone;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDocumento() { return documento; }
}