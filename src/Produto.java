import java.io.Serializable;

public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contadorId = 1;

    private int id;
    private String nome;
    private double precoUnitario;
    private int quantidadeEstoque;

    public Produto(String nome, double precoUnitario, int quantidadeEstoque) {
        this.id = contadorId++;
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // RN04 - Reduz o estoque na hora do consumo
    public void reduzirEstoque(int qtd) throws HotelException {
        if (this.quantidadeEstoque < qtd) {
            throw new HotelException("Estoque insuficiente para o produto: " + nome);
        }
        this.quantidadeEstoque -= qtd;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPrecoUnitario() { return precoUnitario; }
    public int getQuantidadeEstoque() { return quantidadeEstoque; }

    @Override
    public String toString() {
        return id + " - " + nome + " | R$ " + precoUnitario + " | Estoque: " + quantidadeEstoque;
    }
}