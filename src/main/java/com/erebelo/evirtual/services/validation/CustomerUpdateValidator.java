package com.erebelo.evirtual.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.dto.CustomerDTO;
import com.erebelo.evirtual.repositories.CustomerRepository;
import com.erebelo.evirtual.resources.exceptions.FieldMessage;

public class CustomerUpdateValidator implements ConstraintValidator<CustomerUpdate, CustomerDTO> {

	@Autowired
	HttpServletRequest request;

	@Autowired
	CustomerRepository repo;

	@Override
	public void initialize(CustomerUpdate ann) {
	}

	@Override
	public boolean isValid(CustomerDTO objDto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();

		Customer aux = repo.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Existing email"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}