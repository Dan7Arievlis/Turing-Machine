package test;

import java.util.Scanner;

import main.UniversalMachine;

public class Main {
  public Main() {
    System.out.println("*************************************************\n"
                     + " SIGA AS INSTRUCOES DO README.txt PARA ADICIONAR \n"
                     + "               SUA PROPRIA MAQUINA               \n"
                     + "*************************************************\n");
  }
  
  public static void main(String[] args) {
    new Main();
    
    run();
  }
  
  private static void run() {
    try(Scanner option = new Scanner(System.in);
        Scanner expression = new Scanner(System.in)) {
      while(true) {
        System.out.print("Selecione uma das máquinas abaixo para execução:\n"
                        + " 1: M que reconhece |a| == |b|, alfabeto = {a,b}\n"
                        + " 2: M que reconhece a^nb^n, alfabeto = {a,b}\n"
                        + " 3: M que reconhece a^nb^nc^n, alfabeto = {a,b,c}\n"
                        + " 4: M que reconhece wcw^r, alfabeto = {a,b,c}\n"
                        + " 5: M que transforma 1^n01^m -> 1^1(n+m), alfabeto = {0,1}\n"
                        + " 6: Sair\n\n"
                        + "Opcao: ");
        
        UniversalMachine turingMachine = null;
        switch(option.nextInt()) {
        case 1:
          String[] fs1 = {"A"};
          System.out.println("Expressão: ");
          turingMachine = new UniversalMachine("maquina1", "A" , fs1);
          System.out.println(turingMachine.run(expression.nextLine()));
          break;
        case 2:
          String[] fs2 = {"D"};
          System.out.println("Expressão: ");
          turingMachine = new UniversalMachine("maquina2", "A" , fs2);
          System.out.println(turingMachine.run(expression.nextLine()));
          break;
        case 3:
          String[] fs3 = {"4"};
          System.out.println("Expressão: ");
          turingMachine = new UniversalMachine("maquina3", "A" , fs3);
          System.out.println(turingMachine.run(expression.nextLine()));
          break;
        case 4:
          String[] fs4 = {"G"};
          System.out.println("Expressão: ");
          turingMachine = new UniversalMachine("maquina4", "A" , fs4);
          System.out.println(turingMachine.run(expression.nextLine()));
          break;
        case 5:
          String[] fs5 = {"d"};
          System.out.println("Expressão: ");
          turingMachine = new UniversalMachine("maquina5", "A" , fs5);
          System.out.println(turingMachine.run(expression.nextLine()));
          break;
        default:
          throw new InterruptedException();
        }
        
        System.out.println("\n-------------------------------------------------\n");
      }
    } catch (InterruptedException ie) {
      System.out.println("Finalizando...");
    }
  }
}
