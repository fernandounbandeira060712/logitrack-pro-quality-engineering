# Relatório de Cenários de Teste — LogiTrack Pro

## Resumo

| Indicador | Resultado |
|---|---|
| Cenários automatizados no conjunto total | 22 |
| Regressão principal | 19 |
| Quality Gate de desenvolvimento | 9 |
| Defeitos conhecidos segregados | 3 |
| Defeitos funcionais confirmados | 3 |
| Melhorias de UX registradas | 4 |
| Evidências | Allure, screenshot, vídeo, trace e logs |

> A regressão principal exclui os defeitos conhecidos para manter o sinal do pipeline confiável. Os bugs permanecem automatizados na `KnownDefectsSuite`.

---

## CT-LOGIN-001 — Login com credenciais válidas

**Objetivo:** validar o acesso à área autenticada.<br>
**Pré-condições:** usuário cadastrado e aplicação disponível.<br>
**Dados:** credenciais válidas fornecidas por parâmetro ou secret.<br>
**Passos:** acessar login, preencher e-mail e senha e confirmar.<br>
**Resultado esperado:** redirecionamento para o dashboard.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.<br>
**Evidência:** Allure, URL final, screenshot, vídeo e trace.

## CT-LOGIN-002 — Rejeitar senha inválida

**Objetivo:** impedir autenticação com senha incorreta.<br>
**Pré-condições:** usuário existente.<br>
**Dados:** e-mail válido e senha inválida.<br>
**Passos:** tentar autenticar com os dados informados.<br>
**Resultado esperado:** acesso negado e feedback de erro.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.

## CT-LOGIN-003 — Validar e-mail obrigatório

**Objetivo:** validar que o formulário não aceite e-mail vazio.<br>
**Pré-condições:** página de login aberta.<br>
**Dados:** senha preenchida e e-mail vazio.<br>
**Passos:** submeter o formulário.<br>
**Resultado esperado:** bloqueio da submissão ou mensagem de validação.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.

## CT-LOGIN-004 — Validar senha obrigatória

**Objetivo:** validar que o formulário não aceite senha vazia.<br>
**Pré-condições:** página de login aberta.<br>
**Dados:** e-mail preenchido e senha vazia.<br>
**Passos:** submeter o formulário.<br>
**Resultado esperado:** bloqueio da submissão ou mensagem de validação.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.

## CT-CAD-001 — Exibir formulário de cadastro

**Objetivo:** confirmar o acesso ao cadastro de usuário.<br>
**Pré-condições:** página de login aberta.<br>
**Passos:** acessar a opção de criação de conta.<br>
**Resultado esperado:** formulário de cadastro visível.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.

## CT-CAD-002 — Rejeitar senha abaixo do limite mínimo

**Objetivo:** validar a regra de tamanho mínimo da senha.<br>
**Pré-condições:** formulário de cadastro aberto.<br>
**Dados:** senha com menos de oito caracteres.<br>
**Passos:** preencher e submeter o cadastro.<br>
**Resultado esperado:** cadastro bloqueado com feedback de validação.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.

## CT-A11Y-001 — Imagens devem possuir atributo alternativo

**Objetivo:** identificar imagens sem alternativa textual.<br>
**Pré-condições:** área autenticada carregada.<br>
**Passos:** inspecionar imagens renderizadas.<br>
**Resultado esperado:** imagens informativas com texto alternativo adequado.<br>
**Resultado atual:** cenário automatizado aprovado.<br>
**Status:** Aprovado.

## CT-A11Y-002 — Botões devem possuir nome acessível

**Objetivo:** validar que botões possam ser identificados por tecnologias assistivas.<br>
**Pré-condições:** área autenticada carregada.<br>
**Passos:** inspecionar nome acessível dos botões.<br>
**Resultado esperado:** todos os botões relevantes com nome acessível.<br>
**Resultado atual:** cenário automatizado aprovado.<br>
**Status:** Aprovado.

## CT-DASH-001 — Carregar dashboard

**Objetivo:** validar o carregamento da página inicial autenticada.<br>
**Pré-condições:** login realizado.<br>
**Passos:** acessar o dashboard.<br>
**Resultado esperado:** página, menu e conteúdo principal visíveis.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.

## CT-NAV-001 — Acessar área autenticada

**Objetivo:** validar acesso às rotas protegidas após login.<br>
**Pré-condições:** credenciais válidas.<br>
**Passos:** realizar login e validar a URL autenticada.<br>
**Resultado esperado:** acesso permitido.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.

## CT-NAV-002 — Navegar para veículos

**Objetivo:** validar a navegação pelo menu lateral.<br>
**Pré-condições:** usuário autenticado.<br>
**Passos:** selecionar a opção Veículos.<br>
**Resultado esperado:** tela de veículos carregada.<br>
**Resultado atual:** cenário mantido como condicional/ignorado na regressão atual.<br>
**Status:** Ignorado.

## DISCOVERY-001 — Mapear links da área autenticada

**Objetivo:** identificar rotas e elementos principais disponíveis.<br>
**Pré-condições:** usuário autenticado.<br>
**Passos:** percorrer o menu e registrar os destinos.<br>
**Resultado esperado:** Dashboard, Veículos, Manutenção e Viagens identificados.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.

## CT-VEI-001 — Cadastrar veículo válido

**Objetivo:** garantir o cadastro e a exibição do veículo.<br>
**Pré-condições:** usuário autenticado.<br>
**Dados:** placa única, modelo, tipo e ano válidos.<br>
**Passos:** abrir Veículos, cadastrar e pesquisar pela placa.<br>
**Resultado esperado:** mensagem de sucesso e registro na tabela.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.

## CT-VEI-002 — Impedir veículo com placa duplicada

**Objetivo:** validar a unicidade da placa.<br>
**Pré-condições:** veículo previamente cadastrado.<br>
**Dados:** mesma placa em uma segunda tentativa.<br>
**Passos:** repetir o cadastro.<br>
**Resultado esperado:** operação bloqueada.<br>
**Resultado atual:** cadastro bloqueado, porém a mensagem mistura português e inglês.<br>
**Status:** Aprovado com melhoria UX registrada em `UX-001`.

## CT-MAN-001 — Agendar manutenção válida

**Objetivo:** cadastrar manutenção vinculada a um veículo.<br>
**Pré-condições:** veículo existente.<br>
**Dados:** serviço, datas e custo válidos.<br>
**Passos:** abrir Manutenção, preencher e confirmar.<br>
**Resultado esperado:** mensagem de sucesso e registro na tabela.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.

## CT-MAN-002 — Impedir data final anterior à inicial

**Objetivo:** validar a ordem cronológica da manutenção.<br>
**Pré-condições:** veículo existente.<br>
**Dados:** data final anterior à data inicial.<br>
**Passos:** tentar agendar a manutenção.<br>
**Resultado esperado:** operação bloqueada com mensagem clara.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.

## CT-MAN-004 — Impedir manutenção com custo negativo

**Objetivo:** impedir persistência de custo estimado inválido.<br>
**Pré-condições:** veículo existente.<br>
**Dados:** custo menor que zero.<br>
**Passos:** tentar agendar a manutenção.<br>
**Resultado esperado:** operação bloqueada.<br>
**Resultado atual:** bloqueio realizado, porém o feedback genérico está em inglês.<br>
**Status:** Aprovado com melhoria UX registrada em `UX-004`.

## CT-VIA-001 — Cadastrar viagem válida

**Objetivo:** cadastrar uma viagem vinculada a um veículo.<br>
**Pré-condições:** veículo existente.<br>
**Dados:** origem, destino, datas e distância válidos.<br>
**Passos:** abrir Viagens, preencher e confirmar.<br>
**Resultado esperado:** mensagem de sucesso e registro na tabela.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.

## E2E-001 — Gerenciamento completo da frota

**Objetivo:** validar a integração dos principais módulos.<br>
**Pré-condições:** usuário válido e ambiente disponível.<br>
**Dados:** veículo, manutenção e viagem gerados dinamicamente.<br>
**Passos:** autenticar, cadastrar veículo, agendar manutenção, cadastrar viagem e consultar os registros.<br>
**Resultado esperado:** todos os módulos concluídos e registros encontrados.<br>
**Resultado atual:** comportamento conforme esperado.<br>
**Status:** Aprovado.<br>
**Evidência:** screenshot, vídeo, trace, logs e Allure público.

---

# Defeitos conhecidos

## CT-VIA-002 — Impedir chegada anterior à saída

**Resultado esperado:** viagem bloqueada.<br>
**Resultado atual:** a aplicação permite o cadastro e apresenta sucesso.<br>
**Status:** Reprovado.<br>
**Defeito:** `BUG-TRIP-001`.<br>
**Issue:** https://github.com/fernandounbandeira060712/logitrack-pro-quality-engineering/issues/1

## CT-VIA-004 — Impedir quilometragem negativa

**Resultado esperado:** viagem bloqueada.<br>
**Resultado atual:** a aplicação persiste valor negativo.<br>
**Status:** Reprovado.<br>
**Defeito:** `BUG-TRIP-002`.<br>
**Issue:** https://github.com/fernandounbandeira060712/logitrack-pro-quality-engineering/issues/6

## CT-VIA-005 — Impedir quilometragem igual a zero

**Resultado esperado:** viagem bloqueada.<br>
**Resultado atual:** a aplicação persiste valor zero.<br>
**Status:** Reprovado.<br>
**Defeito:** `BUG-TRIP-003`.<br>
**Issue:** https://github.com/fernandounbandeira060712/logitrack-pro-quality-engineering/issues/7
