# Test Suite: Relatórios e Testes Automáticos (Unitários)

TMS-ID: TMS-CHK-001

## Propósito
Descrever o conjunto de operações a executar na diretoria de checklists: gerar relatórios (cobertura, javadoc, etc.) e executar testes automáticos unitários.

## Objetivo
Garantir que o projeto produz:
- Relatórios de qualidade e cobertura.
- Execução fiável dos testes unitários (JUnit).
- Artefactos para análise (Allure, cobertura).

## Escopo
Aplica-se a todo o ciclo de integração local e CI que envolve:
- `mvn test`
- geração de relatórios (`mvn site`, Cobertura)
- recolha de resultados de testes (Allure )

## Tipos de operações
- Relatórios:
    - Gerar javadoc.
    - Gerar relatório de cobertura (Cobertura/JaCoCo).
    - Gerar site do projecto (`mvn site`).
- Testes automáticos — unitários:
    - Executar testes.
    - Publicar resultados (Allure / XML).
    - Validar que não há falhas regressivas.

## Test Suite: passos principais
1. Limpar e compilar o projecto:
    - `mvn clean compile`
2. Executar testes unitários:
    - `mvn test`
    - Validar saída JUnit e códigos de exit.
3. Gerar relatórios:
    - `mvn site` / plugin de cobertura
    - Exportar relatórios para `target/site` e directórios de reports.
4. Recolher e publicar resultados:
    - Gerar resultados Allure (se configurado).
    - Anexar relatórios à pipeline / diretório `reports/`.
5. Verificação final:
    - Confirmar pass/fail dos testes.
    - Confirmar existência dos relatórios esperados.

## Critérios de aceitação
- Todos os testes unitários devem passar.
- Relatórios gerados estão presentes em `target/site` e `reports/`.
- Resultados de cobertura e Allure (se configurado) são exportados.

## Observações
- Incluir dependência Allure em `pom.xml` para recolha de anotações e relatórios de teste.
- Ajustar pipeline CI para executar esta Test Suite automaticamente.
