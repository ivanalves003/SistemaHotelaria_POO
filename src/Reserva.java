import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contadorReserva = 1000;

    private int idReserva;
    private Cliente cliente;
    private Quarto quarto;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private int quantidadeDiarias;
    private LocalDateTime dataHoraCheckIn;

    public Reserva(Cliente cliente, Quarto quarto, LocalDate dataCheckIn, LocalDate dataCheckOut) {
        this.idReserva = contadorReserva++;
        this.cliente = cliente;
        this.quarto = quarto;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.quantidadeDiarias = (int) ChronoUnit.DAYS.between(dataCheckIn, dataCheckOut);
    }

    public Cliente getCliente() { return cliente; }
    public Quarto getQuarto() { return quarto; }
    public int getQuantidadeDiarias() { return quantidadeDiarias; }
    public LocalDate getDataCheckIn() { return dataCheckIn; }
    public LocalDate getDataCheckOut() { return dataCheckOut; }
    public LocalDateTime getDataHoraCheckIn() { return dataHoraCheckIn; }
    public void setDataHoraCheckIn(LocalDateTime dataHoraCheckIn) { this.dataHoraCheckIn = dataHoraCheckIn; }

    // RN01 - Duas reservas do mesmo quarto se sobrepõem se um intervalo começa antes do outro terminar (em ambos os sentidos).
    public static boolean seSobrepoe(LocalDate inicioA, LocalDate fimA, LocalDate inicioB, LocalDate fimB) {
        return inicioA.isBefore(fimB) && inicioB.isBefore(fimA);
    }

    @Override
    public String toString() {
        return "Reserva #" + idReserva + " | Cliente: " + cliente.getNome() + " | Quarto: " + quarto.getNumero();
    }
}
