import java.time.LocalDate;
import java.time.LocalDateTime;

public class TesteFuncional {
    public static void main(String[] args) {
        System.out.println("Executando casos de teste...\n");

        Cliente c1 = new Cliente("João", "111", "999", "joao@email.com");
        Cliente c2 = new Cliente("Maria", "222", "888", "maria@email.com");

        System.out.println("Teste 1 - ID Automático:");
        System.out.println("Esperado: IDs diferentes (sequenciais)");
        System.out.println("Resultado: " + c1.getNome() + " (ID: " + c1.getId() + "), " + c2.getNome() + " (ID: " + c2.getId() + ")");
        System.out.println(c1.getId() != c2.getId() ? "[PASSOU]\n" : "[FALHOU]\n");

        System.out.println("Teste 2 - Tratamento de Exceção Própria:");
        try {
            simularErroReserva();
            System.out.println("[FALHOU] - A exceção não foi lançada.");
        } catch (HotelException e) {
            System.out.println("Exceção capturada com sucesso: " + e.getMessage());
            System.out.println("[PASSOU]");
        }

        teste3SettersDePessoa();
        teste4ReservaDiariasESobreposicao();
        teste5MiniBarAgregacaoDeProdutos();
        teste6FaturaExibeCheckIn();
    }

    private static void simularErroReserva() throws HotelException {
        boolean quartoOcupado = true;
        if (quartoOcupado) {
            throw new HotelException("Não foi possível reservar: O quarto já está ocupado.");
        }
    }

    private static void teste3SettersDePessoa() {
        System.out.println("\nTeste 3 - Setters de Pessoa (nome, telefone, documento):");
        Cliente c = new Cliente("Nome Antigo", "000.000.000-00", "(00) 0000-0000", "antigo@email.com");
        c.setNome("Nome Novo");
        c.setTelefone("(11) 91111-1111");
        c.setDocumento("111.111.111-11");
        boolean ok = c.getNome().equals("Nome Novo")
                && c.getTelefone().equals("(11) 91111-1111")
                && c.getDocumento().equals("111.111.111-11");
        System.out.println("Resultado: nome=" + c.getNome() + " | telefone=" + c.getTelefone() + " | documento=" + c.getDocumento());
        System.out.println(ok ? "[PASSOU]" : "[FALHOU]");
    }

    private static void teste4ReservaDiariasESobreposicao() {
        System.out.println("\nTeste 4 - Diárias automáticas e sobreposição de datas:");
        Cliente cliente = new Cliente("Teste", "999", "999", "teste@email.com");
        TipoQuarto tipo = new TipoQuarto("Solteiro", 1);
        Quarto quarto = new Quarto(999, tipo, 100.0);

        Reserva r1 = new Reserva(cliente, quarto, LocalDate.of(2026, 8, 10), LocalDate.of(2026, 8, 15));
        boolean diariasOk = r1.getQuantidadeDiarias() == 5;
        System.out.println("Diárias esperadas: 5 | Resultado: " + r1.getQuantidadeDiarias());
        System.out.println(diariasOk ? "[PASSOU]" : "[FALHOU]");

        boolean sobrepoe = Reserva.seSobrepoe(
                LocalDate.of(2026, 8, 12), LocalDate.of(2026, 8, 20),
                r1.getDataCheckIn(), r1.getDataCheckOut());
        System.out.println("Sobreposição esperada: true | Resultado: " + sobrepoe);
        System.out.println(sobrepoe ? "[PASSOU]" : "[FALHOU]");

        boolean naoSobrepoe = Reserva.seSobrepoe(
                LocalDate.of(2026, 8, 15), LocalDate.of(2026, 8, 20),
                r1.getDataCheckIn(), r1.getDataCheckOut());
        System.out.println("Sobreposição esperada: false (dia de saída = dia de entrada da outra) | Resultado: " + naoSobrepoe);
        System.out.println(!naoSobrepoe ? "[PASSOU]" : "[FALHOU]");
    }

    private static void teste5MiniBarAgregacaoDeProdutos() {
        System.out.println("\nTeste 5 - MiniBar só aceita consumo de produtos que ele mesmo oferece:");
        MiniBar miniBar = new MiniBar();
        Produto agua = new Produto("Água Mineral", 5.0, 10);
        Produto refri = new Produto("Refrigerante", 8.0, 10);

        boolean bloqueouProdutoForaDaLista;
        try {
            miniBar.registrarConsumo(agua, 1);
            bloqueouProdutoForaDaLista = false;
        } catch (HotelException e) {
            bloqueouProdutoForaDaLista = true;
        }
        System.out.println("Esperado: bloqueado (água não foi adicionada ao minibar) | Resultado: " + bloqueouProdutoForaDaLista);
        System.out.println(bloqueouProdutoForaDaLista ? "[PASSOU]" : "[FALHOU]");

        miniBar.adicionarProdutoAoMiniBar(refri);
        boolean consumoOk;
        try {
            miniBar.registrarConsumo(refri, 2);
            consumoOk = refri.getQuantidadeEstoque() == 8 && miniBar.getTotalConsumo() == 16.0;
        } catch (HotelException e) {
            consumoOk = false;
        }
        System.out.println("Esperado: consumo aceito, estoque=8, total=16.0 | Resultado: estoque=" + refri.getQuantidadeEstoque() + ", total=" + miniBar.getTotalConsumo());
        System.out.println(consumoOk ? "[PASSOU]" : "[FALHOU]");
    }

    private static void teste6FaturaExibeCheckIn() {
        System.out.println("\nTeste 6 - Fatura exibe data/hora do check-in quando preenchida:");
        Cliente cliente = new Cliente("Teste Fatura", "888", "888", "fatura@email.com");
        TipoQuarto tipo = new TipoQuarto("Solteiro", 1);
        Quarto quarto = new Quarto(998, tipo, 100.0);
        Reserva reserva = new Reserva(cliente, quarto, LocalDate.of(2026, 8, 10), LocalDate.of(2026, 8, 12));

        Fatura semCheckIn = new Fatura(reserva);
        boolean semLinha = !semCheckIn.gerarDetalhes().contains("Check-in realizado em");
        System.out.println("Esperado: sem linha de check-in | Contém a linha? " + !semLinha);
        System.out.println(semLinha ? "[PASSOU]" : "[FALHOU]");

        reserva.setDataHoraCheckIn(LocalDateTime.of(2026, 8, 10, 14, 30));
        Fatura comCheckIn = new Fatura(reserva);
        boolean comLinha = comCheckIn.gerarDetalhes().contains("Check-in realizado em: 10/08/2026 14:30");
        System.out.println("Esperado: contém 'Check-in realizado em: 10/08/2026 14:30' | Resultado contém? " + comLinha);
        System.out.println(comLinha ? "[PASSOU]" : "[FALHOU]");
    }
}
