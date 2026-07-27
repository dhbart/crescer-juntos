    package dhbart.crescerjuntos.domain.service;

    import dhbart.crescerjuntos.domain.exception.ResourceNotFoundException;
    import dhbart.crescerjuntos.domain.model.Crianca;
    import dhbart.crescerjuntos.infrastructure.persistence.JpaCriancaRepository;
    import dhbart.crescerjuntos.infrastructure.persistence.JpaFamiliaRepository;
    import dhbart.crescerjuntos.infrastructure.persistence.entity.CriancaEntity;
    import dhbart.crescerjuntos.infrastructure.persistence.entity.FamiliaEntity;
    import dhbart.crescerjuntos.infrastructure.persistence.mapper.CriancaEntityMapper;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.List; import java.util.stream.Collectors;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    @Transactional public class CriancaService {

        private final JpaCriancaRepository criancaRepository;
        private final CriancaEntityMapper criancaMapper;
        private final JpaFamiliaRepository familiaRepository;

        public Crianca criar(Crianca crianca) {
            log.info("Criando nova criança: {}", crianca.getApelido());

            FamiliaEntity familiaEntity = familiaRepository.findById(crianca.getFamiliaId())
                    .orElseThrow(() -> ResourceNotFoundException.familia(crianca.getFamiliaId()));

            CriancaEntity entity = criancaMapper.toEntity(crianca, familiaEntity);
            CriancaEntity savedEntity = criancaRepository.save(entity);

            return criancaMapper.toDomain(savedEntity);
        }

        @Transactional(readOnly = true)
        public Crianca buscarPorId(Long id) {
            log.debug("Buscando criança por id: {}", id);
            CriancaEntity entity = criancaRepository.findById(id)
                    .orElseThrow(() -> ResourceNotFoundException.crianca(id));

            return criancaMapper.toDomain(entity);
        }

        @Transactional(readOnly = true)
        public List<Crianca> listarTodas() {
            log.debug("Listando todas as crianças");
            return criancaRepository.findAll().stream()
                    .map(criancaMapper::toDomain)
                    .collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public List<Crianca> listarPorFamilia(Long familiaId) {
            log.debug("Listando crianças da família {}", familiaId);
            return criancaRepository.findByFamiliaId(familiaId).stream()
                    .map(criancaMapper::toDomain)
                    .collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public List<Crianca> listarAtivasPorFamilia(Long familiaId) {
            log.debug("Listando crianças ativas da família {}", familiaId);
            return criancaRepository.findByFamiliaIdAndAtivaTrue(familiaId).stream()
                    .map(criancaMapper::toDomain)
                    .collect(Collectors.toList());
        }

        public Crianca atualizarApelido(Long id, String novoApelido) {
            log.info("Atualizando apelido da criança id {}: novo apelido = {}", id, novoApelido);
            CriancaEntity entity = criancaRepository.findById(id)
                    .orElseThrow(() -> ResourceNotFoundException.crianca(id));

            Crianca crianca = criancaMapper.toDomain(entity);
            crianca.atualizarApelido(novoApelido);

            entity.setApelido(crianca.getApelido());

            CriancaEntity savedEntity = criancaRepository.save(entity);
            return criancaMapper.toDomain(savedEntity);
        }

        public void ativar(Long id) {
            log.info("Ativando criança id {}", id);
            CriancaEntity entity = criancaRepository.findById(id)
                    .orElseThrow(() -> ResourceNotFoundException.crianca(id));

            Crianca crianca = criancaMapper.toDomain(entity);
            crianca.ativar();

            entity.setAtiva(crianca.isAtiva());
            criancaRepository.save(entity);
        }

        public void desativar(Long id) {
            log.info("Desativando criança id {}", id);
            CriancaEntity entity = criancaRepository.findById(id)
                    .orElseThrow(() -> ResourceNotFoundException.crianca(id));

            Crianca crianca = criancaMapper.toDomain(entity);
            crianca.desativar();

            entity.setAtiva(crianca.isAtiva());
            criancaRepository.save(entity);
        }

        public void excluir(Long id) {
            log.info("Excluindo criança id {}", id);
            if (!criancaRepository.existsById(id)) {
                throw ResourceNotFoundException.crianca(id);
            }

            criancaRepository.deleteById(id);
        }

    }
