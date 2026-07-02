import java.io.*;

public class GerenciadorArquivo {
    private final String NOME_ARQUIVO = "dados_hotel.dat";

    public void salvarDados(DadosHotel dados) throws HotelException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            oos.writeObject(dados);
        } catch (IOException e) {
            throw new HotelException("Erro ao salvar os dados do sistema: " + e.getMessage());
        }
    }

    public DadosHotel carregarDados() {
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            return new DadosHotel();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (DadosHotel) ois.readObject();
        } catch (Exception e) {
            System.out.println("Aviso: Falha ao ler arquivo. Iniciando sistema vazio.");
            return new DadosHotel();
        }
    }
}