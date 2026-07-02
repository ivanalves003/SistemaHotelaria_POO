public class GeradorDeDados {
    public static void main(String[] args) {
        try {
            DadosHotel bd = new DadosHotel();

            Cliente c1 = new Cliente("Ivan Alves Pires", "111.111.111-11", "(62) 91111-1111", "ivan@ufg.br");
            Cliente c2 = new Cliente("Nicole dos Santos Cassiano", "222.222.222-22", "(62) 92222-2222", "nicole@ufg.br");
            Cliente c3 = new Cliente("Isabella Rodrigues Mendonça", "333.333.333-33", "(62) 93333-3333", "isabella@ufg.br");

            bd.clientes.add(c1); bd.clientes.add(c2); bd.clientes.add(c3);

            TipoQuarto solteiro = new TipoQuarto("Solteiro Padrão", 1);
            TipoQuarto casal = new TipoQuarto("Casal Standard", 2);
            TipoQuarto master = new TipoQuarto("Suíte Master Luxo", 4);

            bd.quartos.add(new Quarto(101, solteiro, 150.00));
            bd.quartos.add(new Quarto(102, solteiro, 150.00));
            bd.quartos.add(new Quarto(201, casal, 250.00));
            bd.quartos.add(new Quarto(202, casal, 250.00));
            bd.quartos.add(new Quarto(301, master, 550.00));

            bd.catalogo.add(new Produto("Água Mineral 500ml", 5.00, 50));
            bd.catalogo.add(new Produto("Refrigerante Lata", 8.00, 40));
            bd.catalogo.add(new Produto("Suco de Uva Integral", 12.00, 20));
            bd.catalogo.add(new Produto("Chocolate em Barra", 15.00, 30));
            bd.catalogo.add(new Produto("Cerveja Artesanal", 18.00, 25));

            new GerenciadorArquivo().salvarDados(bd);
            System.out.println("Banco de dados resetado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}