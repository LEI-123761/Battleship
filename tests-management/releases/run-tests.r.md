# run-testes

TMS-ID: TMS-RUN-001
Test Run: Registro de Execução — Relatórios e Testes Automáticos (Unitários)

## Metadata
- Run ID: TMS-RUN-001
- Date: 2025-11-21
- Executed by: CI / local
- Environment: Java 17, Maven 3.8.x, OS: Windows
- Branch / Commit: setup-tms / (fill in commit)
- Commands executed:
    - `mvn clean compile`
    - `mvn test`
    - `mvn site`

## Summary
- Tests executed: 11
- Passed: (fill in)
- Failed: (fill in)
- Skipped: (fill in)
- Coverage report generated: `target/site/jacoco/index.html` (if configured)
- Allure results directory: `target/allure-results/` (if configured)
- Site reports: `target/site/`

## Acceptance criteria
- \[ \] Todos os testes unitários devem passar.
- \[ \] Relatórios gerados estão presentes em `target/site` e `reports/`.
- \[ \] Resultados de cobertura e Allure (se configurado) são exportados.

## Execution notes
- JUnit output: `target/surefire-reports/` (JUnit XML and console logs)
- Exit code: (fill in)
- Errors / stack traces: (link or paste excerpt)

## Test Suite: passos executados
1. Limpeza e compilação:
    - Command: `mvn clean compile`
    - Status: (fill in)
2. Execução de testes unitários:
    - Command: `mvn test`
    - Status: (fill in)
3. Geração de relatórios:
    - Command: `mvn site` / cobertura plugin
    - Status: (fill in)
4. Recolha de resultados:
    - Allure generation (if configured): Status: (fill in)
    - Reports copied to `reports/`: Status: (fill in)

## Test Run: resultados por caso (Unit Tests)
- AppTest \- TMS-ID: TMS-TC-001 \- Status: PASSED \- EXECUTED BY: @LEI-123761 \- Report: `target/surefire-reports/`
- BargeTest \- TMS-ID: TMS-TC-002 \- Status: PASSED \- EXECUTED BY: @LEI-123761 \- Report: `target/surefire-reports/`
- CaravelTest \- TMS-ID: TMS-TC-003 \- Status: PASSED \- EXECUTED BY: @LEI-123761 \- Report: `target/surefire-reports/`
- CarrackTest \- TMS-ID: TMS-TC-004 \- Status: PASSED \- EXECUTED BY: @LEI-123761 \- Report: `target/surefire-reports/`
- CompassTest \- TMS-ID: TMS-TC-005 \- Status: PASSED \- EXECUTED BY: @LEI-123761 \- Report: `target/surefire-reports/`
- FleetTest \- TMS-ID: TMS-TC-006 \- Status: PASSED \- EXECUTED BY: @LEI-123761 \- Report: `target/surefire-reports/`
- FrigateTest \- TMS-ID: TMS-TC-007 \- Status: PASSED \- EXECUTED BY: @LEI-105514 \- Report: `target/surefire-reports/`
- GalleonTest \- TMS-ID: TMS-TC-008 \- Status: PASSED \- EXECUTED BY: @LEI-105514 \- Report: `target/surefire-reports/`
- GameTest \- TMS-ID: TMS-TC-009 \- Status: PASSED \- EXECUTED BY: @LEI-105514 \- Report: `target/surefire-reports/`
- PositionTest \- TMS-ID: TMS-TC-010 \- Status: PASSED \- EXECUTED BY: @LEI-105514 \- Report: `target/surefire-reports/`
- ShipTest \- TMS-ID: TMS-TC-011 \- Status: PASSED \- EXECUTED BY: @LEI-105514 \- Report: `target/surefire-reports/`

## Artifacts
- JUnit XML / Surefire: `target/surefire-reports/`
- Coverage (JaCoCo/Cobertura): `target/site/` (e.g. `target/site/jacoco/index.html`)
- Allure results: `target/allure-results/` (if enabled)
- Aggregated reports: `reports/` (ci/artifacts)

## Observations / Actions
- CI pipeline should upload `target/site` and `reports/` to run artifacts.
- If failures occur, attach failing test stack traces and link to reproducer.
- Add Allure plugin and configuration to `pom.xml` if Allure reports are required.