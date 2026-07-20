## 🐞 Descrição

O sistema permite cadastrar uma viagem cuja **data de chegada é anterior à data de saída**, gerando uma inconsistência operacional na programação da frota.

## 📌 Identificação

| Campo | Valor |
|---|---|
| ID | BUG-TRIP-001 |
| Módulo | Viagens |
| Severidade | Alta |
| Prioridade | Alta |
| Status | Confirmado |
| Ambiente | LogiTrack Pro – ambiente do desafio |
| Navegador | Chromium |
| Sistema operacional | Windows 11 |
| Data da identificação | 20/07/2026 |

## ✅ Pré-condições

- Usuário autenticado na aplicação.
- Existência de um veículo disponível para seleção.
- Acesso à tela **Viagens**.

## 🧪 Dados utilizados

| Campo | Valor |
|---|---|
| Veículo | QAT9A72 – Modelo QA Automatizado |
| Origem | Origem QAT9A72 |
| Destino | Destino QAT9A72 |
| Data de saída | 25/07/2026 |
| Data de chegada | 24/07/2026 |
| Quilometragem | 220.5 km |

## ▶️ Passos para reprodução

1. Acessar a aplicação com um usuário válido.
2. Abrir o menu **Viagens**.
3. Clicar em **Nova Viagem**.
4. Selecionar um veículo.
5. Preencher origem e destino.
6. Informar a data de saída como `25/07/2026`.
7. Informar a data de chegada como `24/07/2026`.
8. Informar a quilometragem.
9. Confirmar o cadastro.
10. Consultar a tabela de viagens.

## 🎯 Resultado esperado

- O sistema deve impedir o cadastro.
- Deve ser exibida uma mensagem clara, por exemplo:

> Data de chegada não pode ser anterior à data de saída.

- A viagem não deve ser apresentada na tabela.

## ❌ Resultado obtido

A viagem é cadastrada e permanece visível na tabela mesmo com a data de chegada anterior à data de saída.

## 💥 Impacto

- Permite o registro de dados cronologicamente inválidos.
- Compromete a confiabilidade do planejamento de viagens.
- Pode afetar indicadores operacionais, relatórios e integrações.
- Pode provocar erros em regras dependentes das datas da viagem.

## 📎 Evidência

Arquivo no repositório:

`evidence/defects/BUG-TRIP-001-data-chegada-anterior-saida.png`

![Evidência do defeito](https://raw.githubusercontent.com/fernandounbandeira060712/logitrack-pro-quality-engineering/main/evidence/defects/BUG-TRIP-001-data-chegada-anterior-saida.png)

## 🤖 Cobertura automatizada

Cenário automatizado:

`InvalidTripDateTests.shouldNotAllowTripWithArrivalBeforeDeparture`

O teste deve permanecer como evidência automatizada da regra e demonstrar a falha enquanto o defeito estiver aberto.

## ✅ Critérios de aceite para correção

- [ ] Bloquear data de chegada anterior à data de saída.
- [ ] Exibir mensagem de validação em português.
- [ ] Não persistir a viagem inválida.
- [ ] Manter o formulário preenchido para correção.
- [ ] Garantir que o teste automatizado passe após a correção.
