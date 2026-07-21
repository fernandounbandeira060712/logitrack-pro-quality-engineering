# Plano e Estratégia de Testes — LogiTrack Pro

## 1. Objetivo

Avaliar a qualidade do LogiTrack Pro considerando funcionalidades, regras de negócio, experiência do usuário, acessibilidade, riscos operacionais e confiabilidade dos fluxos de gestão de frota.

A estratégia combina testes funcionais, exploratórios e automatizados, com geração de evidências por screenshot, vídeo, Playwright Trace, logs e relatório Allure.

## 2. Escopo

### Funcionalidades cobertas

- autenticação;
- cadastro de usuário;
- dashboard;
- navegação autenticada;
- cadastro e consulta de veículos;
- agendamento e consulta de manutenções;
- cadastro e consulta de viagens;
- acessibilidade básica;
- fluxo E2E de gerenciamento da frota.

### Fora do escopo atual

- testes de API e contrato;
- validação direta no banco de dados;
- carga e estresse;
- segurança ofensiva;
- matriz completa de navegadores;
- testes mobile nativos.

Esses itens estão priorizados em `04-estrategia-de-testes-adicionais.md`.

## 3. Abordagem

| Abordagem | Aplicação |
|---|---|
| Testes funcionais | Validação dos fluxos e mensagens da aplicação |
| Testes negativos | Credenciais, datas, valores e regras inválidas |
| Testes de limite | Senha mínima, custo negativo e quilometragem zero |
| Testes exploratórios | Descoberta de comportamento, inconsistências e oportunidades de UX |
| Testes E2E | Validação integrada de veículo, manutenção e viagem |
| Acessibilidade básica | Texto alternativo e nome acessível de botões |
| Regressão automatizada | Execução da suíte principal no GitHub Actions |
| Defeitos conhecidos | Reprodução segregada em suíte específica |

## 4. Regras de negócio consideradas

| ID | Regra |
|---|---|
| RN-001 | O acesso autenticado exige credenciais válidas |
| RN-002 | A senha de cadastro deve respeitar o limite mínimo definido pela aplicação |
| RN-003 | A placa do veículo deve ser única |
| RN-004 | A manutenção deve estar vinculada a um veículo existente |
| RN-005 | A data final da manutenção não pode ser anterior à data inicial |
| RN-006 | O custo estimado da manutenção não pode ser negativo |
| RN-007 | A viagem deve estar vinculada a um veículo existente |
| RN-008 | A chegada da viagem não pode ser anterior à saída |
| RN-009 | A quilometragem da viagem deve ser maior que zero |
| RN-010 | Operações concluídas devem apresentar feedback compreensível ao usuário |

## 5. Riscos priorizados

| Risco | Impacto | Probabilidade | Prioridade | Cobertura |
|---|---|---|---|---|
| Persistência de datas inválidas | Alto | Alta | Crítica | Manutenção e viagem |
| Persistência de quilometragem inválida | Alto | Alta | Crítica | Viagem |
| Duplicidade de placa | Alto | Média | Alta | Veículos |
| Falha no vínculo entre módulos | Alto | Média | Alta | E2E |
| Mensagens inconsistentes | Médio | Alta | Média | UX e validação |
| Barreiras de acessibilidade | Médio | Média | Média | Acessibilidade |
| Regressão em autenticação | Alto | Média | Alta | Login e cadastro |

## 6. Priorização

### P0 — Crítica

- login válido;
- cadastro de veículo;
- unicidade de placa;
- agendamento de manutenção;
- validações de datas;
- validações de quilometragem;
- fluxo E2E.

### P1 — Alta

- cadastro de usuário;
- dashboard;
- navegação;
- custo de manutenção;
- mensagens de confirmação e erro.

### P2 — Média

- acessibilidade básica;
- padronização visual e textual;
- análise exploratória de UX.

## 7. Ambiente

| Item | Configuração |
|---|---|
| Aplicação | LogiTrack Pro |
| Automação | Playwright |
| Linguagem | Java 17 |
| Framework | JUnit 5 |
| Build | Maven |
| Navegador principal | Chromium |
| CI/CD | GitHub Actions |
| Evidências | Allure, screenshot, vídeo, trace e logs |
| Relatório público | GitHub Pages |

## 8. Critérios de entrada

- aplicação disponível;
- credenciais válidas configuradas;
- Java 17 e Maven disponíveis;
- Chromium instalado pelo Playwright;
- massa de dados gerada de forma isolada;
- GitHub Secrets configurados para execução em CI.

## 9. Critérios de saída

- suíte DEV sem falhas;
- regressão principal sem falhas não conhecidas;
- defeitos encontrados registrados com evidência;
- melhorias de UX documentadas;
- relatório Allure publicado;
- documentação e matriz de rastreabilidade atualizadas.

## 10. Estratégia de automação

A automação utiliza:

- Page Object Model;
- Component Objects;
- fábrica de dados;
- configuração centralizada;
- isolamento de contexto do navegador;
- execução paralela;
- retry configurável;
- anexos automáticos;
- suítes separadas por finalidade.

## 11. Suítes

| Suíte | Finalidade |
|---|---|
| `DevQualityGateSuite` | Feedback rápido para desenvolvimento e Pull Requests |
| `FullRegressionSuite` | Regressão funcional principal |
| `KnownDefectsSuite` | Reprodução controlada dos defeitos confirmados |

## 12. Resultado atual

- 22 cenários automatizados no conjunto total;
- 19 cenários na regressão principal;
- 9 cenários no Quality Gate de desenvolvimento;
- 3 cenários segregados como defeitos conhecidos;
- 3 defeitos funcionais confirmados;
- 4 oportunidades de melhoria de UX;
- relatório Allure publicado no GitHub Pages.
