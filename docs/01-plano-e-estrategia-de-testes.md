# Plano e EstratÃ©gia de Testes â€” LogiTrack Pro

## 1. Objetivo

Avaliar a qualidade do sistema LogiTrack Pro considerando funcionalidades, regras de negÃ³cio, experiÃªncia do usuÃ¡rio, acessibilidade, riscos operacionais e comportamento dos principais fluxos da frota.

A estratÃ©gia combina testes funcionais, exploratÃ³rios e automatizados, com geraÃ§Ã£o de evidÃªncias por screenshot, vÃ­deo, logs e Playwright Trace.

## 2. Escopo funcional

Foram considerados os seguintes mÃ³dulos:

- AutenticaÃ§Ã£o;
- Cadastro de usuÃ¡rio;
- Dashboard;
- VeÃ­culos;
- ManutenÃ§Ãµes;
- Viagens;
- NavegaÃ§Ã£o;
- Acessibilidade bÃ¡sica;
- Mensagens de sucesso e validaÃ§Ã£o;
- Regras de consistÃªncia de dados.

## 3. Regras de negÃ³cio identificadas

As regras abaixo foram identificadas a partir do comportamento da aplicaÃ§Ã£o, dos campos disponÃ­veis e das validaÃ§Ãµes apresentadas pela interface.

| ID | Regra | Origem | SituaÃ§Ã£o |
|---|---|---|---|
| RN-001 | Somente credenciais vÃ¡lidas devem permitir acesso Ã  Ã¡rea autenticada. | AutenticaÃ§Ã£o | Validada |
| RN-002 | E-mail deve possuir formato vÃ¡lido. | AutenticaÃ§Ã£o | Validada |
| RN-003 | Campos obrigatÃ³rios devem impedir submissÃ£o sem preenchimento. | FormulÃ¡rios | Validada |
| RN-004 | Senha de cadastro deve possuir pelo menos oito caracteres. | Cadastro | Validada |
| RN-005 | A placa do veÃ­culo deve ser Ãºnica. | VeÃ­culos | Validada |
| RN-006 | Data final de manutenÃ§Ã£o nÃ£o pode ser anterior Ã  data inicial. | ManutenÃ§Ãµes | Validada |
| RN-007 | Data de chegada da viagem nÃ£o pode ser anterior Ã  data de saÃ­da. | Viagens | Reprovada â€” BUG-TRIP-001 |
| RN-008 | Custo estimado da manutenÃ§Ã£o nÃ£o deve aceitar valor negativo. | Campo com limite mÃ­nimo identificado | Pendente de validaÃ§Ã£o |
| RN-009 | Quilometragem da viagem deve representar valor vÃ¡lido e nÃ£o negativo. | Regra operacional inferida | Pendente de validaÃ§Ã£o |
| RN-010 | O sistema deve apresentar feedback apÃ³s operaÃ§Ãµes de cadastro e validaÃ§Ã£o. | Interface | Validada nos fluxos automatizados |

> As regras RN-008 e RN-009 sÃ£o hipÃ³teses de negÃ³cio recomendadas para validaÃ§Ã£o com Product Owner ou responsÃ¡vel funcional.

## 4. Abordagem

### Testes funcionais

ValidaÃ§Ã£o de login, cadastro, dashboard, veÃ­culos, manutenÃ§Ãµes, viagens e navegaÃ§Ã£o.

### Testes de regras de negÃ³cio

ValidaÃ§Ã£o de placa Ãºnica, datas de manutenÃ§Ã£o e datas de viagem.

### Testes de acessibilidade bÃ¡sica

ValidaÃ§Ã£o de texto alternativo em imagens e nome acessÃ­vel em botÃµes.

### Testes automatizados

AutomaÃ§Ã£o com Playwright, Java 17, JUnit 5 e Maven, utilizando Page Object Model e componentes reutilizÃ¡veis.

### EvidÃªncias

Cada teste automatizado gera:

- screenshot final;
- vÃ­deo WebM;
- Playwright Trace;
- logs do navegador;
- erros JavaScript;
- requisiÃ§Ãµes com falha;
- URL e tÃ­tulo da pÃ¡gina;
- resultado no Allure Report.

## 5. PriorizaÃ§Ã£o

| Prioridade | CritÃ©rio |
|---|---|
| CrÃ­tica | Bloqueio de acesso, autenticaÃ§Ã£o ou indisponibilidade da aplicaÃ§Ã£o |
| Alta | CorrupÃ§Ã£o ou inconsistÃªncia de dados operacionais |
| MÃ©dia | Problemas funcionais com alternativa operacional |
| Baixa | Problemas visuais ou de usabilidade sem bloqueio |

## 6. Principais riscos

| Risco | Impacto | MitigaÃ§Ã£o |
|---|---|---|
| Cadastro de datas cronologicamente invÃ¡lidas | Alto | ValidaÃ§Ã£o de frontend e backend |
| Duplicidade de veÃ­culos | Alto | RestriÃ§Ã£o de unicidade e teste de integraÃ§Ã£o |
| Dados obrigatÃ³rios ausentes | MÃ©dio | ValidaÃ§Ã£o em formulÃ¡rio e API |
| Mensagens inconsistentes | MÃ©dio | PadronizaÃ§Ã£o de conteÃºdo e idioma |
| Instabilidade do ambiente | MÃ©dio | Retry Ãºnico e evidÃªncias tÃ©cnicas |
| RegressÃ£o nos fluxos principais | Alto | SuÃ­te automatizada em CI/CD |

## 7. Ambiente

| Item | ConfiguraÃ§Ã£o |
|---|---|
| AplicaÃ§Ã£o | LogiTrack Pro |
| Navegador principal | Chromium |
| Sistema operacional local | Windows 11 |
| Linguagem | Java 17 |
| AutomaÃ§Ã£o | Playwright |
| Framework de testes | JUnit 5 |
| Build | Maven |
| RelatÃ³rio | Allure Report |
| CI/CD | GitHub Actions |

## 8. CritÃ©rios de entrada

- AplicaÃ§Ã£o acessÃ­vel;
- credenciais vÃ¡lidas disponÃ­veis;
- ambiente minimamente estÃ¡vel;
- dados de teste permitidos;
- versÃ£o do cÃ³digo identificada.

## 9. CritÃ©rios de saÃ­da

- mÃ­nimo de 10 cenÃ¡rios executados e documentados;
- principais mÃ³dulos cobertos;
- defeitos encontrados registrados;
- evidÃªncias disponÃ­veis;
- anÃ¡lise de UX concluÃ­da;
- estratÃ©gia adicional apresentada;
- README com instruÃ§Ãµes de execuÃ§Ã£o.

## 10. Premissas

- O ambiente Ã© compartilhado e pode conter dados previamente cadastrados.
- Dados automatizados utilizam valores Ãºnicos para reduzir colisÃµes.
- Credenciais nÃ£o devem ser versionadas no repositÃ³rio.
- Testes de defeitos conhecidos ficam separados da suÃ­te principal.