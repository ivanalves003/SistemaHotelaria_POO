import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MiniBar implements Serializable {
    private static final long serialVersionUID = 1L;

    private double totalConsumo;
    private List<String> itensConsumidos; // Guarda o extrato para a fatura
    private List<Produto> produtosDisponiveis; // Agregação: produtos que este quarto oferece

    public MiniBar() {
        this.totalConsumo = 0.0;
        this.itensConsumidos = new ArrayList<>();
        this.produtosDisponiveis = new ArrayList<>();
    }

    public void adicionarProdutoAoMiniBar(Produto p) {
        this.produtosDisponiveis.add(p);
    }

    public List<Produto> getProdutosDisponiveis() { return produtosDisponiveis; }

    public void registrarConsumo(Produto p, int qtd) throws HotelException {
        if (!this.produtosDisponiveis.contains(p)) {
            throw new HotelException("Este produto não está disponível no minibar deste quarto.");
        }
        p.reduzirEstoque(qtd); // Tenta reduzir, se não tiver estoque lança a exceção
        double valorGasto = p.getPrecoUnitario() * qtd;
        this.totalConsumo += valorGasto;
        this.itensConsumidos.add(qtd + "x " + p.getNome() + " (R$ " + valorGasto + ")");
    }

    public double getTotalConsumo() { return totalConsumo; }
    public List<String> getItensConsumidos() { return itensConsumidos; }

    // Zera o minibar após o checkout
    public void limparParaProximoHospede() {
        this.totalConsumo = 0.0;
        this.itensConsumidos.clear();
    }
}
