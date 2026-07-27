# ADR 002: Separação entre Domain Model e JPA Entities

## Status
Aceito

## Contexto
O projeto inicial tinha as classes de domínio misturadas com anotações JPA (@Entity, @Column, etc.), violando os princípios do Domain-Driven Design (DDD). Isso criava um acoplamento forte entre a lógica de negócio e o framework de persistência.

## Decisão
Separamos completamente as classes de domínio das entities JPA:

### Estrutura Implementada

```
domain/
  model/                    # Classes de domínio PURAS
    - Familia.java
    - Crianca.java
    - Tarefa.java
    - ExecucaoTarefa.java
    - Recompensa.java
    - Resgate.java

infrastructure/
  persistence/
    entity/                 # JPA Entities
      - FamiliaEntity.java
      - CriancaEntity.java
      - TarefaEntity.java
      - ExecucaoTarefaEntity.java
      - RecompensaEntity.java
      - ResgateEntity.java
    
    mapper/                 # Conversores Domain <-> Entity
      - FamiliaEntityMapper.java
      - CriancaEntityMapper.java
      - TarefaEntityMapper.java
      - ExecucaoTarefaEntityMapper.java
      - RecompensaEntityMapper.java
      - ResgateEntityMapper.java
    
    # Repositories JPA (trabalham com Entities)
    - JpaFamiliaRepository.java
    - JpaCriancaRepository.java
    - etc.
```

### Classes de Domínio (Puras)
- **Sem Bean Validation**: Validações através de métodos de negócio
- **Relacionamentos por ID**: Usam Long familiaId em vez de objetos Familia
- **Imutabilidade parcial**: Campos finais onde apropriado, modificação via métodos de negócio
- **Lógica de negócio rica**: Métodos como `ativar()`, `desativar()`, `atualizarNome()`

### JPA Entities
- **Todas anotações JPA**: @Entity, @Table, @Id, @GeneratedValue, @ManyToOne, etc.
- **Bean Validation**: @NotNull, @NotBlank, @Min, @Max, @Past
- **Timestamps automáticos**: @CreationTimestamp, @UpdateTimestamp
- **Relacionamentos JPA**: @ManyToOne, @OneToMany com FetchType.LAZY
- **Sem lógica de negócio**: Apenas getters/setters

### Entity Mappers
- **Conversão bidirecion**: `toEntity(domain)` e `toDomain(entity)`
- **Injeção de dependências relacionadas**: Recebem entities de relacionamentos como parâmetros
- **@Component**: Gerenciados pelo Spring para injeção

### Services
- **Trabalham com domínio**: Recebem e retornam objetos de domínio
- **Convertem na borda**: Usam mappers apenas para persistência
- **Aplicam regras de negócio**: Chamam métodos do domínio antes de persistir

## Consequências

### Positivas
✅ **Independência de framework**: Domínio não conhece JPA/Hibernate
✅ **Testabilidade**: Testar domínio sem banco de dados
✅ **Flexibilidade**: Trocar tecnologia de persistência sem afetar domínio
✅ **DDD puro**: Domínio expressa apenas regras de negócio
✅ **Separação clara**: Concerns de persistência isolados
✅ **Manutenibilidade**: Mudanças em persistência não afetam domínio

### Negativas
⚠️ **Mais código**: Duplicação de estrutura entre Domain e Entity
⚠️ **Mappers**: Necessidade de manter conversores sincronizados
⚠️ **Performance**: Overhead de conversão (mínimo na prática)
⚠️ **Curva de aprendizado**: Time precisa entender a separação

### Mitigações
- Mappers são simples e declarativos (uso de builders)
- Services encapsulam toda a complexidade de conversão
- Testes garantem que mappers funcionam corretamente
- Documentação clara sobre quando usar Domain vs Entity

## Exemplos

### Domain (Puro)
```java
@Getter
@Builder
@AllArgsConstructor
public class Familia {
    private final Long id;
    private String nome;
    private boolean ativa;
    private final LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
    public void atualizarNome(String novoNome) {
        this.nome = validarNome(novoNome);
        this.atualizadoEm = LocalDateTime.now();
    }
    
    private static String validarNome(String nome) {
        // Validação de negócio
    }
}
```

### Entity (JPA)
```java
@Entity
@Table(name = "familias")
@Getter
@Setter
@Builder
public class FamiliaEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String nome;
    
    @CreationTimestamp
    private LocalDateTime criadoEm;
    
    @UpdateTimestamp
    private LocalDateTime atualizadoEm;
}
```

### Service (Usa ambos)
```java
@Service
public class FamiliaService {
    public Familia atualizar(Long id, String novoNome) {
        // 1. Busca entity
        FamiliaEntity entity = repository.findById(id);
        
        // 2. Converte para domínio
        Familia familia = mapper.toDomain(entity);
        
        // 3. Aplica regra de negócio
        familia.atualizarNome(novoNome);
        
        // 4. Atualiza entity
        entity.setNome(familia.getNome());
        entity.setAtualizadoEm(familia.getAtualizadoEm());
        
        // 5. Persiste e retorna domínio
        return mapper.toDomain(repository.save(entity));
    }
}
```

## Referências
- Domain-Driven Design (Eric Evans)
- Clean Architecture (Robert C. Martin)
- Hexagonal Architecture (Alistair Cockburn)