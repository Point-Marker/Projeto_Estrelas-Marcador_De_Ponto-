package br.com.zup.PointMarker.config.configmensagem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErroValidacao {
    String campo;
    String mensagem;
}
