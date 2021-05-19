package com.erebelo.evirtual.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.erebelo.evirtual.domain.enums.CustomerType;
import com.erebelo.evirtual.dto.CustomerNewDTO;
import com.erebelo.evirtual.resources.exceptions.FieldMessage;
import com.erebelo.evirtual.services.validation.utils.BR;

public class CustomerInsertValidator implements ConstraintValidator<CustomerInsert, CustomerNewDTO> {
	@Override
	public void initialize(CustomerInsert ann) {
	}

	@Override
	public boolean isValid(CustomerNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		// include the tests here, inserting errors in the list
		if (objDto.getType().equals(CustomerType.NATURALPERSON.getCode()) && !BR.isValidSsn(objDto.getSsnOrNrle())) {
			list.add(new FieldMessage("ssnOrNrle", "Invalid CPF"));
		}

		if (objDto.getType().equals(CustomerType.LEGALPERSON.getCode()) && !BR.isValidTfn(objDto.getSsnOrNrle())) {
			list.add(new FieldMessage("ssnOrNrle", "Invalid CNPJ"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}