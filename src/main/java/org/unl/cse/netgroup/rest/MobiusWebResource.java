/*
 * Copyright 2017-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.unl.cse.netgroup.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.onosproject.rest.AbstractWebResource;
import org.unl.cse.netgroup.NDNInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;

import static org.onlab.util.Tools.nullIsNotFound;

/**
 * Sample web resource.
 */
@Path("mobius")
public class MobiusWebResource extends AbstractWebResource {

    /**
     * Get hello world greeting.
     *
     * @return 200 OK
     */
    @GET
    @Path("info")
    public Response getGreeting() {
        ObjectNode node = mapper().createObjectNode().put("hello", "world");
        return ok(node).build();
    }

    private NDNInfo JsonToNDNInfo(InputStream stream) {
        JsonNode jsonNode;
        try {
            jsonNode = mapper().readTree(stream);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to parse the Mobius NDN POST.");
        }

        String interestFileResource = jsonNode.path("interestFileResource").asText(null);
        String interestSrc = jsonNode.path("interestSrc").asText(null);
        String interestDst = jsonNode.path("interestDst").asText(null);

        if (interestFileResource != null && interestSrc != null && interestDst != null) {
            return new NDNInfo(interestFileResource, interestSrc, interestDst);
        } else {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

    }

}
