# EvidÃªncias de Teste

## EvidÃªncias automÃ¡ticas

A execuÃ§Ã£o local gera:

- `target/allure-results`;
- `target/screenshots`;
- `target/videos`;
- `target/traces`;
- `target/logs`;
- `target/surefire-reports`.

Essas pastas sÃ£o geradas em tempo de execuÃ§Ã£o e nÃ£o devem ser versionadas.

## EvidÃªncias permanentes

EvidÃªncias relevantes para defeitos e documentaÃ§Ã£o devem ser copiadas para:

- `evidence/defects`;
- `evidence/screenshots`;
- `docs/defects`.

## Allure Report

O relatÃ³rio Ã© aberto automaticamente apÃ³s a execuÃ§Ã£o local pelo comando:

```powershell
powershell -ExecutionPolicy Bypass -File .\executar-testes-com-allure.ps1 `
  -Email "<EMAIL>" `
  -Password "<SENHA>" `
  -Headless
```

Na pipeline, os resultados e evidÃªncias sÃ£o publicados como artefatos do GitHub Actions.

## Playwright Trace

Os arquivos ZIP podem ser analisados pelo Playwright Trace Viewer e contÃªm:

- aÃ§Ãµes;
- screenshots;
- snapshots;
- rede;
- console;
- cÃ³digo-fonte relacionado Ã  execuÃ§Ã£o.