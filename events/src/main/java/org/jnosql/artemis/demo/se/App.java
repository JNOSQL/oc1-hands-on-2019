/*
 * Copyright (c) 2017 Otávio Santana and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 * You may elect to redistribute this code under either of these licenses.
 *
 * Contributors:
 *
 * Otavio Santana
 */

package org.jnosql.artemis.demo.se;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		String deviceId = args.length > 0 ? args[0] : UUID.randomUUID().toString();

		SeContainer container = SeContainerInitializer.newInstance().initialize();
		
		container.select(MyListenerService.class).get();
		
		final MyPublisherService service = container.select(MyPublisherService.class).get();
		ThreadLocalRandom random = ThreadLocalRandom.current();
		for (int index = 0; index < 10; index++) {
			TemperatureReading reading = new TemperatureReading();
			reading.setTimestamp(System.currentTimeMillis());
			reading.setDeviceId(deviceId);
			reading.setTemperature(50 + 100 * random.nextFloat());
			logger.debug("Sending {} as reading", reading);
			service.sendMessage(reading);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				//NOOP
			}
		}
		
	}

	private App() {
	}
}
