package com.recomendaai.constants;

public enum ErrorMessages {

    ERRO_CAMPO_NAO_INFORMADO(new Erro(1, "Campo obrigatório não foi informado.")),
    PROCESSANDO_TICKET(new Erro(2, "O ticket ainda está em processamento. Favor aguardar...")),
    ERRO_CAMPO_NAO_EXISTE(new Erro(3, "Campo informado não existe.")),
    ERRO_DUPLICADO(new Erro(4, "Parceiro já cadastrado.")),
    ERRO_RETORNO_VAZIO(new Erro(5, "Processamento finalizado. Não foram encontrados registros.")),
    ERRO_RETORNO_API_VAZIO(new Erro(6, "Sem retorno de api parceira.")),
    ERRO_SERVER(new Erro(90, "Erro no servidor/Serviço Indisponível. Favor contactar o administrador."));


    private final Erro erro;

    ErrorMessages(Erro errorCode) {
        erro = errorCode;
    }

    public Erro getErro() {
        return erro;
    }

    public static class Erro {
        public Integer code;
        public String message;

        public Erro(Integer code, String message) { this.code = code; this.message = message; }
    }
}
