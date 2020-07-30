package com.tribanco.recomendaai.constants;

public enum ErrorMessages {

    ERRO_CAMPO_NAO_INFORMADO(new Erro(1, "Campo obrigatório não foi informado")),
    ERRO_CAMPO_NAO_EXISTE(new Erro(2, "Campo informado não existe")),
    ERRO_SERVER(new Erro(90, "Erro no servidor/Serviço Indisponível. Favor contactar o administrador"));

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
