package br.com.mesadigital.vendas.exceptions;

/**
 * DTO para os dados retornados após falha na validação dos campos da request
 */
public class DetalhesValidacaoException extends DetalhesErroException {

    private String campos;


    public DetalhesValidacaoException(String titulo, String dataHora, int status, String mensagem, String campos) {
        super(titulo,dataHora,status,mensagem);
        this.campos = campos;
    }

    public String getCampos() {
        return campos;
    }

    public void setCampos(String campos) {
        this.campos = campos;
    }

}
