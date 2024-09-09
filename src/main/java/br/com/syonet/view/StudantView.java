package br.com.syonet.view;

import java.util.List;
import java.util.Scanner;

import br.com.syonet.model.Studant;
import br.com.syonet.service.StudantService;

public class StudantView {

  private int selectedOption;
  private boolean exit;
  private Scanner scanner;

  private StudantService service;

  public StudantView(StudantService service, Scanner scanner) {
    this.service = service;
    this.scanner = scanner;
  }

  public void init() {
    System.out.println("Ola seja vem vindo ao nosso cadastro de estudantes.");
  }

  public void showOptions() {
    System.out.println("Por favor selecione uma operaçoes abaixo:");
    System.out.println("\t(1) - criar novo estudantes");
    System.out.println("\t(2) -Listar estudantes");
    System.out.println("\t(3) - Atualizar o cadastro de um estudante.");
    System.out.println("\t(4) - Filtrar estudantes por nome.");
    System.out.println("\t(5) - Deletar um estudante.");
    System.out.println("\t(6) - Buscar estudante por id.");
    System.out.println("\t(0) - sair");
    
  }

  public Integer getSelectedOption() {
    return selectedOption;
  }

  public boolean isExit() {
    return this.exit;
  }

  public void readSelectedOption() {
    String nextLine = this.scanner.nextLine();
    int answer = Integer.parseInt(nextLine);
    this.exit = answer == 0;
    this.selectedOption = answer;
  }

  public void executeSelectedOperation() {
    switch (this.selectedOption) {
      case 1:
        this.initCreationProcess();
        break;
      case 2:
        this.initListProcess();
        break;
      case 3:
      initUpdateProcess();
      break;
    case 4:
      initFilterByNameProcess();
      break;
    case 5:
      initDeleteProcess();
      break;
    case 6:
      initFindByIdProcess();
      break;
    case 0:
      System.out.println("Saindo do programa...");
      break;
    default:
      System.out.println("Opção inválida! Por favor, selecione uma opção válida.");
      break;
    }
  }

  private void initListProcess() {
    List<Studant> studants = this.service.listAll();
    if (studants != null && !studants.isEmpty()) {
      System.out.println();
      System.out.println("\t\tid\t\t|\t\tnome\t\t|\t\tidade\t\t|\t\temail");
      for (int i = 0; i < studants.size(); i++) {
        Studant studant = studants.get(i);
        System.out.println("\t\t%d\t\t\t\t%s\t\t\t\t%d\t\t\t\t%s".formatted(
          studant.getId(),
          studant.getName(),
          studant.getAge(),
          studant.getEmail()));
      }
      System.out.println();
    } else {
      System.out.println("Não há estudantes cadastrados!");
    }
  }

  private void initCreationProcess() {
    System.out.println("Ok, qual é o nome do estudante?");
    String name = this.scanner.nextLine();
    System.out.println("E o email do rapaz ou da moça?");
    String email = this.scanner.nextLine();
    System.out.println("Muito bom! agora qual a idade dela ou dele?");
    Integer idade = Integer.parseInt(this.scanner.nextLine());
    System.out.println("Obrigado temos todas as info, criando novo estudante!");
    Studant studant = new Studant(name, idade, email);
    long id = this.service.save(studant);
    System.out.println("O id do novo estudante é " + id);
  }
  private void initUpdateProcess() {
    System.out.print("Digite o ID do estudante que deseja atualizar: ");
    long id = Long.parseLong(scanner.nextLine());

    Studant studant = service.findById(id);
    if (studant == null) {
      System.out.println("Estudante não encontrado!");
      return;
    }

    System.out.print("Novo nome (atual: " + studant.getName() + "): ");
    String name = scanner.nextLine();
    if (!name.isEmpty()) {
      studant.setName(name);
    }

    System.out.print("Novo email (atual: " + studant.getEmail() + "): ");
    String email = scanner.nextLine();
    if (!email.isEmpty()) {
      studant.setEmail(email);
    }

    System.out.print("Nova idade (atual: " + studant.getAge() + "): ");
    try {
      String ageInput = scanner.nextLine();
      if (!ageInput.isEmpty()) {
        int age = Integer.parseInt(ageInput);
        studant.setAge(age);
      }
    } catch (NumberFormatException e) {
      System.out.println("Erro: Idade inválida! Tente novamente.");
      return;
    }

    service.update(studant);
    System.out.println("Estudante atualizado com sucesso!");
  }

  //Buscando por nome
  private void initFilterByNameProcess() {
    System.out.print("Digite o nome do estudante que deseja buscar: ");
    String name = scanner.nextLine();

    List<Studant> filteredStudants = service.findByName(name);
    if (filteredStudants.isEmpty()) {
      System.out.println("Nenhum estudante encontrado com o nome: " + name);
    } else {
      System.out.println("\tID\t|\tNome\t|\tIdade\t|\tEmail");
      for (Studant studant : filteredStudants) {
        System.out.printf("\t%d\t|\t%s\t|\t%d\t|\t%s\n",
            studant.getId(), studant.getName(), studant.getAge(), studant.getEmail());
      }
    }
  }
  //Deletando dados do banco;
  private void initDeleteProcess() {
    System.out.print("Digite o ID do estudante que deseja deletar (esteja seguro se é isso mesmo que você quer. Esse processo é irreversível): ");
    long id = Long.parseLong(scanner.nextLine());

    Studant studant = service.findById(id);
    if (studant == null) {
      System.out.println("Estudante não encontrado!");
      return;
    }

    service.delete(id);
    System.out.println("Estudante deletado com sucesso!");
  }

  //Buscando por id do usuário
  private void initFindByIdProcess() {
    System.out.print("Digite o ID do estudante que deseja buscar: ");
    long id = Long.parseLong(scanner.nextLine());

    Studant studant = service.findById(id);
    if (studant != null) {
      System.out.println("Detalhes do estudante encontrado:");
      System.out.printf("\tID: %d\n\tNome: %s\n\tIdade: %d\n\tEmail: %s\n",
          studant.getId(), studant.getName(), studant.getAge(), studant.getEmail());
    } else {
      System.out.println("Estudante não encontrado!");
    }
  }
}

