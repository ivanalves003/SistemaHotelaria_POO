import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DadosHotel implements Serializable {
    private static final long serialVersionUID = 1L;

    public List<Cliente> clientes = new ArrayList<>();
    public List<Quarto> quartos = new ArrayList<>();
    public List<Reserva> reservas = new ArrayList<>();
    public List<Produto> catalogo = new ArrayList<>();
    public List<TipoQuarto> tiposQuarto = new ArrayList<>();
}
