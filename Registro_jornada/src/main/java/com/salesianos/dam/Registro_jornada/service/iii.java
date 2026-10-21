/*

    public Departamento getDepartamentoById(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new DepartamentoNoEncontradaException(id));
    }

    public Departamento addDepartamento(DepartamentoRequestDTO dto) {
        return departamentoRepository.save(dto.to());
    }

    public Departamento updateDepartamento(Long id, DepartamentoRequestDTO dto) {
        return departamentoRepository.findById(id)
                .map(d -> {
                    d.setNombre(dto.nombre());
                    d.setPresupuesto(dto.presupuesto());
                    return departamentoRepository.save(d);
                })
                .orElseThrow(() -> new DepartamentoNoEncontradaException(id));
    }

    public void deleteDepartamento(Long id) {
        Departamento d = getDepartamentoById(id);
        departamentoRepository.delete(d);
    }

}
 */


