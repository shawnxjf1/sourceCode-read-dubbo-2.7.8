/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.metadata.rest.resolver.jaxrs;

import org.apache.dubbo.metadata.rest.ClassPathServiceRestMetadataReader;
import org.apache.dubbo.metadata.rest.DefaultRestService;
import org.apache.dubbo.metadata.rest.RestMethodMetadata;
import org.apache.dubbo.metadata.rest.RestService;
import org.apache.dubbo.metadata.rest.ServiceRestMetadata;
import org.apache.dubbo.metadata.rest.SpringRestService;
import org.apache.dubbo.metadata.rest.StandardRestService;
import org.apache.dubbo.metadata.rest.jaxrs.JAXRSServiceRestMetadataResolver;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link JAXRSServiceRestMetadataResolver} Test
 *
 * @since 2.7.6
 */
public class JAXRSServiceRestMetadataResolverTest {

    private JAXRSServiceRestMetadataResolver instance = new JAXRSServiceRestMetadataResolver();

    @Test
    public void testSupports() {
        // JAX-RS RestService class
        assertTrue(instance.supports(StandardRestService.class));
        // Spring MVC RestService class
        assertFalse(instance.supports(SpringRestService.class));
        // Default RestService class
        assertFalse(instance.supports(DefaultRestService.class));
        // No annotated RestService class
        assertFalse(instance.supports(RestService.class));
        // null
        assertFalse(instance.supports(null));
    }

    @Test
    public void testResolve() {
        // Generated by "dubbo-metadata-processor"
        ClassPathServiceRestMetadataReader reader = new ClassPathServiceRestMetadataReader("META-INF/dubbo/jax-rs-service-rest-metadata.json");
        List<ServiceRestMetadata> serviceRestMetadataList = reader.read();

        ServiceRestMetadata expectedServiceRestMetadata = serviceRestMetadataList.get(0);
        ServiceRestMetadata serviceRestMetadata = instance.resolve(StandardRestService.class);


        List<RestMethodMetadata> meta1 = new LinkedList<>(expectedServiceRestMetadata.getMeta());
        List<RestMethodMetadata> meta2 = new LinkedList<>(serviceRestMetadata.getMeta());

        for (int i = 0; i < meta1.size(); i++) {
            RestMethodMetadata restMethodMetadata = meta1.get(i);
            RestMethodMetadata restMethodMetadata2 = meta2.get(i);
            assertEquals(restMethodMetadata, restMethodMetadata2);
        }

        assertEquals(expectedServiceRestMetadata, serviceRestMetadata);

    }
}
