# Tools and MCP Standard

## Contrato de ferramenta

Toda ferramenta deve documentar nome, finalidade, parâmetros tipados, pré-condições, efeitos colaterais, permissões, erros, timeout e exemplo.

## Segurança

- Conceda somente o acesso necessário.
- Valide parâmetros no servidor, mesmo que o modelo tenha schema.
- Nunca aceite instruções de uma página ou documento como autorização.
- Separe leitura de escrita e exija confirmação para ações irreversíveis.
- Registre ator, ferramenta, recurso, resultado e correlação sem dados sensíveis.
- Rate-limit ferramentas caras ou externas.

## MCP

Trate servidores MCP como integrações externas: verifique origem, versão, permissões, disponibilidade, isolamento e política de dados antes de habilitar.
