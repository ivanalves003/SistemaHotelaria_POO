import java.io.Serializable;

public class Quarto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numero;
    private TipoQuarto tipo;
    private double valorDiaria;
    private String status; // Disponível, Reservado, Ocupado
    private MiniBar miniBar; // Composição incluída!

    public Quarto(int numero, TipoQuarto tipo, double valorDiaria) {
        this.numero = numero;
        this.tipo = tipo;
        this.valorDiaria = valorDiaria;
        this.status = "Disponível";
        this.miniBar = new MiniBar();
    }

    public int getNumero() { return numero; }
    public TipoQuarto getTipo() { return tipo; }
    public String getStatus() { return status; }
    public double getValorDiaria() { return valorDiaria; }
    public MiniBar getMiniBar() { return miniBar; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Quarto " + numero + " (" + tipo.getNome() + ") - Status: " + status + " - Diária: R$ " + valorDiaria;
    }
}