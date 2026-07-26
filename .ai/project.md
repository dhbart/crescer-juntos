# \# Project Context

# 

# \## Objetivo

# 

# O Crescer Juntos é uma aplicação para apoiar famílias no acompanhamento da rotina, das responsabilidades e dos combinados das crianças.

# 

# O MVP será uma API REST que permitirá ao responsável:

# 

# \- cadastrar crianças;

# \- criar tarefas recorrentes ou vinculadas a períodos personalizados;

# \- configurar tarefas diárias, semanais e mensais;

# \- acompanhar checklists por período;

# \- registrar a conclusão das tarefas;

# \- aprovar ou rejeitar execuções;

# \- atribuir pontos positivos;

# \- cadastrar recompensas;

# \- controlar solicitações de resgate.

# 

# A aplicação deve incentivar autonomia, clareza e reconhecimento positivo. Consequências e pontuações negativas poderão ser adicionadas em uma evolução posterior, sempre como regras configuráveis e sob confirmação do responsável.

# 

# Usuários principais:

# 

# \- Responsáveis: configuram regras, tarefas, pontos e recompensas, além de aprovarem registros.

# \- Crianças: visualizam suas responsabilidades, registram conclusões e acompanham seu progresso.

# 

# Resultado esperado do MVP: disponibilizar uma API documentada, testada e publicada em nuvem, servindo como base para uma futura aplicação web ou mobile.

# 

# \## Restrições

# 

# \- Modelos e provedores permitidos: Java 21, Spring Boot compatível, Spring Data JPA, PostgreSQL, OpenAPI/Swagger, Gradle e Railway para deploy.

# \- Uso de IA: agentes podem apoiar especificação, implementação, testes, revisão e documentação. Toda alteração deve ser revisada pelo responsável pelo projeto e validada por testes.

# \- Dados que não podem sair do ambiente: dados reais de crianças, famílias, credenciais, tokens e variáveis de ambiente. O desenvolvimento deve usar dados fictícios.

# \- Requisitos de latência e disponibilidade: não definidos para o MVP. Priorizar correção, simplicidade, observabilidade básica e baixo custo.

# \- Orçamento: utilizar preferencialmente ferramentas e serviços gratuitos ou de baixo custo durante o desenvolvimento e a validação.

# \- Requisitos regulatórios, incluindo LGPD: coletar somente os dados necessários; evitar nome completo, fotos, localização e outros dados sensíveis no MVP; restringir o acesso aos dados à família autorizada; não expor perfis infantis publicamente; manter segredos fora do código; preparar política de privacidade antes de uma distribuição comercial.

# \- Escopo do MVP: não incluir autenticação avançada, notificações, aplicativo mobile, integração financeira ou mecanismos automáticos de punição.

# 

# \## Padrões locais

# 

# \- O projeto deve seguir uma arquitetura em camadas: Domain, Controller, Service, Repository, Entity e DTOs.

# \- Regras de negócio devem ficar na camada de serviço, e não nos controllers.

# \- Entidades de persistência não devem ser expostas diretamente pela API; utilizar DTOs quando apropriado.

# \- Toda entrada externa deve possuir validação e mensagens de erro claras.

# \- Utilizar tratamento global de exceções e respostas HTTP consistentes.

# \- Toda regra de negócio relevante deve possuir testes automatizados.

# \- Tarefas configuradas devem ser separadas das execuções geradas por data ou período, preservando o histórico mesmo quando a configuração for alterada.

# \- Pontos aplicados devem ser registrados na execução da tarefa para evitar alterações retroativas no histórico.

# \- Frequências previstas no MVP: `DIARIA`, `SEMANAL`, `MENSAL` e `PERIODO\_PERSONALIZADO`.

# \- Status devem ser representados por enums ou tipos equivalentes, sem strings espalhadas pelo código.

# \- Não utilizar valores mágicos; limites e regras configuráveis devem ser nomeados e documentados.

# \- Documentar endpoints com OpenAPI/Swagger.

# \- Usar migrations versionadas para alterações no banco quando essa ferramenta for incorporada ao projeto.

# \- Manter variáveis de ambiente documentadas em `.env.example`, sem incluir segredos reais no repositório.

# \- Cada entrega deve informar arquivos alterados, testes executados, decisões tomadas e limitações conhecidas.

# 

# \## Regra de atualização

# 

# Este arquivo deve refletir o contexto atual do projeto. Não use exemplos ou suposições como se fossem fatos.

# 

# Atualize-o quando houver mudanças em:

# 

# \- objetivo ou público do produto;

# \- escopo do MVP;

# \- stack ou serviços utilizados;

# \- requisitos de privacidade e segurança;

# \- padrões de arquitetura;

# \- decisões que afetem o trabalho dos agentes.

# 

# Decisões específicas de implementação devem ser registradas em `docs/adr/`, e não acumuladas neste arquivo.

