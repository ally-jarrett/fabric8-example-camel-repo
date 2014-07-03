package org.fabric8.demo.transactions;

import org.fabric8.demo.exceptions.ServiceInvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service1Stub {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Service1Stub.class);

    public void doWork() throws ServiceInvocationException {
           LOGGER.info("Service1 Stub Service Fired");
    }

}
