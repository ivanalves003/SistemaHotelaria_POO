import java.io.Serializable;

public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contadorReserva = 1000;

    private int idReserva;
    private Cliente cliente;
    private Quarto quarto;
    private String dataCheckIn;
    private String dataCheckOut;
    private int quantidadeDiarias; // Truque de universitário pra evitar mexer com parse de datas complexas

    public Reserva(Cliente cliente, Quarto quarto, String dataCheckIn, String dataCheckOut, int quantidadeDiarias) {
        this.idReserva = contadorReserva++;
        this.cliente = cliente;
        this.quarto = quarto;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.quantidadeDiarias = quantidadeDiarias;
    }

    public Cliente getCliente() { return cliente; }
    public Quarto getQuarto() { return quarto; }
    public int getQuantidadeDiarias() { return quantidadeDiarias; }

    @Override
    public String toString() {
        return "Reserva #" + idReserva + " | Cliente: " + cliente.getNome() + " | Quarto: " + quarto.getNumero();
    }
}