import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}

class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }
}

public class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // Inserir todos os funcionários
        funcionarios.add(new Funcionario("João", LocalDate.of(1980, 1, 15), new BigDecimal("2500.00"), "Analista"));
        funcionarios.add(new Funcionario("Maria", LocalDate.of(1990, 3, 25), new BigDecimal("3500.00"), "Gerente"));
        funcionarios.add(new Funcionario("Pedro", LocalDate.of(1985, 5, 5), new BigDecimal("2800.00"), "Analista"));
        funcionarios.add(new Funcionario("Ana", LocalDate.of(1995, 7, 10), new BigDecimal("4000.00"), "Gerente"));

        // Remover o funcionário "João" da lista
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // Imprimir todos os funcionários com todas as informações
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        funcionarios.forEach(funcionario -> System.out.println("Nome: " + funcionario.getNome() +
                ", Data de Nascimento: " + funcionario.getDataNascimento().format(formatter) +
                ", Salário: " + funcionario.getSalario().toString().replace(".", ",") +
                ", Função: " + funcionario.getFuncao()));

        // Aumentar o salário dos funcionários em 10%
        funcionarios.forEach(funcionario -> {
            BigDecimal novoSalario = funcionario.getSalario().multiply(new BigDecimal("1.1"));
            funcionario.setSalario(novoSalario);
        });

        // Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Imprimir os funcionários agrupados por função
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(funcionario -> System.out.println("- " + funcionario.getNome()));
        });

        // Imprimir os funcionários que fazem aniversário nos meses 10 e 12
        System.out.println("Funcionários que fazem aniversário no mês 10 e 12:");
        funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10 ||
                        funcionario.getDataNascimento().getMonthValue() == 12)
                .forEach(funcionario -> System.out.println("- " + funcionario.getNome()));

        // Encontrar o funcionário com a maior idade
        Funcionario funcionarioMaisVelho = Collections.max(funcionarios, Comparator.comparing(
                funcionario -> funcionario.getDataNascimento()));

        // Calcular a idade do funcionário mais velho
        int idadeMaisVelho = LocalDate.now().getYear() - funcionarioMaisVelho.getDataNascimento().getYear();

        // Imprimir o funcionário com a maior idade
        System.out.println("Funcionário mais velho:");
        System.out.println("Nome: " + funcionarioMaisVelho.getNome() + ", Idade: " + idadeMaisVelho);

        // Ordenar a lista de funcionários por ordem alfabética
        Collections.sort(funcionarios, Comparator.comparing(Funcionario::getNome));

        // Imprimir a lista de funcionários por ordem alfabética
        System.out.println("Lista de funcionários por ordem alfabética:");
        funcionarios.forEach(funcionario -> System.out.println("- " + funcionario.getNome()));

        // Calcular o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Imprimir o total dos salários dos funcionários
        System.out.println("Total dos salários dos funcionários: " + totalSalarios.toString().replace(".", ","));

        // Imprimir quantos salários mínimos cada funcionário ganha
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("Quantidade de salários mínimos que cada funcionário ganha:");
        funcionarios.forEach(funcionario -> {
            BigDecimal quantidadeSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_DOWN);
            System.out.println("- " + funcionario.getNome() + ": " + quantidadeSalariosMinimos);
        });
    }
}
