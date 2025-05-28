package br.com.mesadigital.vendas.exceptions;

/**
 * ResponseDTO para quando ocorrer exceções durante a requisição
 */
public class DetalhesErroException {

    private String titulo;
    private String dataHora;
    private int status;
    private String mensagem;

    public DetalhesErroException(String titulo, String dataHora, int status, String mensagem) {
        this.titulo = titulo;
        this.dataHora = dataHora;
        this.status = status;
        this.mensagem = mensagem;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
