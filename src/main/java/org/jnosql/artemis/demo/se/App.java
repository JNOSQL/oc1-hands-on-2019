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


import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.concurrent.ThreadLocalRandom;

public class App {


    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            final MyPublisherService service = container.select(MyPublisherService.class).get();

            ThreadLocalRandom random = ThreadLocalRandom.current();
            for (int index = 0; index < 100; index++) {
                MyEntity entity = new MyEntity(Integer.toString(random.nextInt(0, 1000)));
                service.sendMessage(entity);
            }
        }

    }

    private App() {
    }
}
