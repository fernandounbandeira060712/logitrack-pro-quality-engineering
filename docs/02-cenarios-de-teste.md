# RelatÃ³rio de CenÃ¡rios de Teste â€” LogiTrack Pro

## Resumo da execuÃ§Ã£o

| Indicador | Resultado |
|---|---|
| CenÃ¡rios documentados | 16 |
| Aprovados | 15 |
| Reprovados | 1 |
| Defeitos confirmados | 1 |
| EvidÃªncia automatizada | Allure, screenshot, vÃ­deo, trace e logs |
| Defeito registrado | BUG-TRIP-001 |

---

## CT-LOGIN-001 â€” Login com credenciais vÃ¡lidas

**Objetivo:** Validar que um usuÃ¡rio autorizado consegue acessar a Ã¡rea autenticada.

**PrÃ©-condiÃ§Ãµes:** AplicaÃ§Ã£o disponÃ­vel e usuÃ¡rio vÃ¡lido existente.

**Dados utilizados:** Credenciais fornecidas exclusivamente para o desafio.

**Passos:**

1. Acessar a tela de login.
2. Informar e-mail vÃ¡lido.
3. Informar senha vÃ¡lida.
4. Acionar o botÃ£o de entrada.

**Resultado esperado:** UsuÃ¡rio direcionado ao Dashboard.

**Resultado obtido:** A Ã¡rea autenticada foi carregada com sucesso.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `LoginTests` â†’ `CT-LOGIN-001`.

---

## CT-LOGIN-002 â€” Rejeitar credenciais invÃ¡lidas

**Objetivo:** Garantir que credenciais inexistentes nÃ£o permitam acesso.

**PrÃ©-condiÃ§Ãµes:** AplicaÃ§Ã£o disponÃ­vel.

**Dados utilizados:** E-mail inexistente e senha invÃ¡lida.

**Passos:**

1. Acessar a tela de login.
2. Informar credenciais invÃ¡lidas.
3. Submeter o formulÃ¡rio.

**Resultado esperado:** Permanecer fora da Ã¡rea autenticada.

**Resultado obtido:** O acesso nÃ£o foi concedido.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `LoginTests` â†’ `CT-LOGIN-002`.

---

## CT-LOGIN-003 â€” Validar campos obrigatÃ³rios

**Objetivo:** Validar a obrigatoriedade dos campos de autenticaÃ§Ã£o.

**PrÃ©-condiÃ§Ãµes:** Tela de login aberta.

**Dados utilizados:** Campos vazios.

**Passos:**

1. NÃ£o preencher e-mail.
2. NÃ£o preencher senha.
3. Submeter o formulÃ¡rio.

**Resultado esperado:** Navegador ou aplicaÃ§Ã£o deve informar que os campos sÃ£o obrigatÃ³rios.

**Resultado obtido:** A submissÃ£o foi bloqueada e a validaÃ§Ã£o foi apresentada.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `LoginTests` â†’ `CT-LOGIN-003`.

---

## CT-LOGIN-004 â€” Rejeitar formato de e-mail invÃ¡lido

**Objetivo:** Garantir que o campo de e-mail valide o formato informado.

**PrÃ©-condiÃ§Ãµes:** Tela de login aberta.

**Dados utilizados:** `email-invalido`.

**Passos:**

1. Informar e-mail sem formato vÃ¡lido.
2. Informar uma senha.
3. Submeter o formulÃ¡rio.

**Resultado esperado:** Exibir validaÃ§Ã£o de formato e impedir autenticaÃ§Ã£o.

**Resultado obtido:** O formato invÃ¡lido foi identificado.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `LoginTests` â†’ `CT-LOGIN-004`.

---

## CT-CAD-001 â€” Exibir formulÃ¡rio de cadastro

**Objetivo:** Validar o acesso ao formulÃ¡rio de criaÃ§Ã£o de usuÃ¡rio.

**PrÃ©-condiÃ§Ãµes:** AplicaÃ§Ã£o disponÃ­vel.

**Dados utilizados:** NÃ£o aplicÃ¡vel.

**Passos:**

1. Acessar a rota de cadastro.
2. Verificar os elementos do formulÃ¡rio.

**Resultado esperado:** FormulÃ¡rio de cadastro exibido.

**Resultado obtido:** O formulÃ¡rio foi carregado.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `RegistrationTests` â†’ `CT-CAD-001`.

---

## CT-CAD-002 â€” Rejeitar senha menor que oito caracteres

**Objetivo:** Validar a regra mÃ­nima de tamanho da senha.

**PrÃ©-condiÃ§Ãµes:** FormulÃ¡rio de cadastro aberto.

**Dados utilizados:** Nome vÃ¡lido, e-mail vÃ¡lido e senha `1234567`.

**Passos:**

1. Preencher nome.
2. Preencher e-mail.
3. Informar senha com sete caracteres.
4. Submeter.

**Resultado esperado:** Impedir cadastro e apresentar validaÃ§Ã£o.

**Resultado obtido:** O campo de senha foi rejeitado.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `RegistrationTests` â†’ `CT-CAD-002`.

---

## CT-A11Y-001 â€” Imagens devem possuir atributo alt

**Objetivo:** Identificar imagens sem alternativa textual.

**PrÃ©-condiÃ§Ãµes:** Tela de login disponÃ­vel.

**Dados utilizados:** Elementos `img` presentes na pÃ¡gina.

**Passos:**

1. Acessar a tela.
2. Localizar imagens sem atributo `alt`.
3. Contabilizar ocorrÃªncias.

**Resultado esperado:** Nenhuma imagem sem atributo `alt`.

**Resultado obtido:** NÃ£o foram identificadas imagens sem `alt`.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `AccessibilityTests` â†’ `CT-A11Y-001`.

---

## CT-A11Y-002 â€” BotÃµes devem possuir nome acessÃ­vel

**Objetivo:** Verificar se botÃµes possuem texto ou nome acessÃ­vel.

**PrÃ©-condiÃ§Ãµes:** Tela de login disponÃ­vel.

**Dados utilizados:** Elementos `button`.

**Passos:**

1. Acessar a tela.
2. Localizar botÃµes vazios e sem `aria-label`.
3. Contabilizar ocorrÃªncias.

**Resultado esperado:** Nenhum botÃ£o sem nome acessÃ­vel.

**Resultado obtido:** NÃ£o foram identificados botÃµes sem nome acessÃ­vel no escopo analisado.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `AccessibilityTests` â†’ `CT-A11Y-002`.

---

## CT-DASH-001 â€” Carregar Dashboard

**Objetivo:** Validar o carregamento do painel principal apÃ³s autenticaÃ§Ã£o.

**PrÃ©-condiÃ§Ãµes:** UsuÃ¡rio autenticado.

**Dados utilizados:** Credenciais vÃ¡lidas.

**Passos:**

1. Realizar login.
2. Aguardar carregamento do Dashboard.
3. Verificar os elementos principais.

**Resultado esperado:** Dashboard disponÃ­vel para uso.

**Resultado obtido:** O painel foi carregado.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `DashboardTests` â†’ `CT-DASH-001`.

---

## CT-NAV-001 â€” Acessar Ã¡rea autenticada

**Objetivo:** Validar a navegaÃ§Ã£o entre login e Ã¡rea protegida.

**PrÃ©-condiÃ§Ãµes:** Credenciais vÃ¡lidas.

**Dados utilizados:** UsuÃ¡rio do desafio.

**Passos:**

1. Realizar login.
2. Verificar a URL e o estado da pÃ¡gina autenticada.

**Resultado esperado:** Ãrea autenticada acessÃ­vel.

**Resultado obtido:** A navegaÃ§Ã£o foi concluÃ­da.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `NavigationTests` â†’ `CT-NAV-001`.

---

## CT-VEI-002 â€” Cadastrar veÃ­culo e validar mensagem e tabela

**Objetivo:** Validar o cadastro de um veÃ­culo com dados vÃ¡lidos.

**PrÃ©-condiÃ§Ãµes:** UsuÃ¡rio autenticado.

**Dados utilizados:**

- placa Ãºnica gerada automaticamente;
- modelo `Modelo QA Automatizado`;
- tipo `Leve`;
- ano `2024`.

**Passos:**

1. Acessar VeÃ­culos.
2. Abrir o formulÃ¡rio.
3. Preencher placa, modelo, tipo e ano.
4. Confirmar.
5. Validar o toast.
6. Pesquisar a placa.
7. Validar a linha na tabela.

**Resultado esperado:** Mensagem de sucesso e veÃ­culo apresentado na tabela.

**Resultado obtido:** Toast `VeÃ­culo criado com sucesso!` e registro encontrado.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `VehicleRegistrationTests`, com screenshot, vÃ­deo, trace e logs.

---

## CT-VEI-003 â€” Impedir veÃ­culos com a mesma placa

**Objetivo:** Validar a unicidade da placa.

**PrÃ©-condiÃ§Ãµes:** UsuÃ¡rio autenticado e primeiro veÃ­culo cadastrado.

**Dados utilizados:** Dois veÃ­culos com a mesma placa e modelos diferentes.

**Passos:**

1. Cadastrar o primeiro veÃ­culo.
2. Tentar cadastrar outro com a mesma placa.
3. Capturar a mensagem.
4. Pesquisar a placa.
5. Contar os registros.

**Resultado esperado:** Segundo cadastro bloqueado e apenas um registro existente.

**Resultado obtido:** Cadastro bloqueado e apenas um veÃ­culo mantido.

**Mensagem obtida:** `Veiculo with placa '<PLACA>' already exists`.

**Status:** APROVADO com oportunidade de melhoria de UX/localizaÃ§Ã£o.

**EvidÃªncia:** Allure Report â†’ `DuplicateVehiclePlateTests`.

---

## CT-MAN-002 â€” Agendar manutenÃ§Ã£o vÃ¡lida

**Objetivo:** Validar o agendamento de manutenÃ§Ã£o com dados vÃ¡lidos.

**PrÃ©-condiÃ§Ãµes:** UsuÃ¡rio autenticado e veÃ­culo cadastrado.

**Dados utilizados:**

- serviÃ§o `Revisao preventiva`;
- data inicial anterior Ã  final;
- custo estimado `350.00`.

**Passos:**

1. Cadastrar veÃ­culo.
2. Acessar ManutenÃ§Ã£o.
3. Abrir o formulÃ¡rio.
4. Selecionar veÃ­culo.
5. Informar serviÃ§o, datas e custo.
6. Confirmar.
7. Validar toast e tabela.

**Resultado esperado:** ManutenÃ§Ã£o agendada e exibida.

**Resultado obtido:** Toast `ManutenÃ§Ã£o agendada com sucesso!` e registro encontrado.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `MaintenanceSchedulingTests`.

---

## CT-MAN-003 â€” Impedir manutenÃ§Ã£o com data final anterior Ã  inicial

**Objetivo:** Validar a consistÃªncia cronolÃ³gica da manutenÃ§Ã£o.

**PrÃ©-condiÃ§Ãµes:** UsuÃ¡rio autenticado e veÃ­culo cadastrado.

**Dados utilizados:** Data inicial em D+5 e data final em D+4.

**Passos:**

1. Abrir nova manutenÃ§Ã£o.
2. Preencher os dados.
3. Informar data final anterior Ã  inicial.
4. Confirmar.
5. Validar mensagem.
6. Pesquisar o veÃ­culo.

**Resultado esperado:** Bloquear o cadastro e nÃ£o apresentar registro na tabela.

**Resultado obtido:** Mensagem `Data de finalizaÃ§Ã£o nÃ£o pode ser anterior Ã  data de inÃ­cio` e ausÃªncia do registro.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `InvalidMaintenanceDateTests`.

---

## CT-VIA-002 â€” Cadastrar viagem vÃ¡lida

**Objetivo:** Validar o cadastro de viagem com datas vÃ¡lidas.

**PrÃ©-condiÃ§Ãµes:** UsuÃ¡rio autenticado e veÃ­culo cadastrado.

**Dados utilizados:**

- origem `Joao Pessoa`;
- destino `Natal`;
- saÃ­da anterior Ã  chegada;
- distÃ¢ncia `220.5 km`.

**Passos:**

1. Cadastrar veÃ­culo.
2. Acessar Viagens.
3. Abrir o formulÃ¡rio.
4. Preencher todos os campos.
5. Confirmar.
6. Validar o toast.
7. Pesquisar a origem.
8. Validar a tabela.

**Resultado esperado:** Viagem agendada e apresentada na tabela.

**Resultado obtido:** Toast `Viagem agendada com sucesso!` e registro localizado.

**Status:** APROVADO.

**EvidÃªncia:** Allure Report â†’ `TripRegistrationTests`.

---

## CT-VIA-003 â€” Impedir chegada anterior Ã  saÃ­da

**Objetivo:** Validar a consistÃªncia cronolÃ³gica da viagem.

**PrÃ©-condiÃ§Ãµes:** UsuÃ¡rio autenticado e veÃ­culo cadastrado.

**Dados utilizados:**

- saÃ­da em `25/07/2026`;
- chegada em `24/07/2026`;
- origem e destino Ãºnicos;
- distÃ¢ncia `220.5 km`.

**Passos:**

1. Acessar Viagens.
2. Abrir nova viagem.
3. Selecionar o veÃ­culo.
4. Informar origem e destino.
5. Informar chegada anterior Ã  saÃ­da.
6. Confirmar.
7. Consultar a tabela.

**Resultado esperado:**

- bloquear o cadastro;
- exibir mensagem de validaÃ§Ã£o;
- nÃ£o persistir a viagem.

**Resultado obtido:** A viagem foi cadastrada e exibida na tabela com datas cronologicamente invÃ¡lidas.

**Status:** REPROVADO.

**Defeito:** `BUG-TRIP-001 â€” Permite chegada anterior Ã  saÃ­da`.

**Impacto:** Compromete a consistÃªncia do planejamento, indicadores e integraÃ§Ãµes.

**EvidÃªncia:**

- `docs/defects/BUG-TRIP-001.md`;
- `evidence/defects/BUG-TRIP-001-data-chegada-anterior-saida.png`;
- GitHub Issue `BUG-TRIP-001`;
- teste automatizado `InvalidTripDateTests`.