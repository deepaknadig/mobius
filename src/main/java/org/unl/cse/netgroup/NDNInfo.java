package org.unl.cse.netgroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dna on 4/22/17.
 */
public class NDNInfo {

    private String name;
    private String interestFileResource;
    private String interestSrc;
    private String interestDst;

    private static Logger log = LoggerFactory.getLogger(NDNInfo.class);

    public NDNInfo(String name, String interestFileResource, String interestSrc, String interestDst) {
        this.name = name;
        this.interestFileResource = interestFileResource;
        this.interestSrc = interestSrc;
        this.interestDst = interestDst;
    }

    public void logInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name:" ).append(this.name).append(", ")
                .append("Interest File: ").append(this.interestFileResource).append(", ")
                .append("Interest Src: ").append(this.interestSrc).append(", ")
                .append("Interest Dst: ").append(this.interestDst);
        log.info(sb.toString());
    }
}
