package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class UniversalMachine {
  private Character[] input;
  private Set<Command> commands = new HashSet<>();
  private Reader reader;
  private String currentState;
  private String[] finalStates;
  
  public UniversalMachine(String commands, String initialState, String[] finalStates) {
    this.commands = extractCommands(commands);
    this.currentState = initialState;
    this.finalStates = finalStates;
  }
  
  /*
   * O caminho passado para essa funcao determina o comportamento da maquina,
   * o documento deve ter a formatacao correta
   */
  public Set<Command> extractCommands(String path) {
    Set<Command> commands = new HashSet<>();
    
    Scanner lineScanner = null;
    try(Scanner tableScanner = new Scanner(new File("res/" + path + ".csv"))) {
      // pula o cabecalho
      tableScanner.nextLine();
      
      while (tableScanner.hasNextLine()) {
        lineScanner = new Scanner(tableScanner.nextLine());
        lineScanner.useDelimiter("!");
        
        commands.add(new Command(extractState(lineScanner.next()),
            extractChar(lineScanner.next()),extractChar(lineScanner.next()),
            extractChar(lineScanner.next()),extractState(lineScanner.next())));
      }
    } catch (FileNotFoundException fnfe) {} finally {lineScanner.close();}
    
    return commands;
  }
  
  private String extractState(String parameter) {
    try {
      return parameter.trim();
    /* Se falhar signimifca que a string e' vazia, o que significa que a tabela
     * esta' tentando enviar um espaco em branco*/
    } catch(StringIndexOutOfBoundsException sioobe) {
      return " ";
    }
  }

  // CUIDADO: o programa trabalha apenas com caracteres
  public char extractChar(String parameter) {
    try {
      return parameter.trim().charAt(0);
    /* Se falhar signimifca que a string e' vazia, o que significa que a tabela
     * esta' tentando enviar um espaco em branco*/
    } catch(StringIndexOutOfBoundsException sioobe) {
      return ' ';
    }
  }

  // Configura a fita de entrada
  public void setTape(String expression) {
    List<Character> tape = new ArrayList<Character>();
    /* A simulacao da fita infinita e' feita via longos espacos em branco antes e
     * depois do input*/
    for(int i = 0 ; i < 1024 ; i++)
      tape.add(' ');
    // input adicionado
    for(int i = 0 ; i < expression.length() ; i++)
      tape.add(expression.charAt(i));
    for(int i = 0 ; i < 1024 ; i++)
      tape.add(' ');
    
    // a fita e' um array para facilitar a analise de cada celula
    this.input = tape.toArray(new Character[0]);
    // criacao da cabeca de leitura com base na fita de entrada
    this.reader = new Reader(input);
  }
  
  public boolean run(String expression) {
    setTape(expression);
    
    System.out.println("Fita Inicial: " + expression);
    
    try {
      // identifica se o automato entrou em loop ou um estado nao tem
      // a entrada desejada
      int tries = 0;
      while(true) {
        if(tries > 1024)
          throw new StackOverflowError();
        
        // itera pela lista de comandos procurando o proximo passo
        for(Command c : this.commands) {
          if(this.currentState.equals(c.getState())) {
            if(this.reader.getSymbol() == c.getRead()) {
              
              // Inprime a acao realizada
              printTrack();
              
              /* se o simbolo e o estado correspondem, as acoes do
               * comando sao executadas*/
              this.input[this.reader.index] = c.getWrite();
              this.reader.move(c.getMove());
              
              /* alcancou um estado sem saida, entao verifica 
               * se o estado atual e' um dos estados finais*/
              if(c.getToSate().equals("%") || c.getToSate().equals("#")
                  || c.getToSate().equals(" "))
                return endRun(isStateFinal());
              
              // muda o estado
              this.currentState = c.getToSate();
              
              tries = 0;
              break;
            }else {
              continue;
            }
          }
        }
        tries++; // iteracao para quebra do automato
      }
    }catch(StackOverflowError sofe) {
      System.err.println("\t***Quebra!***\n");
      return endRun(false);
    }
  }

  private void printTrack() {
    String head = "";
    System.out.println("          " + currentTape());
    for(int i = 0 ; i < this.reader.index + 10 - 1024 ; i++)
      head = head.concat(" ");
    System.out.println(head.concat("^"));
    System.out.println("Estado: " + this.currentState);
  }

  private String currentTape() {
    String currentTape = "";
    for(char c : this.input)
      currentTape = currentTape.concat("" + c);
    
    return currentTape.strip();
  }

  private boolean isStateFinal() {
    // verifica se o estado atual pertence aos estados finais
    for(int i = 0 ; i < this.finalStates.length ; i++) 
      if(this.finalStates[i].equals(this.currentState))
        return true;
    
    return false;
  }
  
  // imprime a fita resultante no final de cada execucao
  private boolean endRun(boolean exp) {
    printTrack();
    
    System.out.println("\nFita final: " + currentTape());
    return exp;
  }
}

// Classe de controle da cabeca de leitura
class Reader{
  public Character[] tape;
  public int index;
  
  public Reader(Character[] tape) {
    this.tape = tape;
    this.index = 1024;
  }
  
  public void move(char move) {
    try {
      switch(move) {
      case 'L':
        this.index--;
        break;
      case 'R':
        this.index++;
        break;
      case 'S':
        break;
      default:
        throw new IllegalArgumentException("Movimento invalido: " + move);
      }
    } catch (IllegalArgumentException iae) {
      System.out.println(iae.getMessage());
    }
  }

  public char getSymbol() {
    return tape[index];
  }
  
  @Override
  public String toString() {
    return "" + tape[index];
  }
}