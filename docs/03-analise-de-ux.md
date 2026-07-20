# AnÃ¡lise de ExperiÃªncia do UsuÃ¡rio â€” LogiTrack Pro

## UX-001 â€” Mensagem de placa duplicada mistura idiomas

**Funcionalidade ou tela:** Cadastro de veÃ­culos.

**SituaÃ§Ã£o identificada:** Ao tentar cadastrar uma placa jÃ¡ existente, a aplicaÃ§Ã£o apresenta:

`Veiculo with placa '<PLACA>' already exists`

A mensagem mistura portuguÃªs e inglÃªs.

**AlteraÃ§Ã£o recomendada:** Padronizar a mensagem:

`JÃ¡ existe um veÃ­culo cadastrado com esta placa.`

**Justificativa:** Mensagens no idioma da interface reduzem dÃºvida e tornam a experiÃªncia consistente.

**BenefÃ­cio esperado:** Maior clareza, confianÃ§a e facilidade para usuÃ¡rios nÃ£o tÃ©cnicos.

---

## UX-002 â€” ValidaÃ§Ã£o da data da viagem nÃ£o impede dado invÃ¡lido

**Funcionalidade ou tela:** Cadastro de viagens.

**SituaÃ§Ã£o identificada:** A aplicaÃ§Ã£o permite data de chegada anterior Ã  data de saÃ­da.

**AlteraÃ§Ã£o recomendada:**

- restringir o calendÃ¡rio;
- realizar validaÃ§Ã£o no frontend e backend;
- apresentar mensagem prÃ³xima aos campos;
- manter os dados preenchidos para correÃ§Ã£o.

**Justificativa:** O usuÃ¡rio precisa identificar o erro antes de concluir uma operaÃ§Ã£o operacionalmente impossÃ­vel.

**BenefÃ­cio esperado:** ReduÃ§Ã£o de retrabalho, inconsistÃªncias e erros em relatÃ³rios.

---

## UX-003 â€” Campos obrigatÃ³rios nÃ£o sÃ£o identificados visualmente

**Funcionalidade ou tela:** FormulÃ¡rios de veÃ­culo, manutenÃ§Ã£o e viagem.

**SituaÃ§Ã£o identificada:** Os campos nÃ£o apresentam indicaÃ§Ã£o visual clara de obrigatoriedade, como asterisco ou texto de apoio.

**AlteraÃ§Ã£o recomendada:**

- adicionar `*` nos campos obrigatÃ³rios;
- incluir texto `Campos obrigatÃ³rios`;
- apresentar validaÃ§Ã£o inline;
- associar mensagens aos campos para leitores de tela.

**Justificativa:** O usuÃ¡rio deve saber antecipadamente quais dados sÃ£o necessÃ¡rios.

**BenefÃ­cio esperado:** Menos tentativas de submissÃ£o invÃ¡lida e maior previsibilidade.

---

## UX-004 â€” AÃ§Ãµes representadas apenas por Ã­cones

**Funcionalidade ou tela:** Tabelas de veÃ­culos, manutenÃ§Ãµes e viagens.

**SituaÃ§Ã£o identificada:** Editar e excluir sÃ£o apresentados principalmente por Ã­cones.

**AlteraÃ§Ã£o recomendada:**

- adicionar tooltip;
- garantir nome acessÃ­vel;
- diferenciar visualmente aÃ§Ãµes destrutivas;
- solicitar confirmaÃ§Ã£o antes da exclusÃ£o.

**Justificativa:** Ãcones isolados podem nÃ£o ser compreendidos por todos os usuÃ¡rios.

**BenefÃ­cio esperado:** ReduÃ§Ã£o de aÃ§Ãµes acidentais e melhoria da acessibilidade.

---

## UX-005 â€” Feedback de sucesso deve ser consistente

**Funcionalidade ou tela:** Cadastros em geral.

**SituaÃ§Ã£o identificada:** Os fluxos utilizam mensagens diferentes e com padrÃµes de escrita nÃ£o totalmente uniformes.

**AlteraÃ§Ã£o recomendada:** Definir um padrÃ£o de conteÃºdo:

- entidade;
- aÃ§Ã£o realizada;
- resultado;
- idioma;
- pontuaÃ§Ã£o;
- duraÃ§Ã£o suficiente para leitura.

**Justificativa:** Feedback consistente melhora a compreensÃ£o do resultado da aÃ§Ã£o.

**BenefÃ­cio esperado:** Maior confianÃ§a e menor necessidade de verificar manualmente a tabela.