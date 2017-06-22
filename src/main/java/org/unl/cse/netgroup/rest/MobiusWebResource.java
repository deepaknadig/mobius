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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unl.cse.netgroup.NDNInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

/**
 * Sample web resource.
 */
@Path("")
public class MobiusWebResource extends AbstractWebResource {

    private static Logger log = LoggerFactory.getLogger(MobiusWebResource.class);
    private static NDNInfo ndnInfo;
    private final ObjectNode root = mapper().createObjectNode();

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

    @POST
    @Path("ndn")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ndnPostInfo(InputStream stream) throws IOException {
        ndnInfo = JsonToNDNInfo(stream);
        ndnInfo.logInfo();

        String msg = "Message";
        return Response.ok(root).build();
    }

    @GET
    @Path("ndn")
    public Response getNdnInfo() {

        try {
            root.put("Name", ndnInfo.getName());
            root.put("FileResource", ndnInfo.getInterestFileResource());
            root.put("SrcIP", ndnInfo.getInterestSrc());
            root.put("DstIP", ndnInfo.getInterestDst());
        }catch (NullPointerException e) {
            throw new NullPointerException("Empty get() methods");
        }

        return ok(root).build();
    }



    private NDNInfo JsonToNDNInfo(InputStream stream) {
        JsonNode jsonNode;
        try {
            jsonNode = mapper().readTree(stream);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to parse the Mobius NDN POST.");
        }

        String name = jsonNode.path("name").asText(null);
        String interestFileResource = jsonNode.path("interestFileResource").asText(null);
        String interestSrc = jsonNode.path("interestSrc").asText(null);
        String interestDst = jsonNode.path("interestDst").asText(null);

        log.info("Pinged!");
        if (name != null && interestFileResource != null && interestSrc != null && interestDst != null) {
            return new NDNInfo(name, interestFileResource, interestSrc, interestDst);
        } else {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

    }

}
