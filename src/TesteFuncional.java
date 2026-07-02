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
    }

    private static void simularErroReserva() throws HotelException {
        boolean quartoOcupado = true;
        if (quartoOcupado) {
            throw new HotelException("Não foi possível reservar: O quarto já está ocupado.");
        }
    }
}