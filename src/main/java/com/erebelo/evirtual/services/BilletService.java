package com.erebelo.evirtual.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.erebelo.evirtual.domain.BilletPayment;

@Service
public class BilletService {

	public void fillBilletPayment(BilletPayment pay, Date payInstant) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(payInstant);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pay.setDueDate(cal.getTime());
	}
}
