# Matriz de Rastreabilidade — LogiTrack Pro

## Requisitos da entrega

| Requisito | Evidência | Situação |
|---|---|---|
| Mínimo de 10 cenários | `docs/02-cenarios-de-teste.md` | Atendido |
| ID e título | Cenários CT, DISCOVERY e E2E | Atendido |
| Objetivo | Presente nos cenários documentados | Atendido |
| Pré-condições | Presente nos cenários aplicáveis | Atendido |
| Dados de teste | Presentes nos cenários aplicáveis | Atendido |
| Passos | Presentes nos cenários | Atendido |
| Resultado esperado | Presente nos cenários | Atendido |
| Resultado atual | Presente nos cenários | Atendido |
| Status | Aprovado, Reprovado ou Ignorado | Atendido |
| Evidência | Allure, imagens, vídeos, traces e logs | Atendido |
| Defeitos documentados | GitHub Issues #1, #6 e #7 | Atendido |
| Mínimo de 3 melhorias UX | GitHub Issues #2 a #5 | Atendido |
| Estratégia de testes | `docs/01-plano-e-estrategia-de-testes.md` | Atendido |
| Testes adicionais | `docs/04-estrategia-de-testes-adicionais.md` | Atendido |
| Repositório público | GitHub | Atendido |
| README profissional | `README.md` | Atendido |
| Relatório público | GitHub Pages | Atendido |

## Regras de negócio e automação

| Regra | Cenário automatizado | Classe | Evidência |
|---|---|---|---|
| RN-001 — autenticação válida | CT-LOGIN-001 | `LoginTests` | Allure |
| RN-002 — senha mínima | CT-CAD-002 | `RegistrationTests` | Allure |
| RN-003 — placa única | CT-VEI-002 | `DuplicateVehiclePlateTests` | Allure e Issue #2 |
| RN-004 — manutenção vinculada | CT-MAN-001 | `MaintenanceSchedulingTests` | Allure |
| RN-005 — ordem das datas da manutenção | CT-MAN-002 | `InvalidMaintenanceDateTests` | Allure |
| RN-006 — custo não negativo | CT-MAN-004 | `NegativeMaintenanceCostTests` | Allure e Issue #5 |
| RN-007 — viagem vinculada | CT-VIA-001 | `TripRegistrationTests` | Allure |
| RN-008 — chegada não anterior | CT-VIA-002 | `InvalidTripDateTests` | Issue #1 |
| RN-009 — quilometragem maior que zero | CT-VIA-004 e CT-VIA-005 | `InvalidTripDistanceTests` | Issues #6 e #7 |
| RN-010 — feedback compreensível | Testes de toast e UX | Classes funcionais | Issues #2 e #5 |

## Suítes e pipelines

| Artefato | Finalidade |
|---|---|
| `DevQualityGateSuite` | Validação rápida de desenvolvimento |
| `FullRegressionSuite` | Regressão principal |
| `KnownDefectsSuite` | Reprodução de bugs conhecidos |
| `dev-quality-gate.yml` | Quality Gate em push e PR |
| `main-regression-allure-pages.yml` | Regressão e publicação do Allure |
| `known-defects.yml` | Reprodução manual de defeitos |

## Defeitos

| ID | Regra | Teste | Issue |
|---|---|---|---|
| BUG-TRIP-001 | RN-008 | CT-VIA-002 | #1 |
| BUG-TRIP-002 | RN-009 | CT-VIA-004 | #6 |
| BUG-TRIP-003 | RN-009 | CT-VIA-005 | #7 |

## Melhorias

| ID | Área | Issue |
|---|---|---|
| UX-001 | Idioma da placa duplicada | #2 |
| UX-002 | Campos obrigatórios | #3 |
| UX-003 | Ações por ícones | #4 |
| UX-004 | Feedback de validação | #5 |
