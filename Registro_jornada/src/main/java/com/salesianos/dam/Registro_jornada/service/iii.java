/*



    if (nuevo == ultimo) { //fichar #6
        throw new FichajeDuplicadoException(empleadoId, nuevo); //fichar #6
    } //fichar #6

    return fichajeRepository.save( //fichar #6
            Fichaje.builder() //fichar #6
                    .momento(LocalDateTime.now()) //fichar #6
                    .tipo(nuevo) //fichar #6
                    .empleado(empleado) //fichar #6
                    .build()); //fichar #6
} //fichar #6

public List<Fichaje> getFichajesByEmpleado(Long empleadoId) { //historial #7
    if (!empleadoRepository.existsById(empleadoId)) { //historial #7
        throw new EmpleadoNoEncontradaException(empleadoId); //historial #7
    } //historial #7
    return fichajeRepository.findByEmpleadoIdOrderByMomentoAscIdAsc(empleadoId); //historial #7
} //historial #7

} //fichar #6
 */


