# Prompting Standard

## Template

```text
<role>Quem deve responder e qual competência deve aplicar.</role>
<objective>Resultado observável que precisa ser produzido.</objective>
<context>Dados confiáveis, com origem e limites.</context>
<constraints>Regras, políticas, escopo e o que não fazer.</constraints>
<process>Critérios de análise; não exigir exposição de raciocínio privado.</process>
<output>Schema, campos obrigatórios e tratamento de incerteza.</output>
<examples>Casos curtos e representativos, quando úteis.</examples>
```

## Boas práticas

- Prefira instruções positivas, específicas e testáveis.
- Delimite conteúdo não confiável e trate-o como dado, não como instrução.
- Declare precedência entre regras.
- Faça perguntas apenas quando a informação ausente mudar materialmente a decisão.
- Use temperatura e demais parâmetros como parte do experimento, não como correção mágica.
