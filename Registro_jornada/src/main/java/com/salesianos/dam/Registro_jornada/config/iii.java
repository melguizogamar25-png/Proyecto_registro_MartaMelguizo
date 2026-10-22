/*


    @Override
    public boolean isValid(EmpleadoRequestDTO value, ConstraintValidatorContext context) {

        if (value == null || value.departamentoId() == null || value.salario() == null) {
            return true;
        }

        Departamento departamento = departamentoRepository
                .findById(value.departamentoId())
                .orElse(null);

        if (departamento == null) {
            return true;
        }

        BigDecimal salariosActuales = empleadoRepository
                .sumSalarioByDepartamentoId(departamento.getId());

        // Un departamento sin empleados devuelve null en la suma
        if (salariosActuales == null) {
            salariosActuales = BigDecimal.ZERO;
        }

        boolean valido = salariosActuales.add(value.salario())
                .compareTo(departamento.getPresupuesto()) <= 0;

        if (!valido) {
            // Asociamos el error al campo salario
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("salario")
                    .addConstraintViolation();
        }

        return valido;
    }
}
 */
