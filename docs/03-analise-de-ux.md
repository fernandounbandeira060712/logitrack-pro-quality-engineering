# Análise de Experiência do Usuário — LogiTrack Pro

## Resumo

Foram registradas quatro oportunidades de melhoria. Elas não substituem defeitos funcionais, mas impactam clareza, consistência, acessibilidade e confiança do usuário.

---

## UX-001 — Padronizar mensagem de placa duplicada para português

**Módulo:** Veículos.<br>
**Situação:** ao repetir uma placa, a aplicação apresenta mensagem misturando português e inglês.<br>
**Exemplo atual:** `Veiculo with placa '<PLACA>' already exists`.<br>
**Recomendação:** utilizar mensagem integralmente em português, como:

```text
Já existe um veículo cadastrado com a placa informada.
```

**Benefícios:**

- consistência de idioma;
- melhor compreensão;
- redução de dúvida;
- aparência mais profissional.

**Prioridade sugerida:** Média.<br>
**Issue:** https://github.com/fernandounbandeira060712/logitrack-pro-quality-engineering/issues/2

---

## UX-002 — Identificar visualmente os campos obrigatórios

**Módulos:** Veículos, Manutenções e Viagens.<br>
**Situação:** os formulários não deixam claro quais campos são obrigatórios.<br>
**Recomendação:**

- incluir asterisco nos rótulos;
- apresentar texto explicativo;
- utilizar `required` e `aria-required="true"`;
- associar mensagens ao campo correspondente.

**Benefícios:**

- maior previsibilidade;
- menos submissões inválidas;
- melhor acessibilidade;
- menor tempo de preenchimento.

**Prioridade sugerida:** Média.<br>
**Issue:** https://github.com/fernandounbandeira060712/logitrack-pro-quality-engineering/issues/3

---

## UX-003 — Melhorar a identificação das ações por ícones

**Módulos:** Veículos, Manutenções e Viagens.<br>
**Situação:** ações como editar e excluir são exibidas apenas por ícones.<br>
**Recomendação:**

- adicionar tooltip;
- incluir `aria-label`;
- garantir foco visível;
- utilizar confirmação clara para exclusão;
- considerar texto visível em contextos críticos.

**Benefícios:**

- melhor descoberta das ações;
- redução de erro;
- suporte a leitores de tela;
- maior segurança em operações destrutivas.

**Prioridade sugerida:** Média.<br>
**Issue:** https://github.com/fernandounbandeira060712/logitrack-pro-quality-engineering/issues/4

---

## UX-004 — Padronizar mensagens de validação para português

**Módulos:** formulários e regras de negócio.<br>
**Situação:** validações podem apresentar mensagens genéricas em inglês, como `Validation failed`.<br>
**Recomendação:** utilizar mensagens específicas e contextualizadas, por exemplo:

```text
O custo estimado não pode ser negativo.
```

**Benefícios:**

- orientação clara para correção;
- consistência de idioma;
- redução de retrabalho;
- melhor percepção de qualidade.

**Prioridade sugerida:** Média/Alta.<br>
**Issue:** https://github.com/fernandounbandeira060712/logitrack-pro-quality-engineering/issues/5

---

## Diretrizes gerais

- padronizar tom e idioma;
- apresentar feedback próximo ao campo;
- diferenciar erro, alerta e sucesso;
- manter mensagens objetivas;
- evitar mensagens técnicas para o usuário final;
- garantir leitura por tecnologias assistivas;
- manter consistência entre módulos.
