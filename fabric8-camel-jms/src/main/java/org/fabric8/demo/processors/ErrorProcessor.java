package org.fabric8.demo.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang.StringUtils;
import org.fabric8.demo.exceptions.ServiceInvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorProcessor implements Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorProcessor.class);

	public static final String EXCEPTION_HEADER = "JMSThrowException";

	@Override
	public void process(Exchange exchange) throws Exception {

		// First We Check to see if the JMSThrowException Header exists
		if (StringUtils.isNotBlank(exchange.getIn().getHeader(EXCEPTION_HEADER,
				String.class))) {
			
			String exceptionType = exchange.getIn().getHeader(EXCEPTION_HEADER,
					String.class);

			// Check to see whether we throw a fatal or serviceInvocation Exceptions
			if (exceptionType.equals("fatal")) {
				LOGGER.warn("Fatal Exception Being Thrown to Test Transaction Rollback!");

				throw new java.lang.Exception("Testing JMS/AMQ Transaction Policy");
						
			} else if (exceptionType.equals("service")) {
				LOGGER.warn("Non-Fatal Exception Being Thrown, Camel Exception clause should handle this!");

				throw new ServiceInvocationException("Testing Camel onException handling");
			
				//Otherwise the provided JMSThrowException contains an unrecognised value
			} else {
				LOGGER.info("Unrecognised header in JMSThrowException, please use 'fatal' or 'service' to throw specific exceptions");
				return;
			}
		} 

		// Log the fact that the message no Exception will be thrown!
		LOGGER.info("No Fatal Exception Header Detected, transaction should successfully commit...");
	}

}
