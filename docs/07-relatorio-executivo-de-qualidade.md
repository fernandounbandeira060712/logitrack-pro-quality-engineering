# Relatório Executivo de Qualidade — LogiTrack Pro

## 1. Resumo executivo

O LogiTrack Pro foi avaliado por meio de testes funcionais, negativos, de limite, acessibilidade básica e fluxo E2E.

A solução apresenta bom comportamento nos fluxos principais de autenticação, cadastro de veículos, manutenção e registro válido de viagens. A automação foi integrada ao GitHub Actions e o relatório Allure está disponível publicamente.

Entretanto, foram confirmados três defeitos relevantes no módulo de viagens, relacionados à integridade de datas e quilometragem.

## 2. Indicadores

| Indicador | Resultado |
|---|---|
| Cenários automatizados no conjunto total | 22 |
| Regressão principal | 19 |
| Cenários aprovados na regressão | 18 |
| Cenários ignorados/condicionais | 1 |
| Quality Gate de desenvolvimento | 9 aprovados |
| Defeitos conhecidos automatizados | 3 |
| Defeitos funcionais confirmados | 3 |
| Oportunidades de melhoria | 4 |
| Issues registradas | 7 |
| Relatório público | Disponível no GitHub Pages |

## 3. Cobertura alcançada

- login;
- cadastro;
- dashboard;
- navegação;
- veículos;
- manutenção;
- viagens;
- regras de negócio;
- acessibilidade básica;
- fluxo E2E;
- evidências automatizadas;
- CI/CD.

## 4. Defeitos críticos para o negócio

### BUG-TRIP-001 — Chegada anterior à saída

A aplicação permite persistir uma viagem cuja data de chegada é anterior à data de saída.

**Risco:** inconsistência cronológica e impacto em relatórios operacionais.

### BUG-TRIP-002 — Quilometragem negativa

A aplicação permite cadastrar distância negativa.

**Risco:** cálculos incorretos de consumo, custo e desempenho.

### BUG-TRIP-003 — Quilometragem igual a zero

A aplicação permite cadastrar uma viagem concluída com distância igual a zero.

**Risco:** registros sem significado operacional e indicadores incorretos.

## 5. Melhorias de experiência

- padronização de idioma;
- identificação dos campos obrigatórios;
- melhor descrição das ações por ícones;
- mensagens de validação específicas e em português.

## 6. Avaliação por área

| Área | Avaliação | Observação |
|---|---|---|
| Autenticação | Boa | Fluxos principais aprovados |
| Cadastro | Boa | Regra de senha validada |
| Veículos | Boa | Unicidade de placa aplicada |
| Manutenção | Boa | Datas e custo negativo bloqueados |
| Viagens | Atenção crítica | Três regras de integridade apresentam falhas |
| Acessibilidade | Inicial | Cobertura básica implementada |
| UX | Moderada | Quatro melhorias registradas |
| Automação | Boa | Arquitetura reutilizável e evidências |
| CI/CD | Boa | Quality Gate, regressão e Pages |
| Observabilidade de testes | Boa | Screenshot, vídeo, trace e logs |

## 7. Parecer de qualidade

**Situação recomendada: aprovação condicionada.**

Os fluxos principais apresentam estabilidade, mas o módulo de viagens possui defeitos que podem comprometer a integridade das informações da frota.

Antes de uma liberação produtiva, recomenda-se corrigir e retestar:

1. validação de chegada anterior à saída;
2. rejeição de quilometragem negativa;
3. rejeição de quilometragem igual a zero.

## 8. Recomendações prioritárias

### Prioridade crítica

- corrigir `BUG-TRIP-001`;
- corrigir `BUG-TRIP-002`;
- corrigir `BUG-TRIP-003`;
- validar as regras também no backend;
- adicionar testes de API e banco.

### Prioridade alta

- padronizar mensagens;
- ampliar acessibilidade;
- executar matriz cross-browser;
- monitorar histórico do Allure.

## 9. Evidências

- GitHub Issues;
- relatório Allure público;
- screenshots;
- vídeos;
- Playwright Trace;
- logs;
- documentação em `docs/`;
- pipelines do GitHub Actions.

## 10. Conclusão

O projeto demonstra maturidade de Engenharia de Qualidade ao integrar estratégia, automação, gestão de defeitos, rastreabilidade, evidências e entrega contínua.

A arquitetura e o pipeline estão preparados para evolução. O principal risco remanescente está concentrado nas validações de integridade do módulo de viagens.
