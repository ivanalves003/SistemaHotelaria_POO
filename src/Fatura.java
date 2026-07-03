import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Fatura implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contadorFatura = 5000;

    private int idFatura;
    private Reserva reserva;
    private double valorHospedagem;
    private double totalConsumo;
    private double valorTotal;

    public Fatura(Reserva reserva) {
        this.idFatura = contadorFatura++;
        this.reserva = reserva;
        this.totalConsumo = reserva.getQuarto().getMiniBar().getTotalConsumo();

        // RN06 - Cálculo da fatura (Diárias x Valor + Consumos)
        this.valorHospedagem = reserva.getQuantidadeDiarias() * reserva.getQuarto().getValorDiaria();
        this.valorTotal = this.valorHospedagem + this.totalConsumo;
    }

    public String gerarDetalhes() {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================\n");
        sb.append("        FATURA DE HOSPEDAGEM        \n");
        sb.append("====================================\n");
        sb.append("Fatura ID: ").append(idFatura).append("\n");
        sb.append("Cliente Titular: ").append(reserva.getCliente().getNome()).append("\n");
        sb.append("Quarto: ").append(reserva.getQuarto().getNumero()).append("\n");
        sb.append("Diárias: ").append(reserva.getQuantidadeDiarias()).append("x de R$ ").append(reserva.getQuarto().getValorDiaria()).append("\n");
        if (reserva.getDataHoraCheckIn() != null) {
            sb.append("Check-in realizado em: ").append(reserva.getDataHoraCheckIn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
        }
        sb.append("------------------------------------\n");
        sb.append("Total de Hospedagem: R$ ").append(valorHospedagem).append("\n");
        sb.append("Total de Consumos Extras: R$ ").append(totalConsumo).append("\n");
        sb.append("------------------------------------\n");
        sb.append("VALOR FINAL A PAGAR: R$ ").append(valorTotal).append("\n");
        sb.append("====================================");
        return sb.toString();
    }
}
