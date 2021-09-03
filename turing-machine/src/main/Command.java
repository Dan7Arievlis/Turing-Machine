package main;

/*
 * Classe de controle e armazenamento dos comandos da maquina 
 */

public class Command {
  private String state;     // o estado em que atua
  private char read;      // o simbolo que le na fita
  private char write;     // o simbole que escreve na fita
  private char move;      // a movimentacao da cabeca de leitura depois da acao
  private String toSate;    // o estado em que a maquina entra depois da acao
  
  public Command(String state, char read, char write, char move, String toSate) {
    this.state = state;
    this.read = read;
    this.write = write;
    this.move = move;
    this.toSate = toSate;
  }
  
  public String getState() {
    return state;
  }
  public char getRead() {
    return read;
  }
  public char getWrite() {
    return write;
  }
  public char getMove() {
    return move;
  }
  public String getToSate() {
    return toSate;
  }
  
  @Override
  public String toString() {
    return String.format("Estado: %s | Leitura: %c |"
        + " Escrita: %c | Movimentacao: %c | ParaEstado: %s",
        this.state, this.read, this.write, this.move, this.toSate);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + move;
    result = prime * result + read;
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((toSate == null) ? 0 : toSate.hashCode());
    result = prime * result + write;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Command other = (Command) obj;
    if (move != other.move)
      return false;
    if (read != other.read)
      return false;
    if (state == null) {
      if (other.state != null)
        return false;
    } else if (!state.equals(other.state))
      return false;
    if (toSate == null) {
      if (other.toSate != null)
        return false;
    } else if (!toSate.equals(other.toSate))
      return false;
    if (write != other.write)
      return false;
    return true;
  }
}
