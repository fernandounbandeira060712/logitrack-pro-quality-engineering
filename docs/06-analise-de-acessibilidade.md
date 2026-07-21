# Análise de Acessibilidade — LogiTrack Pro

## Objetivo

Avaliar barreiras básicas de acesso e registrar recomendações para evolução da aplicação.

## Cobertura automatizada atual

| ID | Validação | Situação |
|---|---|---|
| CT-A11Y-001 | Imagens devem possuir atributo alternativo | Automatizado |
| CT-A11Y-002 | Botões devem possuir nome acessível | Automatizado |

## Pontos positivos

- existência de testes automatizados de acessibilidade básica;
- uso de seletores por papel e nome em parte da automação;
- possibilidade de auditoria por navegador;
- evidências anexadas ao Allure.

## Oportunidades identificadas

### Campos obrigatórios

Os formulários devem comunicar obrigatoriedade visual e programaticamente.

Recomendações:

```html
<label for="campo">Campo *</label>
<input id="campo" required aria-required="true">
```

### Botões representados apenas por ícones

Ações como editar e excluir devem possuir nome acessível:

```html
<button aria-label="Editar veículo">...</button>
<button aria-label="Excluir veículo">...</button>
```

### Mensagens de erro

Mensagens devem:

- identificar o campo relacionado;
- explicar como corrigir;
- ser anunciadas por leitores de tela;
- não depender apenas de cor;
- manter idioma consistente.

Exemplo:

```html
<p id="erro-custo" role="alert">
  O custo estimado não pode ser negativo.
</p>
```

### Navegação por teclado

Validar manualmente:

- ordem de foco;
- abertura e fechamento de modais;
- uso de selects;
- botões de ação;
- paginação;
- retorno de foco após fechar modal.

### Contraste e foco

Validar:

- contraste de textos;
- contraste dos ícones;
- foco visível;
- estados de erro e sucesso;
- textos em elementos desabilitados.

## Plano de evolução

### Curto prazo

- adicionar `aria-label` aos botões por ícone;
- marcar campos obrigatórios;
- associar erro ao campo;
- padronizar mensagens.

### Médio prazo

- integrar auditoria automatizada mais ampla;
- testar navegação por teclado;
- revisar contraste;
- testar zoom e reflow.

### Longo prazo

- validação com leitor de tela;
- revisão por usuários;
- inclusão de acessibilidade no Definition of Done;
- monitoramento de regressões.

## Critérios de aceite

- todos os controles interativos possuem nome acessível;
- formulários informam obrigatoriedade;
- erros são compreensíveis e associados ao campo;
- fluxo principal pode ser executado por teclado;
- foco visível em todos os controles;
- conteúdo permanece utilizável com zoom.
