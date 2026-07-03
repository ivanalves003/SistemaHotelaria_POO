import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaSistema {
    private DadosHotel bd;
    private GerenciadorArquivo arquivo;
    private JFrame janela;

    public TelaSistema() {
        arquivo = new GerenciadorArquivo();
        bd = arquivo.carregarDados();
        configurarInterface();
    }

    private void configurarInterface() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { UIManager.setLookAndFeel(info.getClassName()); break; }
            }
        } catch (Exception e) {}

        Font fonteGrande = new Font("Segoe UI", Font.PLAIN, 16);
        Font fonteBotoes = new Font("Segoe UI", Font.BOLD, 14);
        Color corFundo = new Color(245, 247, 250);
        Color corTexto = new Color(50, 50, 50);

        UIManager.put("OptionPane.messageFont", fonteGrande);
        UIManager.put("OptionPane.buttonFont", fonteBotoes);
        UIManager.put("TextField.font", fonteGrande);
        UIManager.put("OptionPane.background", corFundo);
        UIManager.put("Panel.background", corFundo);
        UIManager.put("OptionPane.messageForeground", corTexto);

        janela = new JFrame("Sistema Hoteleiro - Gestão");
        janela.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        janela.setSize(650, 450);
        janela.setLocationRelativeTo(null);
        janela.getContentPane().setBackground(corFundo);
        janela.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Sistema Hoteleiro", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titulo.setForeground(new Color(70, 90, 120));

        JLabel subtitulo = new JLabel("Atendimento e Operações", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitulo.setForeground(new Color(120, 120, 120));

        JPanel painelTextos = new JPanel(new GridLayout(2, 1));
        painelTextos.setOpaque(false);
        painelTextos.setBorder(BorderFactory.createEmptyBorder(120, 0, 0, 0));
        painelTextos.add(titulo);
        painelTextos.add(subtitulo);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setOpaque(false);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));

        JButton btnMenu = new JButton("Acessar Painel");
        btnMenu.setFont(fonteBotoes);
        btnMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMenu.addActionListener(e -> abrirMenu());

        JButton btnSair = new JButton("Salvar e Fechar");
        btnSair.setFont(fonteBotoes);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> salvarESair());

        painelBotoes.add(btnMenu);
        painelBotoes.add(Box.createRigidArea(new Dimension(20, 0)));
        painelBotoes.add(btnSair);

        janela.add(painelTextos, BorderLayout.CENTER);
        janela.add(painelBotoes, BorderLayout.SOUTH);
    }

    public void iniciar() { janela.setVisible(true); }

    private void abrirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            String menu = "    PAINEL OPERACIONAL    \n"
                    + "1. Cadastrar Cliente Titular\n"
                    + "2. Adicionar Dependente\n"
                    + "3. Alterar E-mail do Cliente\n"
                    + "4. Excluir Cliente\n"
                    + "5. Cadastrar Quarto\n"
                    + "6. Cadastrar Produto no Catálogo\n"
                    + "7. Fazer Reserva\n"
                    + "8. Fazer Check-in\n"
                    + "9. Lançar Consumo\n"
                    + "10. Fazer Check-out / Gerar Fatura\n"
                    + "11. Relatórios do Sistema\n"
                    + "0. Voltar\n\n"
                    + "Escolha uma opção:";

            try {
                String entrada = JOptionPane.showInputDialog(janela, menu, "Menu", JOptionPane.PLAIN_MESSAGE);
                if (entrada == null) { opcao = 0; }
                else {
                    opcao = Integer.parseInt(entrada);
                    if (opcao != 0) processarOpcao(opcao);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(janela, "Opção inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1: cadastrarCliente(); break;
            case 2: adicionarDependente(); break;
            case 3: alterarCliente(); break;
            case 4: excluirCliente(); break;
            case 5: cadastrarQuarto(); break;
            case 6: cadastrarProduto(); break;
            case 7: fazerReserva(); break;
            case 8: fazerCheckIn(); break;
            case 9: lancarConsumo(); break;
            case 10: fazerCheckOut(); break;
            case 11: relatorios(); break;
            default: JOptionPane.showMessageDialog(janela, "Opção inválida.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // --- MÓDULO DE CADASTROS ---
    private void cadastrarCliente() {
        String nome = JOptionPane.showInputDialog(janela, "Nome do titular:");
        if (nome == null || nome.isEmpty()) return;
        String cpf = JOptionPane.showInputDialog(janela, "CPF:");
        String tel = JOptionPane.showInputDialog(janela, "Telefone:");
        String email = JOptionPane.showInputDialog(janela, "E-mail:");
        Cliente c = new Cliente(nome, cpf, tel, email);
        bd.clientes.add(c);
        JOptionPane.showMessageDialog(janela, "Cliente cadastrado! ID: " + c.getId(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void adicionarDependente() {
        try {
            int idCliente = Integer.parseInt(JOptionPane.showInputDialog(janela, "ID do Cliente Titular:"));
            Cliente cliente = bd.clientes.stream().filter(c -> c.getId() == idCliente).findFirst().orElse(null);
            if (cliente == null) throw new HotelException("Cliente não encontrado.");

            Reserva reservaAtiva = bd.reservas.stream()
                    .filter(r -> r.getCliente().getId() == idCliente && !r.getQuarto().getStatus().equals("Disponível"))
                    .findFirst().orElse(null);

            if (reservaAtiva != null) {
                int totalPessoasAtual = 1 + cliente.getDependentes().size();
                int capacidadeQuarto = reservaAtiva.getQuarto().getTipo().getCapacidadeMaxima();
                if (totalPessoasAtual >= capacidadeQuarto) {
                    throw new HotelException("Capacidade do quarto excedida! Não é possível adicionar dependente com a reserva atual.");
                }
            }

            String nome = JOptionPane.showInputDialog(janela, "Nome do Dependente:");
            int idade = Integer.parseInt(JOptionPane.showInputDialog(janela, "Idade:"));
            Dependente d = new Dependente(nome, "", "", idade);
            cliente.adicionarDependente(d);
            JOptionPane.showMessageDialog(janela, "Dependente adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (HotelException e) {
            JOptionPane.showMessageDialog(janela, e.getMessage(), "Bloqueio de Regra", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(janela, "Dados inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarCliente() {
        try {
            String idStr = JOptionPane.showInputDialog(janela, "Digite o ID do cliente que deseja alterar:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);

            Cliente cliente = bd.clientes.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
            if (cliente == null) throw new HotelException("Cliente não encontrado.");

            String novoEmail = JOptionPane.showInputDialog(janela, "Novo E-mail (atual: " + cliente.getEmail() + "):");
            if (novoEmail != null && !novoEmail.isEmpty()) {
                cliente.setEmail(novoEmail);
                JOptionPane.showMessageDialog(janela, "Dados alterados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (HotelException e) {
            JOptionPane.showMessageDialog(janela, e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(janela, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirCliente() {
        try {
            String idStr = JOptionPane.showInputDialog(janela, "ID do cliente para excluir:");
            if (idStr == null) return;

            int id = Integer.parseInt(idStr);
            boolean removeu = bd.clientes.removeIf(c -> c.getId() == id);

            if (removeu) {
                JOptionPane.showMessageDialog(janela, "Cliente excluído com sucesso.", "Exclusão", JOptionPane.INFORMATION_MESSAGE);
            } else {
                throw new HotelException("Cliente com ID " + id + " não encontrado.");
            }
        } catch (HotelException e) {
            JOptionPane.showMessageDialog(janela, e.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(janela, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarQuarto() {
        try {
            int num = Integer.parseInt(JOptionPane.showInputDialog(janela, "Número do Quarto:"));
            String tipoNome = JOptionPane.showInputDialog(janela, "Tipo (Ex: Casal):");
            int capacidade = Integer.parseInt(JOptionPane.showInputDialog(janela, "Capacidade Máxima de Pessoas:"));
            double valor = Double.parseDouble(JOptionPane.showInputDialog(janela, "Valor da Diária:"));

            TipoQuarto tipo = new TipoQuarto(tipoNome, capacidade);
            Quarto q = new Quarto(num, tipo, valor);
            bd.quartos.add(q);
            JOptionPane.showMessageDialog(janela, "Quarto " + num + " cadastrado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(janela, "Dados inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarProduto() {
        try {
            String nome = JOptionPane.showInputDialog(janela, "Nome do Produto:");
            if (nome == null || nome.isEmpty()) return;
            double preco = Double.parseDouble(JOptionPane.showInputDialog(janela, "Preço Unitário:"));
            int qtd = Integer.parseInt(JOptionPane.showInputDialog(janela, "Quantidade em Estoque:"));

            Produto p = new Produto(nome, preco, qtd);
            bd.catalogo.add(p);
            JOptionPane.showMessageDialog(janela, "Produto cadastrado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(janela, "Dados inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- MÓDULO OPERACIONAL ---
    private void fazerReserva() {
        try {
            int idCliente = Integer.parseInt(JOptionPane.showInputDialog(janela, "ID do Cliente:"));
            Cliente cliente = bd.clientes.stream().filter(c -> c.getId() == idCliente).findFirst().orElse(null);
            if (cliente == null) throw new HotelException("Cliente não encontrado.");

            int numQuarto = Integer.parseInt(JOptionPane.showInputDialog(janela, "Número do Quarto:"));
            Quarto quarto = bd.quartos.stream().filter(q -> q.getNumero() == numQuarto).findFirst().orElse(null);
            if (quarto == null) throw new HotelException("Quarto não encontrado.");

            if (!quarto.getStatus().equals("Disponível")) throw new HotelException("Quarto não está disponível no momento.");

            int totalOcupantes = 1 + cliente.getDependentes().size();
            if (totalOcupantes > quarto.getTipo().getCapacidadeMaxima()) {
                throw new HotelException("Capacidade excedida. Hóspedes: " + totalOcupantes + " | Capacidade do Quarto: " + quarto.getTipo().getCapacidadeMaxima());
            }

            String entrada = JOptionPane.showInputDialog(janela, "Data Entrada (DD/MM/AAAA):");
            String saida = JOptionPane.showInputDialog(janela, "Data Saída (DD/MM/AAAA):");
            int diarias = Integer.parseInt(JOptionPane.showInputDialog(janela, "Quantidade de Diárias previstas:"));

            Reserva r = new Reserva(cliente, quarto, entrada, saida, diarias);
            quarto.setStatus("Reservado");
            bd.reservas.add(r);

            JOptionPane.showMessageDialog(janela, "Reserva efetuada com sucesso!\n" + r.toString(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (HotelException e) {
            JOptionPane.showMessageDialog(janela, e.getMessage(), "Aviso do Sistema", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(janela, "Entrada inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fazerCheckIn() {
        try {
            int numQuarto = Integer.parseInt(JOptionPane.showInputDialog(janela, "Número do Quarto reservado:"));
            Quarto quarto = bd.quartos.stream().filter(q -> q.getNumero() == numQuarto).findFirst().orElse(null);

            if (quarto == null) throw new HotelException("Quarto não encontrado.");
            if (!quarto.getStatus().equals("Reservado")) throw new HotelException("Apenas quartos 'Reservados' podem fazer Check-in.");

            quarto.setStatus("Ocupado");
            JOptionPane.showMessageDialog(janela, "Check-in realizado! Quarto " + numQuarto + " está agora Ocupado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (HotelException e) {
            JOptionPane.showMessageDialog(janela, e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {}
    }

    private void lancarConsumo() {
        try {
            int numQuarto = Integer.parseInt(JOptionPane.showInputDialog(janela, "Número do Quarto:"));
            Quarto quarto = bd.quartos.stream().filter(q -> q.getNumero() == numQuarto).findFirst().orElse(null);

            if (quarto == null) throw new HotelException("Quarto não encontrado.");
            if (!quarto.getStatus().equals("Ocupado")) throw new HotelException("Só é possível lançar consumo em quartos ocupados.");

            if (bd.catalogo.isEmpty()) throw new HotelException("O catálogo de produtos está vazio.");

            StringBuilder listaProd = new StringBuilder("ID - Produto (Preço - Estoque)\n");
            for (Produto p : bd.catalogo) { listaProd.append(p.getId()).append(" - ").append(p.getNome()).append(" (R$ ").append(p.getPrecoUnitario()).append(" - Qtd: ").append(p.getQuantidadeEstoque()).append(")\n"); }

            int idProd = Integer.parseInt(JOptionPane.showInputDialog(janela, listaProd.toString() + "\nDigite o ID do produto consumido:"));
            Produto produto = bd.catalogo.stream().filter(p -> p.getId() == idProd).findFirst().orElse(null);
            if (produto == null) throw new HotelException("Produto não encontrado.");

            int qtd = Integer.parseInt(JOptionPane.showInputDialog(janela, "Quantidade consumida:"));
            quarto.getMiniBar().registrarConsumo(produto, qtd);

            JOptionPane.showMessageDialog(janela, "Consumo registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (HotelException e) {
            JOptionPane.showMessageDialog(janela, e.getMessage(), "Aviso do Sistema", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {}
    }

    private void fazerCheckOut() {
        try {
            int numQuarto = Integer.parseInt(JOptionPane.showInputDialog(janela, "Número do Quarto para Checkout:"));
            Quarto quarto = bd.quartos.stream().filter(q -> q.getNumero() == numQuarto).findFirst().orElse(null);

            if (quarto == null) throw new HotelException("Quarto não encontrado.");
            if (!quarto.getStatus().equals("Ocupado")) throw new HotelException("Quarto não está Ocupado.");

            Reserva reserva = bd.reservas.stream().filter(r -> r.getQuarto().getNumero() == numQuarto).reduce((first, second) -> second).orElse(null);
            if (reserva == null) throw new HotelException("Reserva associada não encontrada.");

            Fatura fatura = new Fatura(reserva);
            JOptionPane.showMessageDialog(janela, fatura.gerarDetalhes(), "Fechamento de Conta", JOptionPane.INFORMATION_MESSAGE);

            quarto.setStatus("Disponível");
            quarto.getMiniBar().limparParaProximoHospede();

            JOptionPane.showMessageDialog(janela, "Check-out concluído. Quarto liberado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (HotelException e) {
            JOptionPane.showMessageDialog(janela, e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {}
    }

    private void relatorios() {
        String msg = "--- CLIENTES ---\n";
        for (Cliente c : bd.clientes) msg += c.toString() + "\n";
        msg += "\n--- QUARTOS ---\n";
        for (Quarto q : bd.quartos) msg += q.toString() + "\n";
        msg += "\n--- ESTOQUE DE PRODUTOS ---\n";
        for (Produto p : bd.catalogo) msg += p.toString() + "\n";

        JOptionPane.showMessageDialog(janela, msg, "Relatório Geral", JOptionPane.PLAIN_MESSAGE);
    }

    private void salvarESair() {
        try {
            arquivo.salvarDados(bd);
            JOptionPane.showMessageDialog(janela, "Dados salvos com sucesso!\nEncerrando o sistema...", "Finalizando", JOptionPane.INFORMATION_MESSAGE);
        } catch (HotelException e) {
            JOptionPane.showMessageDialog(janela, e.getMessage(), "Erro ao Salvar", JOptionPane.ERROR_MESSAGE);
        } finally {
            janela.dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) { new TelaSistema().iniciar(); }
}